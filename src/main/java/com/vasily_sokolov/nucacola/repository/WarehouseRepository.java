package com.vasily_sokolov.nucacola.repository;

import com.vasily_sokolov.nucacola.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
