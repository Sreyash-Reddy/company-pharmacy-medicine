package com.projectshelby.medicinepharmacycompanymapping.medicine;

import com.projectshelby.medicinepharmacycompanymapping.pharmacy.Pharmacy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue
    private Integer medicineID;

    private String medicineName;
    private Double price;
    private LocalDate manufacturingDate;

    @ManyToOne
    @JoinColumn(name = "pharmacyID")
    private Pharmacy pharmacy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(medicineName, medicine.medicineName) && Objects.equals(price, medicine.price) && Objects.equals(manufacturingDate, medicine.manufacturingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineName, price, manufacturingDate);
    }
}
