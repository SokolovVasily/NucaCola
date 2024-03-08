package com.vasily_sokolov.nucacola.controller.rest;

import com.vasily_sokolov.nucacola.dto.ErrorDto;
import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.service.interf.RawMaterialService;
import com.vasily_sokolov.nucacola.validation.interf.Str45LengthCheck;
import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "RawMaterialController", description = "Class for processing raw material requests")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rawMaterial")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @Operation(summary = "Finds the rawMaterials",
            description = "Finds a rawMaterials by name",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = RawMaterialDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because rawMaterial is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/name/{name}")
    public List<RawMaterialDto> getRawMaterialsByName(
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of RawMaterial",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @PathVariable("name") String name
    ) {
        return rawMaterialService.getRawMaterialsByName(name);
    }

    @Operation(summary = "Finds the rawMaterials",
            description = "Finds all rawMaterials",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = RawMaterialDto.class),
                                    mediaType = "application/json")}),
            })
    @GetMapping("/all")
    public List<RawMaterialDto> getAllRawMaterials() {
        return rawMaterialService.getAllRawMaterials();
    }

    @Operation(summary = "Saves the rawMaterialDto",
            description = "Stores RawMaterialDto and returns RawMaterialDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "RawMaterialDto data for saving",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RawMaterialDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = RawMaterialDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "return Error Message user is not autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @PostMapping("/create")
    public RawMaterialDto postCreateRawMaterial(@Valid @RequestBody RawMaterialDto rawMaterialDto) {
        return rawMaterialService.postCreateRawMaterial(rawMaterialDto);
    }

    @Operation(summary = "Change name of rawMaterial",
            description = "Changing the name of a rawMaterial through it id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "return Error Message user is not autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because rawMaterial is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @PutMapping("/put")
    public void updateRawMaterialName(
            @Schema(description = "Id of rawMaterial",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @RequestParam String rawMaterialId,
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of rawMaterial",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @RequestParam String name
    ) {
        rawMaterialService.updateRawMaterialName(rawMaterialId, name);
    }

    @Operation(summary = "Delete of rawMaterial",
            description = "Delete rawMaterial through it id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "return Error Message user is not autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because rawMaterial is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @DeleteMapping("/delete/{rawMaterialId}")
    public void deleteRawMaterialById(
            @Schema(description = "Id of rawMaterial",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @PathVariable("rawMaterialId") String rawMaterialId
    ) {
        rawMaterialService.deleteRawMaterialById(rawMaterialId);
    }
}
