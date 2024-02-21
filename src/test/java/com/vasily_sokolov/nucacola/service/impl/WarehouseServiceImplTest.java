package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.Warehouse;
import com.vasily_sokolov.nucacola.repository.WarehouseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceImplTest {

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;


    private final String warehouseId = "1";

    @Test
    void getWarehouseByIdPositiveTest() {
        Mockito.when(warehouseRepository.findById(Integer.valueOf(warehouseId)))
                .thenReturn(Optional.of(new Warehouse()));
        Assertions.assertEquals((new Warehouse()), warehouseService.getWarehouseById(Integer.valueOf(warehouseId)));
        Mockito.verify(warehouseRepository).findById(Integer.valueOf(warehouseId));
    }
}