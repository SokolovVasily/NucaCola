package com.vasily_sokolov.nucacola.entity;

import com.vasily_sokolov.nucacola.entity.enums.WarehouseType;
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
public class Warehouse<T> {
    private int warehouseId;
    private WarehouseType warehouseType;
    private Map<T, Integer> quantityProduct;
    private Supplier suppliers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse<?> warehouse = (Warehouse<?>) o;
        return warehouseId == warehouse.warehouseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", warehouseType=" + warehouseType +
                ", quantityProduct=" + quantityProduct +
                ", suppliers=" + suppliers +
                '}';
    }
}
