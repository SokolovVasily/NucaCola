package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCharacteristic(String characteristic);

    List<ProductDto> getProductsByNameAndCharacteristic(String name, String characteristic);

    List<ProductDto> getProductsByCapacityAndCharacteristic(
            String capacityType, String characteristic);

    ProductDto postCreateProduct(ProductDto productDto);

    void updateProductPrice(String productId, String productPrice);

    void deleteProductById(String productId);


}
