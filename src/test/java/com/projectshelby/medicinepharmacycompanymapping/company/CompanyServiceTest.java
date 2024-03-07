package com.projectshelby.medicinepharmacycompanymapping.company;

import static org.junit.jupiter.api.Assertions.*;

import com.projectshelby.medicinepharmacycompanymapping.medicine.Medicine;
import com.projectshelby.medicinepharmacycompanymapping.medicine.MedicineService;
import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import com.projectshelby.medicinepharmacycompanymapping.pharmacy.PharmacyService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private MedicineService medicineService;

    private final Company internalCompany = Company.builder().name("Company1").address("CompanyAddress1").owner("CompanyOwner1").build();
    private final Company internalCompanyModified = Company.builder().name("Company100").address("CompanyAddress1").owner("CompanyOwner1").build();


    private final Pharmacy internalPharmacy = Pharmacy.builder().name("Pharmacy1").address("PharmacyAddress1").owner("PharmacyOwner1").build();

    @Order(1)
    @Test
    void when_addNew_method_is_Called_with_nonExisting_companyName_add_New_Company_and_return_Company(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        assertEquals(internalCompany,companyService.addNew(internalCompany));
    }

    @Order(2)
    @Test
    void when_addNew_method_is_Called_with_Existing_companyName_throw_CompanyException(){
        assertThrows(CompanyException.class,()->companyService.addNew(internalCompany));
    }

    @Order(3)
    @Test
    void when_getBy_method_is_Called_with_Existing_companyName_return_Company(){
        assertEquals(internalCompany,companyService.getBy("Company1"));
    }

    @Order(4)
    @Test
    void when_getBy_method_is_Called_with_nonExisting_companyName_throw_CompanyException(){
        assertThrows(CompanyException.class,()->companyService.getBy("Company2"));
    }

    @Order(5)
    @Test
    void when_updateBy_is_called_for_Existing_companyName_update_the_companyDetails_and_return_the_updated_Company(){
        companyService.updateBy(internalCompany.getName(),internalCompanyModified);
        assertEquals(internalCompanyModified,companyService.getBy(internalCompanyModified.getName()));
    }

    @Order(6)
    @Test
    void when_updateBy_is_called_for_non_Existing_companyName_throw_Company_Exception(){
        assertThrows(CompanyException.class,()->companyService.updateBy(internalCompany.getName(),internalCompanyModified));
    }

    @Order(7)
    @Test
    void when_deleteBy_method_is_Called_with_existing_companyName_delete_the_entry_and_return_void(){
        companyService.deleteBy("Company100");
        assertThrows(CompanyException.class,()->companyService.getBy("Company100"));
    }

    @Order(8)
    @Test
    void when_deleteBy_method_is_Called_with_non_existing_companyName_throw_Company_exception(){
        assertThrows(CompanyException.class,()->companyService.deleteBy("Company100"));
    }

    private final Company internalCompany2 = Company.builder().name("Company2").address("CompanyAddress2").owner("CompanyOwner2").build();

    private final Medicine internalMedicine = Medicine.builder().medicineName("Medicine1").price(100.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    private final Medicine internalMedicine2 = Medicine.builder().medicineName("Medicine2").price(200.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    private final Pharmacy internalPharmacy2 = Pharmacy.builder().name("Pharmacy2").address("PharmacyAddress2").owner("PharmacyOwner2").build();

    @Order(9)
    @Test
    void when_getCompanyDetailsFromMedicine_is_Called_with_Existing_medicineName_return_company(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        companyService.addNew(internalCompany2);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        pharmacyService.addNew(internalPharmacy2,internalCompany2.getName());
        medicineService.addNew(internalMedicine2,internalPharmacy2.getName());
        assertEquals(internalCompany2,companyService.getCompanyDetailsFromMedicine(internalMedicine2.getMedicineName()));
    }




}
