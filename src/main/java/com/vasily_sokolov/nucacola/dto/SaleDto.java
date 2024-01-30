package com.vasily_sokolov.nucacola.dto;

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

    private String customerName;
    private UUID productId;
    private String productName;

}
