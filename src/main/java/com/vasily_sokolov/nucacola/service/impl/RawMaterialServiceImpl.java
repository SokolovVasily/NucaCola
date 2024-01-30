package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.entity.RawMaterial;
import com.vasily_sokolov.nucacola.entity.Supplier;
import com.vasily_sokolov.nucacola.exception.exceptions.RawMaterialNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.StringNotCorrectException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
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


    /**
     * The method finds a rawMaterial in the database by id;
     * @param rawMaterialId Unique rawMaterial Identifier
     * @return If the rawMaterial is found, it returns the rawMaterial, otherwise it throws it away
     * RawMaterialNotFoundException error
     */
    public RawMaterial findById(String rawMaterialId) {
        return rawMaterialRepository.findById(UUID.fromString(rawMaterialId)).orElseThrow(
                () -> new RawMaterialNotFoundException(ErrorMessage.RAW_MATERIAL_NOT_FOUND));
    }

    /**
     * The method finds a RawMaterial in the database by name;
     * In this method, for the name parameter, a check occurs in the
     * {@link RawMaterialServiceImpl#checkString45Length(String)} method,
     * when return FALSE a StringNotCorrectException error is thrown.
     *
     * @param name RawMaterial Identifier
     * @return If the rawMaterial is found, it returns the List<{@link RawMaterialDto}> or empty List
     */
    @Override
    public List<RawMaterialDto> getRawMaterialsByName(String name) {
        if (!checkString45Length(name)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
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
        findById(rawMaterialId);
        if (!checkString45Length(name)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        rawMaterialRepository.updateRawMaterialName(UUID.fromString(rawMaterialId), name);
    }

    @Override
    public void deleteRawMaterialById(String rawMaterialId) {
        findById(rawMaterialId);
        rawMaterialRepository.deleteById(UUID.fromString(rawMaterialId));
    }

    private boolean checkString45Length(String name) {
        return name.length() > 1 && name.length() < 45;
    }

}