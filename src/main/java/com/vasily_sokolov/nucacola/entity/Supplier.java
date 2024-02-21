package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier")
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("supplierRawMaterialFk")
    private List<RawMaterial> rawMaterial;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return supplierId == supplier.supplierId && Objects.equals(supplierName, supplier.supplierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId, supplierName);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", rawMaterial=" + rawMaterial +
                '}';
    }
}
