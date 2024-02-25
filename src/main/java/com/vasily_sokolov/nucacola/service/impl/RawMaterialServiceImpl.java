package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.entity.RawMaterial;
import com.vasily_sokolov.nucacola.entity.Supplier;
import com.vasily_sokolov.nucacola.exception.exceptions.ListException;
import com.vasily_sokolov.nucacola.exception.exceptions.RawMaterialNotFoundException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
import com.vasily_sokolov.nucacola.mapper.RawMaterialMapper;
import com.vasily_sokolov.nucacola.repository.RawMaterialRepository;
import com.vasily_sokolov.nucacola.repository.SupplierRepository;
import com.vasily_sokolov.nucacola.service.interf.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImpl implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    private final SupplierRepository supplierRepository;

    private final RawMaterialMapper rawMaterialMapper;


    /**
     * The method finds a rawMaterial in the database by id;
     *
     * @param rawMaterialId Unique rawMaterial Identifier
     * @return If the rawMaterial is found, it returns the rawMaterial, otherwise it throws it away
     * RawMaterialNotFoundException error
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RawMaterial findById(String rawMaterialId) {
        return rawMaterialRepository.findById(UUID.fromString(rawMaterialId)).orElseThrow(
                () -> new RawMaterialNotFoundException(ErrorMessage.RAW_MATERIAL_NOT_FOUND));
    }

    /**
     * The method finds a RawMaterial in the database by name;
     *
     * @param name RawMaterial Identifier
     * @return If the rawMaterial is found, it returns the List<{@link RawMaterialDto}> or empty List
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<RawMaterialDto> getRawMaterialsByName(String name) {
        List<RawMaterialDto> rawMaterialDtoList =
                rawMaterialMapper.rawMaterialToRawMaterialDto(rawMaterialRepository.getRawMaterialsByName(name));
        if (rawMaterialDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.RAW_MATERIAL_NOT_FOUND);
        } else {
            return rawMaterialDtoList;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<RawMaterialDto> getAllRawMaterials() {
        List<RawMaterialDto> rawMaterialDtoList =
                rawMaterialMapper.rawMaterialToRawMaterialDto(rawMaterialRepository.findAll());
        if (rawMaterialDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.RAW_MATERIAL_NOT_FOUND);
        } else {
            return rawMaterialDtoList;
        }
    }

    /**
     * @param rawMaterialDto
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public RawMaterialDto postCreateRawMaterial(RawMaterialDto rawMaterialDto) {
        Supplier supplier = supplierRepository.getSupplierByName(rawMaterialDto.getSupplierName());
        RawMaterial rawMaterial = RawMaterial.builder()
                .rawMaterialName(rawMaterialDto.getRawMaterialName())
                .supplier(supplier)
                .build();
        return rawMaterialMapper.toDto(rawMaterialRepository.save(rawMaterial));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateRawMaterialName(String rawMaterialId, String name) {
        findById(rawMaterialId);
        rawMaterialRepository.updateRawMaterialName(UUID.fromString(rawMaterialId), name);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteRawMaterialById(String rawMaterialId) {
        findById(rawMaterialId);
        rawMaterialRepository.deleteById(UUID.fromString(rawMaterialId));
    }
}
