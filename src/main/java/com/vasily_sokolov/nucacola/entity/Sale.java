package com.vasily_sokolov.nucacola.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
public class Sale {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "sale_id")
    private UUID saleId;

    @Column(name = "sale_date")
    private Date saleDate;

    @Column(name = "customer_name")
    private String customerName;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;


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
                '}';
    }
}
