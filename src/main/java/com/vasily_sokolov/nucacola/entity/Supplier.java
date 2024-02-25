package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Tag(name = "Supplier", description = "Entity supplier")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier")
public class Supplier {

    @Schema(description = "Primary key of supplier")
    @Id
    @Column(name = "supplier_id")
    private int supplierId;

    @Schema(maxLength = 45,
            description = "Name of supplier")
    @Column(name = "supplier_name")
    private String supplierName;

    @Schema(description = "Raw materials supplied by the supplier")
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
