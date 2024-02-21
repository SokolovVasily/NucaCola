package com.vasily_sokolov.nucacola.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialDto {

    @NotNull(message = "RawMaterial name shouldn`t be null !")
    @Size(min = 2, max = 44)
    private String rawMaterialName;

    @NotNull(message = "Supplier name shouldn`t be null !")
    @Size(min = 2, max = 44)
    private String supplierName;

}
