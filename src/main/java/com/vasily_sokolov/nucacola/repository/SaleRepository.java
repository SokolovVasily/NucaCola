package com.vasily_sokolov.nucacola.repository;

import com.vasily_sokolov.nucacola.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Integer> {
    @Query("select s from Sale s where s.saleId = :saleId")
    Sale getSaleById(String saleId);

    @Query("select s from Sale s where s.customerName = :customerName")
    List<Sale> getSalesByCustomerName(String customerName);
}
