package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import com.projectshelby.medicinepharmacycompanymapping.medicine.Medicine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Integer> {
    Optional<Pharmacy> findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Pharmacy SET name = ?2 , address = ?3 ,owner = ?4 WHERE name = ?1")
    void updatePharmacy(String oldPharmacyName, String name , String address, String owner);

    @Modifying
    @Transactional
    @Query("DELETE FROM Pharmacy WHERE name = ?1")
    void deleteByName(String name);

    void deleteAll();
}
