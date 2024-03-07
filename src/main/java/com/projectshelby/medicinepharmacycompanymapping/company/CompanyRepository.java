package com.projectshelby.medicinepharmacycompanymapping.company;

import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Optional<Company> findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Company SET name = ?2 , address = ?3 ,owner = ?4 WHERE name = ?1")
    void updateCompany(String oldCompanyName, String name , String address, String owner);

    @Modifying
    @Transactional
    @Query("DELETE FROM Company WHERE name = ?1")
    void deleteByName(String name);

    @Query(value = "SELECT pharmacies FROM Company WHERE name = ?1")
    List<Pharmacy> findPharmaciesByName(String companyName);

    void deleteAll();
}


