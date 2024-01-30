package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import com.vasily_sokolov.nucacola.exception.exceptions.CapacityNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.CharacteristicNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.ProductNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.StringNotCorrectException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
import com.vasily_sokolov.nucacola.mapper.ProductMapper;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * The method finds a product in the database by id;
     *
     * @param productId Unique Product Identifier
     * @return If the product is found, it returns the product, otherwise it throws it away
     * ProductNotFoundException error.
     */
    @Override
    public Product findById(String productId) {
        return productRepository.findById(UUID.fromString(productId)).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    /**
     * The method finds a product in the database by name;
     * In this method, for the name parameter, a check occurs in the
     * {@link ProductServiceImpl#checkString45Length(String)} method,
     * when return FALSE a StringNotCorrectException error is thrown.
     *
     * @param name Product Identifier
     * @return If the products is found, it returns the List<{@link ProductDto}> or empty List.
     */
    @Override
    public List<ProductDto> getProductsByName(String name) {
        if (!checkString45Length(name)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        return productMapper.productsToProductsDto(productRepository.getProductsByName(name));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.productsToProductsDto(productRepository.getAllProducts());
    }

    /**
     * The method finds a products in the database by characteristic;
     * The characteristic parameter is converted to upper case and transmitted in
     * {@link ProductServiceImpl#checkCharacteristic(String)} method,
     * if FALSE is returned, a CharacteristicNotFoundException error is thrown.
     *
     * @param characteristic Product Identifier
     * @return If the products is found, it returns the List<{@link ProductDto}> or empty List.
     */
    @Override
    public List<ProductDto> getProductsByCharacteristic(String characteristic) {
        String characteristicUpperCase = characteristic.toUpperCase();
        if (!checkCharacteristic(characteristicUpperCase)) {
            throw new CharacteristicNotFoundException(ErrorMessage.CHARACTERISTIC_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getProductsByCharacteristic((ProductCharacteristic.valueOf(characteristicUpperCase))));
    }

    /**
     * The method finds a product in the database by name and characteristic;
     * In this method, for the name parameter, a check occurs in the
     * {@link ProductServiceImpl#checkString45Length(String)} method,
     * when return FALSE a StringNotCorrectException error is thrown.
     * <p>
     * Next, the characteristic parameter is converted to upper case and transmitted in
     * {@link ProductServiceImpl#checkCharacteristic(String)} method,
     * if FALSE is returned, a CharacteristicNotFoundException error is thrown.
     *
     * @param name           Product Identifier,
     * @param characteristic Product Identifier;
     * @return If the products is found, it returns the List<{@link ProductDto}> or empty List.
     */
    @Override
    public List<ProductDto> getProductsByNameAndCharacteristic(String name, String characteristic) {
        if (!checkString45Length(name)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        String characteristicUpperCase = characteristic.toUpperCase();
        if (!checkCharacteristic(characteristicUpperCase)) {
            throw new CharacteristicNotFoundException(ErrorMessage.CHARACTERISTIC_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getProductsByNameAndCharacteristic(
                        name,
                        ProductCharacteristic.valueOf(characteristicUpperCase)));
    }


    /**
     * The method finds a product in the database by capacity and characteristic;
     * The capacityType parameter is converted to upper case and transmitted in
     * {@link ProductServiceImpl#checkCapacityType(String)}  method,
     * if FALSE is returned, a CapacityNotFoundException error is thrown.
     * Next, the characteristic parameter is converted to upper case and transmitted in
     * {@link ProductServiceImpl#checkCharacteristic(String)} method,
     * if FALSE is returned, a CharacteristicNotFoundException error is thrown.
     *
     * @param capacityType   Product Identifier,
     * @param characteristic Product Identifier;
     * @return If the products is found, it returns the List<{@link ProductDto}> or empty List.
     */
    @Override
    public List<ProductDto> getProductsByCapacityAndCharacteristic(String capacityType, String characteristic) {
        String capacityUpperCase = capacityType.toUpperCase();
        if (!checkCapacityType(capacityUpperCase)) {
            throw new CapacityNotFoundException(ErrorMessage.CAPACITY_NOT_FOUND);
        }
        String characteristicUpperCase = characteristic.toUpperCase();
        if (!checkCharacteristic(characteristicUpperCase)) {
            throw new CharacteristicNotFoundException(ErrorMessage.CHARACTERISTIC_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getProductsByCapacityAndCharacteristic(
                        ProductCapacityType.valueOf(capacityUpperCase),
                        ProductCharacteristic.valueOf(characteristicUpperCase)));
    }

    @Override
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
    public void updateProductPrice(String productId, BigDecimal productPrice) {
        findById(productId);
        productRepository.updateProductPrice(UUID.fromString(productId), productPrice);
    }

    @Override
    public void deleteProductById(String productId) {
        findById(productId);
        productRepository.deleteById(UUID.fromString(productId));
    }

    /**
     *This method checks the length of the Product name against the condition:
     * greater than 1 and less than 45 characters, and returns boolean.
     *
     * @param name
     * @return TRUE or FALSE
     */
    private boolean checkString45Length(String name) {
        return name.length() > 1 && name.length() < 45;
    }

    private boolean checkCharacteristic(String characteristicUpperCase) {
        return ProductCharacteristic.getProductCharacteristicList().contains(characteristicUpperCase);
    }

    private boolean checkCapacityType(String capacityUpperCase) {
        return ProductCapacityType.getProductCapacityTypeList().contains(capacityUpperCase);
    }
}
