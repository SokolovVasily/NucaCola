package com.vasily_sokolov.nucacola.dto;

import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Tag(name = "ProductDto", description = ("ProductDto class that has some fields from the entity product"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @Schema(minLength = 2, maxLength = 44,
            description = "Name of product")
    @Size(min = 2, max = 44)
    @NotNull(message = "Product name shouldn`t be null !")
    private String productName;

    @Schema(minimum = "0.01", maximum = "999999.99", description = "Price of product")
    @Positive(message = "Price must be greater than 0 !")
    @NotNull(message = "Product price shouldn`t be null !")
    @DecimalMin(value = "0.01", message = "Price should be greater than 0 ")
    @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00 !")
    private BigDecimal productPrice;

    @Schema(minLength = 1, description = "Capacity type of product")
    @NotNull(message = "Product capacity shouldn`t be null !")
    private ProductCapacityType capacityType;

    @Schema(minLength = 1, description = "Characteristic of product")
    @NotNull(message = "Product characteristic shouldn`t be null !")
    private ProductCharacteristic characteristic;
}
