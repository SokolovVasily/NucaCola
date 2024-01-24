package com.vasily_sokolov.nucacola.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
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
@Table(name = "product")
public class Product {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_characteristic")
    @Enumerated(EnumType.STRING)
    private ProductCharacteristic characteristic;

    @Column(name = "product_capacity_type")
    @Enumerated(EnumType.STRING)
    private ProductCapacityType capacityType;

    @ManyToOne
    @JsonBackReference("productProductionFk")
    @JoinColumn(name = "production_id", referencedColumnName = "production_id")
    private Production production;

    @ManyToOne
    @JsonBackReference("productWarehouseFk")
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse finishedProductWarehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Double.compare(productPrice, product.productPrice) == 0 &&
                Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productPrice);
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
