package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Tag(name = "RawMaterial", description = "Entity rawMaterial")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "raw_material")
public class RawMaterial {

    @Schema(maxLength = 36, description = "Primary key of RawMaterial ")
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "raw_material_id")
    private UUID rawMaterialId;

    @Schema(maxLength = 45, description = "Name of raw material")
    @Column(name = "raw_material_name")
    private String rawMaterialName;

    @Schema(description = "Raw materials stored in warehouse")
    @ManyToOne
    @JsonBackReference("warehouseRawMaterialFk")
    @JoinColumn(name = "raw_material_warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse rawMaterialWarehouse;

    @Schema(description = "Raw materials for supply to production")
    @ManyToOne
    @JsonBackReference("productionRawMaterialFk")
    @JoinColumn(name = "production_id", referencedColumnName = "production_id")
    private Production production;


    @Schema(description = "Raw material supplier ")
    @ManyToOne
    @JsonBackReference("supplierRawMaterialFk")
    @JoinColumn(name = "supplier_id",
            referencedColumnName = "supplier_id")
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
