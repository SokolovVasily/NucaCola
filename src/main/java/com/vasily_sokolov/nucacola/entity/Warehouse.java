package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vasily_sokolov.nucacola.entity.enums.WarehouseType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Tag(name = "Warehouse", description = "Entity warehouse")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse")
public class Warehouse {

    @Schema(description = "Primary key of warehouse")
    @Id
    @Column(name = "warehouse_id")
    private int warehouseId;

    @Schema(maxLength = 45, description = "Warehouse type")
    @Enumerated(EnumType.STRING)
    @Column(name = "warehouse_type")
    private WarehouseType warehouseType;

    @Schema(description = "Raw materials stored in warehouse for production list")
    @JsonManagedReference("warehouseRawMaterialFk")
    @OneToMany(mappedBy = "rawMaterialWarehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RawMaterial> rawMaterialList;

    @Schema(description = "Produced in production Products stored in warehouse list")
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
