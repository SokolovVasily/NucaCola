package com.vasily_sokolov.nucacola.entity;

import com.vasily_sokolov.nucacola.entity.enums.StatusProduction;
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
@Table(name = "production")
public class Production {

    @Id
    @Column(name = "production_id")
    private int productionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_production")
    private StatusProduction status;

    @OneToMany
    @JoinColumn(name = "raw_material_id")
    private List<RawMaterial> rawMaterialList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "warehouse_id_product",referencedColumnName = "warehouse_id")
    private Warehouse finishedProductWarehouse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id_raw_material",referencedColumnName = "warehouse_id")
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

