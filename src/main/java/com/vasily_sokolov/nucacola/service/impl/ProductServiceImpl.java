package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import com.vasily_sokolov.nucacola.exception.exceptions.CapacityNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.CharacteristicNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.ListException;
import com.vasily_sokolov.nucacola.exception.exceptions.ProductNotFoundException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
import com.vasily_sokolov.nucacola.mapper.ProductMapper;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Product findById(String productId) {
        return productRepository.findById(UUID.fromString(productId)).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    /**
     * The method finds a product in the database by name;
     *
     * @param name Product Identifier
     * @return If the products is found, it returns the List<{@link ProductDto}> or empty List.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getProductsByName(String name) {
        List<ProductDto> productDtoList =
                productMapper.productsToProductsDto(
                        productRepository.getProductsByName(name));
        if (productDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.PRODUCT_NOT_FOUND);
        } else {
            return productDtoList;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtoList =
                productMapper.productsToProductsDto(
                        productRepository.getAllProducts());
        if (productDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.PRODUCT_NOT_FOUND);
        } else {
            return productDtoList;
        }
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getProductsByCharacteristic(String characteristic) {
        String characteristicUpperCase = characteristic.toUpperCase();
        if (!checkCharacteristic(characteristicUpperCase)) {
            throw new CharacteristicNotFoundException(ErrorMessage.CHARACTERISTIC_NOT_FOUND);
        }
        List<ProductDto> productDtoList = productMapper.productsToProductsDto(
                productRepository.getProductsByCharacteristic((ProductCharacteristic.valueOf(characteristicUpperCase))));
        if (productDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.PRODUCT_NOT_FOUND);
        } else {
            return productDtoList;
        }
    }


    /**
     * The method finds a product in the database by name and characteristic;
     * <p>
     * The characteristic parameter is converted to upper case and transmitted in
     * {@link ProductServiceImpl#checkCharacteristic(String)} method,
     * if FALSE is returned, a CharacteristicNotFoundException error is thrown.
     *
     * @param name           Product Identifier,
     * @param characteristic Product Identifier;
     * @return If the products is found, it returns the List<{@link ProductDto}> or empty List.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getProductsByNameAndCharacteristic(String name, String characteristic) {
        String characteristicUpperCase = characteristic.toUpperCase();
        if (!checkCharacteristic(characteristicUpperCase)) {
            throw new CharacteristicNotFoundException(ErrorMessage.CHARACTERISTIC_NOT_FOUND);
        }
        List<ProductDto> productDtoList = productMapper.productsToProductsDto(
                productRepository.getProductsByNameAndCharacteristic(
                        name,
                        ProductCharacteristic.valueOf(characteristicUpperCase)));
        if (productDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.PRODUCT_NOT_FOUND);
        } else {
            return productDtoList;
        }
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
        List<ProductDto> productDtoList = productMapper.productsToProductsDto(
                productRepository.getProductsByCapacityAndCharacteristic(
                        ProductCapacityType.valueOf(capacityUpperCase),
                        ProductCharacteristic.valueOf(characteristicUpperCase)));
        if (productDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.PRODUCT_NOT_FOUND);
        } else {
            return productDtoList;
        }
    }

    /**
     * The method create a product in the database ;
     *
     * @param productDto
     * @return If the products is creat, it returns the List<{@link ProductDto}> or empty List.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateProductPrice(String productId, BigDecimal productPrice) {
        findById(productId);
        productRepository.updateProductPrice(UUID.fromString(productId), productPrice);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteProductById(String productId) {
        findById(productId);
        productRepository.deleteById(UUID.fromString(productId));
    }


    private boolean checkCharacteristic(String characteristicUpperCase) {
        return ProductCharacteristic.getProductCharacteristicList().contains(characteristicUpperCase);
    }

    private boolean checkCapacityType(String capacityUpperCase) {
        return ProductCapacityType.getProductCapacityTypeList().contains(capacityUpperCase);
    }
}
