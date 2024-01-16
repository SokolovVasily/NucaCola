package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.getProductsByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<Product> getProductsByCharacteristic(String characteristic) {
        return productRepository.getProductsByCharacteristic((ProductCharacteristic.valueOf(characteristic)));
    }


}
