package com.projectshelby.medicinepharmacycompanymapping.company;

import static org.junit.jupiter.api.Assertions.*;

import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import com.projectshelby.medicinepharmacycompanymapping.pharmacy.PharmacyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PharmacyService pharmacyService;

    private final Company internalCompany = Company.builder().name("Company1").address("CompanyAddress1").owner("CompanyOwner1").build();
    private final Company internalCompanyModified = Company.builder().name("Company100").address("CompanyAddress1").owner("CompanyOwner1").build();


    private final Pharmacy internalPharmacy = Pharmacy.builder().name("Pharmacy1").address("PharmacyAddress1").owner("PharmacyOwner1").build();

    @Test
    void when_addNew_method_is_Called_with_nonExisting_companyName_add_New_Company_and_return_Company(){
        assertEquals(internalCompany,companyService.addNew(internalCompany));
    }

    @Test
    void when_addNew_method_is_Called_with_Existing_companyName_throw_CompanyException(){
        assertThrows(CompanyException.class,()->companyService.addNew(internalCompany));
    }

    @Test
    void when_getBy_method_is_Called_with_Existing_companyName_return_Company(){
        assertEquals(internalCompany,companyService.getBy("Company1"));
    }

    @Test
    void when_getBy_method_is_Called_with_nonExisting_companyName_throw_CompanyException(){
        assertThrows(CompanyException.class,()->companyService.getBy("Company2"));
    }

    @Test
    void when_updateBy_is_called_for_Existing_companyName_update_the_companyDetails_and_return_the_updated_Company(){
        companyService.updateBy(internalCompany.getName(),internalCompanyModified);
        assertEquals(internalCompanyModified,companyService.getBy(internalCompanyModified.getName()));
    }

    @Test
    void when_updateBy_is_called_for_non_Existing_companyName_throw_Company_Exception(){
        assertThrows(CompanyException.class,()->companyService.updateBy(internalCompany.getName(),internalCompanyModified));
    }

    @Test
    void when_deleteBy_method_is_Called_with_existing_companyName_delete_the_entry_and_return_void(){
        companyService.deleteBy("Company100");
        assertThrows(CompanyException.class,()->companyService.getBy("Company100"));
    }

    @Test
    void when_deleteBy_method_is_Called_with_non_existing_companyName_throw_Company_exception(){
        assertThrows(CompanyException.class,()->companyService.deleteBy("Company100"));
    }

//    @Test
//    void when_addPharmacyToCompany_is_Called_with_existing_companyName_and_existing_PharmacyName_add_Pharmacy(){
//
//        companyService.addNew(internalCompany);
//        pharmacyService.addNew(internalPharmacy,internalCompany.getName());
//        companyService.addPharmacyToCompany();
//    }

}
