package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.Warehouse;
import com.vasily_sokolov.nucacola.exception.exceptions.WarehouseNotFoundException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
import com.vasily_sokolov.nucacola.repository.WarehouseRepository;
import com.vasily_sokolov.nucacola.service.interf.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    /**
     * The method finds a Warehouse in the database by id
     *
     * @param warehouseId Unique Product Identifier
     * @return If the Warehouse is found, it returns the Warehouse, otherwise it throws it away
     * WarehouseNotFoundException error.
     */
    @Override
    public Warehouse getWarehouseById(Integer warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotFoundException(ErrorMessage.WAREHOUSE_NOT_FOUND));
    }
}
 