package com.vasily_sokolov.nucacola.repository;

import com.vasily_sokolov.nucacola.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, UUID> {

    @Query("select r from RawMaterial r where r.rawMaterialName = :name")
    List<RawMaterial> getRawMaterialsByName(String name);

    @Modifying
    @Query("update RawMaterial r set r.rawMaterialName = :name where r.rawMaterialId = :uuid")
    void updateRawMaterialName(UUID uuid, String name);
}
