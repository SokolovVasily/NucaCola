package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.dto.RawMaterialDto;

import java.util.List;

public interface RawMaterialService {

    List<RawMaterialDto> getRawMaterialsByName(String name);

    List<RawMaterialDto> getAllRawMaterials();

    RawMaterialDto postCreateRawMaterial(RawMaterialDto rawMaterialDto);

    void updateRawMaterialName(String rawMaterialId, String name);

    void deleteRawMaterialById(String rawMaterialId);

}
