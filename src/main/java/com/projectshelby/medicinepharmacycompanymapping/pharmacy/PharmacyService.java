package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import com.projectshelby.medicinepharmacycompanymapping.company.CompanyService;
import com.projectshelby.medicinepharmacycompanymapping.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private CompanyService companyService;


    public Pharmacy addNew(Pharmacy pharmacy, String companyName) {
        //Try doing this logic in one line making use of optional
        if (pharmacyRepository.findByName(pharmacy.getName()).isPresent()) throw new PharmacyException("The pharmacy name already exists");
        pharmacy.setCompany(companyService.getBy(companyName));
        return pharmacyRepository.save(pharmacy);
    }

    public Pharmacy getBy(String pharmacyName){
        return pharmacyRepository.findByName(pharmacyName).orElseThrow(()->new PharmacyException("The pharmacy name doesn't exists"));
    }

    public void updateBy(String oldPharmacyName,Pharmacy pharmacy){
        if (pharmacyRepository.findByName(oldPharmacyName).isEmpty()) throw new PharmacyException("The pharmacy name doesn't exists");
        pharmacyRepository.updatePharmacy(oldPharmacyName,pharmacy.getName(),pharmacy.getAddress(),pharmacy.getOwner());
    }


    public void deleteBy(String pharmacyName){
        if (pharmacyRepository.findByName(pharmacyName).isEmpty()) throw new PharmacyException("The pharmacy name doesn't exists");
        pharmacyRepository.deleteByName(pharmacyName);
    }

    public Pharmacy getPharmacyDetailsFromMedicine(String medicineName){
        //UNOPTIMIZED CODE
        return pharmacyRepository.findAll()
                .stream()
                .filter(pharmacy -> pharmacy.getMedicines().stream().map(medicine -> medicine.getMedicineName().equals(medicineName)).toList().contains(true))
                .findFirst()
                .orElseThrow(()->new PharmacyException("No pharmacies found"));

    }

    public Double getTotalCostOfMedicinesOwnedBy(String pharmacyName){
        return pharmacyRepository.findByName(pharmacyName)
                .orElseThrow(IllegalArgumentException::new)
                .getMedicines()
                .stream()
                .map(Medicine::getPrice)
                .reduce(Double::sum)
                .orElseThrow(()-> new PharmacyException("The Pharmacy name doesn't exists"));
    }




    public void deleteAll(){
        pharmacyRepository.deleteAll();
    }


}
