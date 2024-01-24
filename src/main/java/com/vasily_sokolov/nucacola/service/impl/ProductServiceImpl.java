package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import com.vasily_sokolov.nucacola.mapper.ProductMapper;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productMapper.productsToProductsDto(productRepository.getProductsByName(name));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.productsToProductsDto(productRepository.getAllProducts());
    }

    @Override
    public List<ProductDto> getProductsByCharacteristic(String characteristic) {
        return productMapper.productsToProductsDto(
                productRepository.getProductsByCharacteristic((ProductCharacteristic.valueOf(characteristic))));
    }

    @Override
    public List<ProductDto> getProductsByNameAndCharacteristic(String name, String characteristic) {
        return productMapper.productsToProductsDto(
                productRepository.getProductsByNameAndCharacteristic(
                        name,
                        ProductCharacteristic.valueOf(characteristic)
                ));
    }

    @Override
    public List<ProductDto> getProductsByCapacityAndCharacteristic(String capacityType, String characteristic) {
        return productMapper.productsToProductsDto(
                productRepository.getProductsByCapacityAndCharacteristic(
                        ProductCapacityType.valueOf(capacityType),
                        ProductCharacteristic.valueOf(characteristic)
                ));
    }

    @Override
    @Transactional
    public ProductDto postCreateProduct(ProductDto productDto) {
        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productPrice(productDto.getProductPrice())
                .characteristic(productDto.getCharacteristic())
                .capacityType(productDto.getCapacityType())
                .build();
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public void updateProductPrice(String productId, String productPrice) {
        productRepository.updateProductPrice(UUID.fromString(productId), Double.parseDouble(productPrice));
    }

    @Override
    public void deleteProductById(String productId) {
        productRepository.deleteById(UUID.fromString(productId));
    }
}
