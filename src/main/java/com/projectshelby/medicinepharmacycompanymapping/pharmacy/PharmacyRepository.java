package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Integer> {
    Optional<Pharmacy> findByName(String name);
}
