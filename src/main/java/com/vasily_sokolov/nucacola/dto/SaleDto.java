package com.vasily_sokolov.nucacola.dto;

import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {


    @Size(min = 2, max = 44)
    @NotNull(message = "Customer name shouldn`t be null !")
    private String customerName;

    @UuidCheck
    private UUID productId;

    @Size(min = 2, max = 44)
    @NotNull(message = "Product name shouldn`t be null !")
    private String productName;

}
