package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.entity.RawMaterial;
import com.vasily_sokolov.nucacola.entity.Supplier;
import com.vasily_sokolov.nucacola.mapper.RawMaterialMapper;
import com.vasily_sokolov.nucacola.repository.RawMaterialRepository;
import com.vasily_sokolov.nucacola.repository.SupplierRepository;
import com.vasily_sokolov.nucacola.service.interf.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImpl implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    private final SupplierRepository supplierRepository;

    private final RawMaterialMapper rawMaterialMapper;


    @Override
    public List<RawMaterialDto> getRawMaterialsByName(String name) {
        return rawMaterialMapper.rawMaterialToRawMaterialDto(rawMaterialRepository.getRawMaterialsByName(name));
    }

    @Override
    public List<RawMaterialDto> getAllRawMaterials() {
        return rawMaterialMapper.rawMaterialToRawMaterialDto(rawMaterialRepository.findAll());
    }

    @Override
    public RawMaterialDto postCreateRawMaterial(RawMaterialDto rawMaterialDto) {
        Supplier supplier = supplierRepository.getSupplierByName(rawMaterialDto.getSupplierName());
        RawMaterial rawMaterial = RawMaterial.builder()
                .rawMaterialName(rawMaterialDto.getRawMaterialName())
                .supplier(supplier)
                .build();
        return rawMaterialMapper.toDto(rawMaterialRepository.save(rawMaterial));
    }

    @Transactional
    @Override
    public void updateRawMaterialName(String rawMaterialId, String name) {
        rawMaterialRepository.updateRawMaterialName(UUID.fromString(rawMaterialId), name);
    }

    @Override
    public void deleteRawMaterialById(String rawMaterialId) {
        rawMaterialRepository.deleteById(UUID.fromString(rawMaterialId));
    }
}
