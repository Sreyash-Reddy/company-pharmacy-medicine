package com.projectshelby.medicinepharmacycompanymapping.pharmacy;

import com.projectshelby.medicinepharmacycompanymapping.company.Company;
import com.projectshelby.medicinepharmacycompanymapping.medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacy {

    @Id
    @GeneratedValue
    private Integer pharmacyID;

    private String name;
    private String address;
    private String owner;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    private Company company;

    @OneToMany
    private List<Medicine> medicines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(name, pharmacy.name) && Objects.equals(address, pharmacy.address) && Objects.equals(owner, pharmacy.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, owner);
    }
}
