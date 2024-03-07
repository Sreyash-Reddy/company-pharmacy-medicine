package com.projectshelby.medicinepharmacycompanymapping.company;


import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyID;

    private String name;
    private String address;
    private String owner;

    @OneToMany
    private List<Pharmacy> pharmacies;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(address, company.address) && Objects.equals(owner, company.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, owner);
    }

}
