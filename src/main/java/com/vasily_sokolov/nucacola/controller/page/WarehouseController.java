package com.vasily_sokolov.nucacola.controller.page;

import com.vasily_sokolov.nucacola.entity.Warehouse;
import com.vasily_sokolov.nucacola.service.interf.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouseId/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable("warehouseId")String warehouseId){
        return warehouseService.getWarehouseById(warehouseId);
    }
    // http://localhost:8080/warehouse/warehouseId/1
    // http://localhost:8080/warehouse/warehouseId/2


}
