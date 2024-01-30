package com.vasily_sokolov.nucacola.mapper;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<ProductDto>productsToProductsDto(List<Product> products);
    ProductDto toDto(Product product);

}
