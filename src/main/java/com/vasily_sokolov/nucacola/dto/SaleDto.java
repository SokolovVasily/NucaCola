package com.vasily_sokolov.nucacola.dto;

import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Tag(name = "SaleDto", description = ("SaleDto class that has some fields from the entity sale and product"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    @Schema(minLength = 2, maxLength = 44,
            description = "Name of customer")
    @Size(min = 2, max = 44)
    @NotNull(message = "Customer name shouldn`t be null !")
    private String customerName;

    @Schema(description = "UUID of product")
    @UuidCheck
    private UUID productId;

    @Schema(minLength = 2, maxLength = 44,
            description = "Name of product")
    @Size(min = 2, max = 44)
    @NotNull(message = "Product name shouldn`t be null !")
    private String productName;
}
