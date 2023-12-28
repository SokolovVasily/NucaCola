package com.vasily_sokolov.nucacola.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
public class Sale {

    @Id
    @Column(name = "sale_id")
    private int saleId;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "customer_name")
    private String customerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse finishedProductWarehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return saleId == sale.saleId && Objects.equals(customerName, sale.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, customerName);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", product=" + product +
                ", saleDate=" + saleDate +
                ", customerName='" + customerName + '\'' +
                ", finishedProductWarehouse=" + finishedProductWarehouse +
                '}';
    }
}
