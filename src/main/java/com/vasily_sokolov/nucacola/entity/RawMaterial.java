package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "raw_material")
public class RawMaterial {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "raw_material_id")
    private UUID rawMaterialId;

    @Column(name = "raw_material_name")
    private String rawMaterialName;

    @ManyToOne
    @JsonBackReference("warehouseRawMaterialFk")
    @JoinColumn(name = "raw_material_warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse rawMaterialWarehouse;

    @ManyToOne
    @JsonBackReference("productionRawMaterialFk")
    @JoinColumn(name = "production_id", referencedColumnName = "production_id")
    private Production production;


    @ManyToOne
    @JsonBackReference("supplierRawMaterialFk")
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
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
