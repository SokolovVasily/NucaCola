package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vasily_sokolov.nucacola.entity.enums.StatusProduction;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Tag(name = "production", description = "Entity production")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "production")
public class Production {

    @Schema(description = "Primary key of production")
    @Id
    @Column(name = "production_id")
    private int productionId;


    @Schema(maxLength = 45,
            description = "Characteristic of product")
    @Enumerated(EnumType.STRING)
    @Column(name = "status_production")
    private StatusProduction status;

    @Schema(description = "Raw materials of production list")
    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("productionRawMaterialFk")
    private List<RawMaterial> rawMaterialList;

    @Schema(description = "Products produced at production list")
    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("productProductionFk")
    private List<Product> product;

    @Schema(description = "FinishedProductWarehouse of production")
    @OneToOne
    @JoinColumn(name = "warehouse_id_product",
            referencedColumnName = "warehouse_id")
    private Warehouse finishedProductWarehouse;

    @Schema(description = "RawMaterialWarehouse of production")
    @OneToOne
    @JoinColumn(name = "warehouse_id_raw_material",
            referencedColumnName = "warehouse_id")
    private Warehouse rawMaterialWarehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Production that = (Production) o;
        return productionId == that.productionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionId);
    }

    @Override
    public String toString() {
        return "Production{" +
                "productionId=" + productionId +
                ", rawMaterialWarehouse=" + rawMaterialWarehouse +
                ", product=" + product +
                ", rawMaterialList=" + rawMaterialList +
                ", finishedProductWarehouse=" + finishedProductWarehouse +
                ", status=" + status +
                '}';
    }
}

