package com.vasily_sokolov.nucacola.repository;

import com.vasily_sokolov.nucacola.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    @Query("select s from Supplier s where s.supplierName = :name")
    Supplier getSupplierByName(String name);
}
