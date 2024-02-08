package com.vasily_sokolov.nucacola.controller.rest;


import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.service.interf.RawMaterialService;
import com.vasily_sokolov.nucacola.validation.interf.Str45LengthCheck;
import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rawMaterial")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @GetMapping("/name/{name}")
    public List<RawMaterialDto> getRawMaterialsByName(@Str45LengthCheck @PathVariable("name") String name) {
        return rawMaterialService.getRawMaterialsByName(name);
    }

    @GetMapping("/all")
    public List<RawMaterialDto> getAllRawMaterials() {
        return rawMaterialService.getAllRawMaterials();
    }

    @PostMapping("/create")
    public RawMaterialDto postCreateRawMaterial(@RequestBody RawMaterialDto rawMaterialDto) {
        return rawMaterialService.postCreateRawMaterial(rawMaterialDto);
    }

    @PutMapping("/put")
    public void updateRawMaterialName(
            @UuidCheck @RequestParam String rawMaterialId,
            @Str45LengthCheck @RequestParam String name
    ) {
        rawMaterialService.updateRawMaterialName(rawMaterialId, name);
    }

    @DeleteMapping("/delete/{rawMaterialId}")
    public void deleteRawMaterialById(@UuidCheck @PathVariable("rawMaterialId") String rawMaterialId) {
        rawMaterialService.deleteRawMaterialById(rawMaterialId);
    }
}
