package com.vasily_sokolov.nucacola.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Tag(name = "RawMaterialDto", description = ("RawMaterialDto class that has some fields from the entity rawMaterial"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialDto {

    @Schema(minLength = 2, maxLength = 44,
            description = "Name of rawMaterial")
    @NotNull(message = "RawMaterial name shouldn`t be null !")
    @Size(min = 2, max = 44)
    private String rawMaterialName;

    @Schema(minLength = 2, maxLength = 44,
            description = "Name of supplier")
    @NotNull(message = "Supplier name shouldn`t be null !")
    @Size(min = 2, max = 44)
    private String supplierName;
}
