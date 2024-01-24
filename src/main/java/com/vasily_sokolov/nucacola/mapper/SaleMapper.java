package com.vasily_sokolov.nucacola.mapper;


import com.vasily_sokolov.nucacola.dto.SaleDto;

import com.vasily_sokolov.nucacola.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;


@Mapper(componentModel = "spring", imports = {Date.class})
public interface SaleMapper {
    List<SaleDto> salesToSalesDto(List<Sale> sales);

    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
   // @Mapping(target = "saleDate",expression = "java(new Date(System.currentTimeMillis()))")
    SaleDto toDto(Sale sale);
}
