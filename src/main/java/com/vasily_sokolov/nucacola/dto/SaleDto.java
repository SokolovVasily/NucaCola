package com.vasily_sokolov.nucacola.dto;

import com.vasily_sokolov.nucacola.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    private String customerName;
    private UUID productId;
    private String productName;

}
