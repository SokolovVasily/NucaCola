package com.vasily_sokolov.nucacola.dto;

import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @Size(min = 2, max = 44)
    @NotNull(message = "Product name shouldn`t be null !")
    private String productName;

    @Positive(message = "Price must be greater than 0 !")
    @NotNull(message = "Product price shouldn`t be null !")
    @DecimalMin(value = "0.01", message = "Price should be greater than 0 ")
    @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00 !")
    private BigDecimal productPrice;

    @NotNull(message = "Product capacity shouldn`t be null !")
    private ProductCapacityType capacityType;

    @NotNull(message = "Product characteristic shouldn`t be null !")
    private ProductCharacteristic characteristic;
}
