package com.projectshelby.medicinepharmacycompanymapping.medicine;

import com.projectshelby.medicinepharmacycompanymapping.company.Company;
import com.projectshelby.medicinepharmacycompanymapping.company.CompanyService;
import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import com.projectshelby.medicinepharmacycompanymapping.pharmacy.PharmacyException;
import com.projectshelby.medicinepharmacycompanymapping.pharmacy.PharmacyService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class MedicineServiceTest {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private CompanyService companyService;

    private final Medicine internalMedicine = Medicine.builder().medicineName("Medicine1").price(100.0).manufacturingDate(LocalDate.of(2024,3,7)).build();
    private final Medicine internalMedicine2 = Medicine.builder().medicineName("Medicine2").price(200.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    private final Pharmacy internalPharmacy = Pharmacy.builder().name("Pharmacy1").address("PharmacyAddress1").owner("PharmacyOwner1").build();
    private final Company internalCompany = Company.builder().name("Company1").address("CompanyAddress1").owner("CompanyOwner1").build();

    @Order(1)
    @Test
    void when_addNew_method_is_called_for_nonExisting_medicineName_return_medicineObject(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        assertEquals(internalMedicine,medicineService.addNew(internalMedicine,internalPharmacy.getName()));
    }

    @Order(2)
    @Test
    void when_addNew_method_is_called_for_Existing_medicineName_throw_exception(){
        assertThrows(MedicineException.class,()->medicineService.addNew(internalMedicine,internalPharmacy.getName()));
    }

    @Order(3)
    @Test
    void when_addNew_method_is_called_for_nonExisting_pharmacyName_throw_exception(){
        assertThrows(PharmacyException.class,()->medicineService.addNew(internalMedicine2,"Pharmacy100"));
    }

    @Order(4)
    @Test
    void when_getBy_method_is_called_for_Existing_medicineName_return_medicine(){
        assertEquals(internalMedicine,medicineService.getBy(internalMedicine.getMedicineName()));
    }

    @Order(5)
    @Test
    void when_getBy_method_is_called_for_nonExisting_medicineName_throw_Exception(){
        assertThrows(MedicineException.class,()->medicineService.getBy(internalMedicine2.getMedicineName()));
    }

    @Order(6)
    @Test
    void when_updateBy_method_is_Called_for_existing_medicineName_update_the_entity_and_return_void(){
        medicineService.updateBy(internalMedicine.getMedicineName(),internalMedicine2);
        assertEquals(internalMedicine2,medicineService.getBy(internalMedicine2.getMedicineName()));
    }

    @Order(7)
    @Test
    void when_updateBy_method_is_Called_for_nonExisting_medicineName_throw_Exception(){
        assertThrows(MedicineException.class,()->medicineService.updateBy(internalMedicine.getMedicineName(),internalMedicine2));
    }

    @Order(8)
    @Test
    void when_deleteByName_method_is_called_for_Existing_medicineName_delete_medicine_and_return_void(){
        medicineService.deleteBy(internalMedicine2.getMedicineName());
        assertThrows(MedicineException.class,()->medicineService.getBy(internalMedicine2.getMedicineName()));
    }

    @Order(9)
    @Test
    void when_deleteByName_method_is_called_for_nonExisting_medicineName_delete_medicine_and_return_void(){
        assertThrows(MedicineException.class,()-> medicineService.deleteBy(internalMedicine2.getMedicineName()));
    }


    private final Medicine internalMedicine3 = Medicine.builder().medicineName("Medicine3").price(300.0).manufacturingDate(LocalDate.of(2024,3,7)).build();

    private final Medicine internalMedicine4 = Medicine.builder().medicineName("Medicine4").price(400.0).manufacturingDate(LocalDate.of(2024,3,7)).build();
    private final Medicine internalMedicine5 = Medicine.builder().medicineName("Medicine5").price(500.0).manufacturingDate(LocalDate.of(2024,3,7)).build();
    private final Pharmacy internalPharmacy2 = Pharmacy.builder().name("Pharmacy2").address("PharmacyAddress2").owner("PharmacyOwner2").build();

    @Order(10)
    @Test
    void when_getMedicinesDetailsFrom_method_is_called_for_existing_pharmacyName_return_List_of_Medicines(){
        companyService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        pharmacyService.addNew(internalPharmacy2,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        medicineService.addNew(internalMedicine2,internalPharmacy2.getName());
        medicineService.addNew(internalMedicine3,internalPharmacy2.getName());
        medicineService.addNew(internalMedicine4,internalPharmacy.getName());

        List<Medicine> medicines = medicineService.getMedicinesDetailsFrom(internalPharmacy.getName());
        assertEquals(internalMedicine.getMedicineName(),medicines.get(0).getMedicineName());
        assertEquals(internalMedicine4.getMedicineName(),medicines.get(1).getMedicineName());
    }

    @Order(11)
    @Test
    void when_getMedicinesDetailsFrom_method_is_called_for_non_Existing_pharmacyName_return_List_of_Medicines(){
        assertThrows(PharmacyException.class,()->medicineService.getMedicinesDetailsFrom("Pharmacy100"));
    }

    @Order(12)
    @Test
    void when_getTwoMedicinesDetailsFrom_method_is_called_for_existing_pharmacyName_return_List_of_Medicines(){
        companyService.deleteAll();
        pharmacyService.deleteAll();
        medicineService.deleteAll();
        companyService.addNew(internalCompany);
        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
        pharmacyService.addNew(internalPharmacy2,internalCompany.getName());
        medicineService.addNew(internalMedicine,internalPharmacy.getName());
        medicineService.addNew(internalMedicine2,internalPharmacy2.getName());
        medicineService.addNew(internalMedicine3,internalPharmacy2.getName());
        medicineService.addNew(internalMedicine4,internalPharmacy.getName());
        medicineService.addNew(internalMedicine5,internalPharmacy.getName());

        List<Medicine> medicines = medicineService.getTwoMedicinesDetailsFrom(internalPharmacy.getName());
        assertEquals(2,medicines.size());
    }

}
