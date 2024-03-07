package com.projectshelby.medicinepharmacycompanymapping.company;

import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company addNew(Company company){
        if (companyRepository.findByName(company.getName()).isPresent()) throw new CompanyException("The company already exists");
        return companyRepository.save(company);
    }

    public Company getBy(String companyName){
        return companyRepository.findByName(companyName).orElseThrow(()->new CompanyException("The company doesn't exists"));
    }

    public void updateBy(String oldCompanyName,Company company){
        if (companyRepository.findByName(oldCompanyName).isEmpty()) throw new CompanyException("The company doesn't exists");
        companyRepository.updateCompany(oldCompanyName,company.getName(),company.getAddress(),company.getOwner());
    }

    public void deleteBy(String companyName){
        if (companyRepository.findByName(companyName).isEmpty()) throw new CompanyException("The company doesn't exists");
        companyRepository.deleteByName(companyName);
    }

//    public void addPharmacyToCompany(String pharmacyName,String companyName){
//        if (companyRepository.findByName(companyName).isEmpty()) throw new CompanyException("The company doesn't exists");
//        companyRepository.addPharmacy(companyName,pharmacyName);
//    }



}
