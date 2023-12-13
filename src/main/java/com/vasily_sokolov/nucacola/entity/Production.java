package com.vasily_sokolov.nucacola.entity;

import com.vasily_sokolov.nucacola.entity.enums.StatusProduction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Production {
    private int productionId;
    private Warehouse<RawMaterial> rawMaterialWarehouse;
    private Product product;
    private Map<RawMaterial, Integer> quantityNeeded;
    private Warehouse<Product> finishedProductWarehouse;
    private StatusProduction status;

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
                ", quantityNeeded=" + quantityNeeded +
                ", finishedProductWarehouse=" + finishedProductWarehouse +
                ", status=" + status +
                '}';
    }
}

