package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.Warehouse;
import com.vasily_sokolov.nucacola.repository.WarehouseRepository;
import com.vasily_sokolov.nucacola.service.interf.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public Warehouse getWarehouseById(String warehouseId) {
        return warehouseRepository.findById(Integer.valueOf(warehouseId)).orElse(null);
    }
}
