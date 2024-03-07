package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import com.projectshelby.medicinepharmacycompanymapping.company.CompanyRepository;
import com.projectshelby.medicinepharmacycompanymapping.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private CompanyService companyService;


    public Pharmacy addNew(Pharmacy pharmacy, String companyName) {
        if (pharmacyRepository.findByName(pharmacy.getName()).isPresent()) throw new PharmacyException("The pharmacy name already exists");
        pharmacy.setCompany(companyService.getBy(companyName));
        return pharmacyRepository.save(pharmacy);
    }
}
