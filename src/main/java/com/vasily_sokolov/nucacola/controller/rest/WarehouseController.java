package com.vasily_sokolov.nucacola.controller.rest;

import com.vasily_sokolov.nucacola.dto.ErrorDto;
import com.vasily_sokolov.nucacola.entity.Warehouse;
import com.vasily_sokolov.nucacola.service.interf.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WarehouseController", description = "Class for processing warehouse requests")
@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Operation(summary = "Finds the warehouse",
            description = "finds a warehouse by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = Warehouse.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/warehouseId/{warehouseId}")
    public Warehouse getWarehouseById(
            @Schema(description = "Id of warehouse",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull @Positive @PathVariable("warehouseId") Integer warehouseId
    ) {
        return warehouseService.getWarehouseById(warehouseId);
    }
    // http://localhost:8080/warehouse/warehouseId/1
    // http://localhost:8080/warehouse/warehouseId/2
}
