package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByName(String name);

    List<Product> getAllProducts();

    List<Product> getProductsByCharacteristic(String characteristic);

    Product postCreateProduct(Product product);

    void updateProductPrice(String productId, String productPrice);

    void deleteProductById(String productId);
}
