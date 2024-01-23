package com.vasily_sokolov.nucacola.controller.page;


import com.vasily_sokolov.nucacola.entity.RawMaterial;
import com.vasily_sokolov.nucacola.service.interf.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rawMaterial")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @GetMapping("/name/{name}")    //("/{не отображается  в запросе}")
    public List<RawMaterial> getRawMaterialsByName(@PathVariable("name") String name) {
        return rawMaterialService.getRawMaterialsByName(name);
    }

    @GetMapping("/all")
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialService.getAllRawMaterials();
    }

    @PostMapping("/create")
    public RawMaterial postCreateRawMaterial(@RequestBody RawMaterial rawMaterial) {
        return rawMaterialService.postCreateRawMaterial(rawMaterial);
    }

    @PutMapping("/put")
    public void updateRawMaterialName(
            @RequestParam String rawMaterialId,
            @RequestParam String name
    ) {
        rawMaterialService.updateRawMaterialName(rawMaterialId, name);
    }

    @DeleteMapping("/delete/{rawMaterialId}")
    public void deleteRawMaterialById(@PathVariable("rawMaterialId") String rawMaterialId) {
        rawMaterialService.deleteRawMaterialById(rawMaterialId);
    }
}
