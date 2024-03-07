package com.projectshelby.medicinepharmacycompanymapping.medicine;

import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine,Integer> {
    Optional<Medicine> findByMedicineName(String medicineName);

    @Modifying
    @Transactional
    @Query("UPDATE Medicine SET medicineName = ?2 , price = ?3 ,manufacturingDate = ?4 WHERE medicineName = ?1")
    void updateMedicine(String oldMedicineName, String medicineName, Double price, LocalDate manufacturingDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM Medicine WHERE medicineName = ?1")
    void deleteByName(String name);

    List<Medicine> getMedicinesByPharmacyName(String pharmacyName);

    void deleteAll();
}
