package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vasily_sokolov.nucacola.entity.enums.WarehouseType;
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
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "warehouse_id")
    private int warehouseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "warehouse_type")
    private WarehouseType warehouseType;

    @JsonManagedReference("warehouseRawMaterialFk")
    @OneToMany(mappedBy = "rawMaterialWarehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RawMaterial> rawMaterialList;

    @JsonManagedReference("productWarehouseFk")
    @OneToMany(mappedBy = "finishedProductWarehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
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
                ", productList=" + productList +
                '}';
    }
}
