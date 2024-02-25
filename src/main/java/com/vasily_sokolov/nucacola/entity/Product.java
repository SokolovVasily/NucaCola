package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Tag(name = "Product", description = "Entity product")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Schema(maxLength = 36, description = "Primary key of product")
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "product_id")
    private UUID productId;

    @Schema(maxLength = 45, description = "Name of product")
    @Column(name = "product_name")
    private String productName;

    @Schema(maximum = "999999.99", description = "Price of product")
    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Schema(maxLength = 45, description = "Characteristic of product")
    @Column(name = "product_characteristic")
    @Enumerated(EnumType.STRING)
    private ProductCharacteristic characteristic;

    @Schema(maxLength = 45, description = "Capacity type of product")
    @Column(name = "product_capacity_type")
    @Enumerated(EnumType.STRING)
    private ProductCapacityType capacityType;

    @Schema(description = "Products that come from production")
    @ManyToOne
    @JsonBackReference("productProductionFk")
    @JoinColumn(name = "production_id",
            referencedColumnName = "production_id")
    private Production production;

    @Schema(description = "Products stored in warehouse")
    @ManyToOne
    @JsonBackReference("productWarehouseFk")
    @JoinColumn(name = "warehouse_id",
            referencedColumnName = "warehouse_id")
    private Warehouse finishedProductWarehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", characteristic=" + characteristic +
                ", capacityType=" + capacityType +
                ", finishedProductWarehouse=" + finishedProductWarehouse +
                ", price=" + productPrice +
                '}';
    }
}
