package com.vasily_sokolov.nucacola.mapper;


import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.entity.RawMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RawMaterialMapper {
    List<RawMaterialDto> rawMaterialToRawMaterialDto(List<RawMaterial> rawMaterialList);

    @Mapping(source = "rawMaterialName", target = "rawMaterialName")
    @Mapping(source = "supplier.supplierName", target = "supplierName")
    RawMaterialDto toDto(RawMaterial rawMaterial);
}
