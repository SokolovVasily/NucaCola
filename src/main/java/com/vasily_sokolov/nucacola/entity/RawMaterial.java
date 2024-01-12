package com.vasily_sokolov.nucacola.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "raw_material")
public class RawMaterial {

    @Id
    @Column(name = "raw_material_id")
    private UUID rawMaterialId;

    @Column(name = "raw_material_name")
    private String rawMaterialName;

    @ManyToOne
    @JoinColumn(name = "raw_material_warehouse_id",referencedColumnName = "warehouse_id")
    private Warehouse rawMaterialWarehouse;

    @OneToOne
    @JoinColumn(name = "supplier_id",referencedColumnName = "supplier_id")
    private Supplier supplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawMaterial that = (RawMaterial) o;
        return rawMaterialId == that.rawMaterialId && Objects.equals(rawMaterialName, that.rawMaterialName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMaterialId, rawMaterialName);
    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "rawMaterialId=" + rawMaterialId +
                ", rawMaterialName='" + rawMaterialName + '\'' +
                ", rawMaterialWarehouse=" + rawMaterialWarehouse +
                ", supplier=" + supplier +
                '}';
    }
}
