package com.vasily_sokolov.nucacola.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private int saleId;
    private Map<Product, Integer> quantitySold;
    private Date saleDate;
    private String customerName;
    private Warehouse<Product> finishedProductWarehouse;

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
                ", quantitySold=" + quantitySold +
                ", saleDate=" + saleDate +
                ", customerName='" + customerName + '\'' +
                ", finishedProductWarehouse=" + finishedProductWarehouse +
                '}';
    }
}
