package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.entity.RawMaterial;

import java.util.List;

public interface RawMaterialService {

    List<RawMaterial> getRawMaterialsByName(String name);

    List<RawMaterial> getAllRawMaterials();

    RawMaterial postCreateRawMaterial(RawMaterial rawMaterial);

    void updateRawMaterialName(String rawMaterialId, String name);

    void deleteRawMaterialById(String rawMaterialId);

}
