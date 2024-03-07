package com.projectshelby.medicinepharmacycompanymapping.medicine;

import com.projectshelby.medicinepharmacycompanymapping.pharmacy.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyService pharmacyService;


    public Medicine addNew(Medicine medicine, String pharmacyName) {
        //Try doing this logic in one line making use of optional
        if (medicineRepository.findByMedicineName(medicine.getMedicineName()).isPresent()) throw new MedicineException("The medicine already exists");
        medicine.setPharmacy(pharmacyService.getBy(pharmacyName));
        return medicineRepository.save(medicine);
    }

    public Medicine getBy(String medicineName){
        return medicineRepository.findByMedicineName(medicineName).orElseThrow(()->new MedicineException("The medicine name doesn't exists"));
    }

    public void updateBy(String oldMedicineName,Medicine medicine){
        if (medicineRepository.findByMedicineName(oldMedicineName).isEmpty()) throw new MedicineException("The medicine name doesn't exists");
        medicineRepository.updateMedicine(oldMedicineName,medicine.getMedicineName(),medicine.getPrice(),medicine.getManufacturingDate());
    }


    public void deleteBy(String medicineName){
        if (medicineRepository.findByMedicineName(medicineName).isEmpty()) throw new MedicineException("The medicine name doesn't exists");
        medicineRepository.deleteByName(medicineName);
    }

    public List<Medicine> getMedicinesDetailsFrom(String pharmacyName){
        pharmacyService.getBy(pharmacyName);
        return medicineRepository.getMedicinesByPharmacyName(pharmacyName);
    }

    public List<Medicine> getTwoMedicinesDetailsFrom(String pharmacyName){
        return getMedicinesDetailsFrom(pharmacyName).stream().limit(2).toList();
    }


    public void deleteAll(){
        medicineRepository.deleteAll();
    }


}
