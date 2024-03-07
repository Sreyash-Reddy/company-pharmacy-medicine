package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import com.projectshelby.medicinepharmacycompanymapping.company.Company;
import com.projectshelby.medicinepharmacycompanymapping.company.CompanyException;
import com.projectshelby.medicinepharmacycompanymapping.company.CompanyService;
import com.projectshelby.medicinepharmacycompanymapping.medicine.Medicine;
import com.projectshelby.medicinepharmacycompanymapping.medicine.MedicineService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PharmacyServiceTest {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MedicineService medicineService;

    private final Pharmacy internalPharmacy = Pharmacy.builder().name("Pharmacy1").address("PharmacyAddress1").owner("PharmacyOwner1").build();
    private final Pharmacy internalPharmacy2 = Pharmacy.builder().name("Pharmacy2").address("PharmacyAddress2").owner("PharmacyOwner2").build();

    private final Company internalCompany = Company.builder().name("Company1").address("CompanyAddress1").owner("CompanyOwner1").build();

    @Order(1)
    @Test
    void when_addNew_method_is_Called_with_nonExisting_pharmacyName_add_New_Pharmacy_and_return_Pharmacy(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        Pharmacy pharmacy = pharmacyService.addNew(internalPharmacy,"Company1");
        assertEquals(internalPharmacy,pharmacy);
        assertEquals("Company1",pharmacy.getCompany().getName());
    }

    @Order(2)
    @Test
    void when_addNew_method_is_Called_with_Existing_pharmacyName_throw_pharmacyException(){
        assertThrows(PharmacyException.class,()->pharmacyService.addNew(internalPharmacy,"Company1"));
    }

    @Order(3)
    @Test
    void when_addNew_method_is_Called_with_nonExisting_companyName_throw_pharmacyException(){
        assertThrows(CompanyException.class,()->pharmacyService.addNew(internalPharmacy2,"Company2"));
    }

    @Order(4)
    @Test
    void when_getBy_method_is_called_with_existing_companyName_return_CompanyName(){
        assertEquals(internalPharmacy , pharmacyService.getBy(internalPharmacy.getName()));
    }

    @Order(5)
    @Test
    void when_getBy_method_is_called_with_non_existing_companyName_throw_PharmacyException(){
        assertThrows(PharmacyException.class,()->pharmacyService.getBy(internalPharmacy2.getName()));
    }

    @Order(6)
    @Test
    void when_updateBy_method_is_Called_for_existing_companyName_update_the_entity_and_return_void(){
        pharmacyService.updateBy(internalPharmacy.getName(),internalPharmacy2);
        assertEquals(internalPharmacy2,pharmacyService.getBy(internalPharmacy2.getName()));
    }

    @Order(7)
    @Test
    void when_updateBy_method_is_Called_for_nonExisting_companyName_throw_Exception(){
        assertThrows(PharmacyException.class,()->pharmacyService.updateBy(internalPharmacy.getName(),internalPharmacy2));
    }

    @Order(8)
    @Test
    void when_deleteBy_method_is_Called_for_existing_companyName_delete_company_and_return_void(){
        pharmacyService.deleteBy(internalPharmacy2.getName());
        assertThrows(PharmacyException.class,()->pharmacyService.getBy(internalPharmacy2.getName()));
    }

    @Order(9)
    @Test
    void when_deleteBy_method_is_Called_for_nonExisting_companyName_throw_Exception(){
        assertThrows(PharmacyException.class,()->pharmacyService.deleteBy(internalPharmacy2.getName()));
    }

    private final Medicine internalMedicine = Medicine.builder().medicineName("Medicine1").price(100.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    private final Medicine internalMedicine2 = Medicine.builder().medicineName("Medicine2").price(200.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    private final Medicine internalMedicine3 = Medicine.builder().medicineName("Medicine3").price(300.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    @Order(10)
    @Test
    void when_getPharmacyDetailsFromMedicine_is_Called_For_ExistingMedicine_return_Pharmacy_Object(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        pharmacyService.addNew(internalPharmacy2,internalCompany.getName());
        medicineService.addNew(internalMedicine2,internalPharmacy2.getName());

        assertEquals(internalPharmacy2,pharmacyService.getPharmacyDetailsFromMedicine(internalMedicine2.getMedicineName()));
    }

    @Order(11)
    @Test
    void when_getPharmacyDetailsFromMedicine_is_Called_For_nonExistingMedicine_throw_Exception(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        assertThrows(PharmacyException.class,()->pharmacyService.getPharmacyDetailsFromMedicine(internalMedicine2.getMedicineName()));
    }

    @Order(12)
    @Test
    void when_getTotalCostOfMedicinesOwnedBy_is_Called_for_Existing_Pharmacy_Name_return_the_cost_of_all_medicines_of_that_pharmacy(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        pharmacyService.addNew(internalPharmacy2,internalCompany.getName());
        medicineService.addNew(internalMedicine2,internalPharmacy2.getName());
        medicineService.addNew(internalMedicine3,internalPharmacy.getName());
        assertEquals(400.0,pharmacyService.getTotalCostOfMedicinesOwnedBy(internalPharmacy.getName()));
    }

    @Order(13)
    @Test
    void when_getTotalCostOfMedicinesOwnedBy_is_Called_for_nonExisting_Pharmacy_Name_throw_Exception(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        pharmacyService.addNew(internalPharmacy2,internalCompany.getName());
        medicineService.addNew(internalMedicine2,internalPharmacy.getName());
        assertThrows(PharmacyException.class,()->pharmacyService.getTotalCostOfMedicinesOwnedBy(internalPharmacy2.getName()));
    }

}
