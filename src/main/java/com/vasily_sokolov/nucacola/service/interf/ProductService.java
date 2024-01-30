package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product findById(String productId);

    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCharacteristic(String characteristic);

    List<ProductDto> getProductsByNameAndCharacteristic(String name, String characteristic);

    List<ProductDto> getProductsByCapacityAndCharacteristic(
            String capacityType, String characteristic);

    ProductDto postCreateProduct(ProductDto productDto);

    void updateProductPrice(String productId, BigDecimal productPrice);

    void deleteProductById(String productId);


}
