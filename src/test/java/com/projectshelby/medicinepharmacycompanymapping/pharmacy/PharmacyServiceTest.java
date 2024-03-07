package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import com.projectshelby.medicinepharmacycompanymapping.company.Company;
import com.projectshelby.medicinepharmacycompanymapping.company.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PharmacyServiceTest {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private CompanyService companyService;

    private final Pharmacy internalPharmacy = Pharmacy.builder().name("Pharmacy1").address("PharmacyAddress1").owner("PharmacyOwner1").build();
    private final Pharmacy internalPharmacy2 = Pharmacy.builder().name("Pharmacy2").address("PharmacyAddress2").owner("PharmacyOwner2").build();

    private final Company internalCompany = Company.builder().name("Company1").address("CompanyAddress1").owner("CompanyOwner1").build();

    @Test
    void when_addNew_method_is_Called_with_nonExisting_pharmacyName_add_New_Pharmacy_and_return_Pharmacy(){
        companyService.addNew(internalCompany);
        Pharmacy pharmacy = pharmacyService.addNew(internalPharmacy,"Company1");
        assertEquals(internalPharmacy,pharmacy);
        assertEquals("Company1",pharmacy.getCompany().getName());
        Pharmacy pharmacy2 = pharmacyService.addNew(internalPharmacy2,"Company1");
        assertEquals(internalPharmacy2,pharmacy2);
        assertEquals("Company1",pharmacy2.getCompany().getName());
    }

}
