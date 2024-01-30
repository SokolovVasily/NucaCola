package com.vasily_sokolov.nucacola.repository;

import com.vasily_sokolov.nucacola.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {

    @Query("select s from Sale s where s.customerName = :customerName")
    List<Sale> getSalesByCustomerName(String customerName);

    @Query("select s from Sale s where s.customerName = " +
            ":customerName and s.product.productName = :productName")
    List<Sale> getSalesByCustomerNameAndProductName(String customerName, String productName);

    @Modifying
    @Query("update Sale s set s.customerName = :customerName where s.saleId = :saleId")
    void updateSaleCustomerName(UUID saleId, String customerName);



}
