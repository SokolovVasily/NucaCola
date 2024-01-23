package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.RawMaterial;
import com.vasily_sokolov.nucacola.repository.RawMaterialRepository;
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

    @Override
    public List<RawMaterial> getRawMaterialsByName(String name) {
        return rawMaterialRepository.getRawMaterialsByName(name);
    }

    @Override
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }

    @Override
    public RawMaterial postCreateRawMaterial(RawMaterial rawMaterial) {
        return rawMaterialRepository.save(rawMaterial);
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
