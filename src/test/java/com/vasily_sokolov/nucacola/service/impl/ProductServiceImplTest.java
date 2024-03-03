package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import com.vasily_sokolov.nucacola.exception.exceptions.CapacityNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.CharacteristicNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.ListException;
import com.vasily_sokolov.nucacola.exception.exceptions.ProductNotFoundException;
import com.vasily_sokolov.nucacola.mapper.ProductMapper;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private final String uuid = "10d83df1-7247-4a7e-af09-96d418317ec2";

    private List<Product> productList;
    private List<ProductDto> productDtoList;

    private final List<Product> productListEmpty = new ArrayList<>();
    private final List<ProductDto> productDtoListEmpty = new ArrayList<>();

    @BeforeEach
    public void init() {
        productList = List.of(new Product());
        productDtoList = List.of(new ProductDto());
    }

    //------------------------------------------findById()--------------------------------------------------------------
    @Test
    void findByIdPositiveTest() {
        when(productRepository.findById(UUID.fromString(uuid)))
                .thenReturn(Optional.of(new Product()));
        assertEquals((new Product()), productService.findById(uuid));
        Mockito.verify(productRepository).findById(UUID.fromString(uuid));
    }

    @Test
    void findByIdProductNotFoundExceptionTest() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findById(uuid));
    }

    //------------------------------------------getProductsByName()-----------------------------------------------------
    @Test
    void getProductsByNamePositiveTest() {
        String name = "s";
        when(productRepository.getProductsByName(name))
                .thenReturn(productList);
        when(productMapper.productsToProductsDto(productList))
                .thenReturn(productDtoList);
        assertEquals(1, productService.getProductsByName(name).size());
        Mockito.verify(productRepository).getProductsByName(name);
        Mockito.verify(productMapper).productsToProductsDto(productList);
    }

    @Test
    void getProductsByNameListExceptionTest() {
        String name = "s";
        Assertions.assertThrows(ListException.class, () -> productService.getProductsByName(name).size());

    }

    //-----------------------------------------getAllProducts()---------------------------------------------------------
    @Test
    void getAllProductsPositiveTest() {
        when(productRepository.getAllProducts())
                .thenReturn(productList);
        when(productMapper.productsToProductsDto(productList))
                .thenReturn(productDtoList);
        assertEquals(1, productService.getAllProducts().size());
        Mockito.verify(productRepository).getAllProducts();
        Mockito.verify(productMapper).productsToProductsDto(productList);
    }

    @Test
    void getAllProductsListExceptionTest() {
        Assertions.assertThrows(ListException.class, () -> productService.getAllProducts().size());
    }

    //------------------------------------------getProductsByCharacteristic()-------------------------------------------
    @Test
    void getProductsByCharacteristicPositiveTest() {
        String characteristic = "sugary";
        when(productRepository
                .getProductsByCharacteristic(ProductCharacteristic.valueOf(characteristic.toUpperCase())))
                .thenReturn(productList);
        when(productMapper.productsToProductsDto(productList)).thenReturn(productDtoList);
        assertEquals(1, productService.getProductsByCharacteristic(characteristic).size());
        Mockito.verify(productRepository)
                .getProductsByCharacteristic(ProductCharacteristic.valueOf(characteristic.toUpperCase()));
        Mockito.verify(productMapper).productsToProductsDto(productList);
    }

    @Test
    void getProductsByCharacteristicCharacteristicNotFoundExceptionTest() {
        Assertions.assertThrows(CharacteristicNotFoundException.class, () ->
                productService.getProductsByCharacteristic("sugaryS")); //fehler
    }

    @Test
    void getProductsByCharacteristicListExceptionTest() {
        String characteristic = "sugary";

        when(productRepository
                .getProductsByCharacteristic(ProductCharacteristic.valueOf(characteristic.toUpperCase())))
                .thenReturn(productListEmpty);
        when(productMapper.productsToProductsDto(productListEmpty))
                .thenReturn(productDtoListEmpty);
        Assertions.assertThrows(ListException.class, () ->
                productService.getProductsByCharacteristic(characteristic));
        Mockito.verify(productRepository)
                .getProductsByCharacteristic(ProductCharacteristic.valueOf(characteristic.toUpperCase()));
        Mockito.verify(productMapper).productsToProductsDto(productListEmpty);
    }
    //------------------------------------------getProductsByNameAndCharacteristic()------------------------------------

    @Test
    void getProductsByNameAndCharacteristicPositiveTest() {
        String characteristic = "sugary";
        String name = "s";
        when(productRepository
                .getProductsByNameAndCharacteristic(name,
                        (ProductCharacteristic.valueOf(characteristic.toUpperCase()))))
                .thenReturn(productList);
        when(productMapper.productsToProductsDto(productList)).thenReturn(productDtoList);
        assertEquals(
                1, productService.getProductsByNameAndCharacteristic(name, (characteristic)).size());
        Mockito.verify(productRepository)
                .getProductsByNameAndCharacteristic((name), ProductCharacteristic.valueOf(characteristic.toUpperCase()));
        Mockito.verify(productMapper).productsToProductsDto(productList);
    }

    @Test
    void getProductsByNameAndCharacteristicCharacteristicNotFoundExceptionTest() {
        String characteristic = "sugaryS";
        String name = "s";
        Assertions.assertThrows(CharacteristicNotFoundException.class, () ->
                productService.getProductsByNameAndCharacteristic(name, characteristic));
    }

    @Test
    void getProductsByNameAndCharacteristicListExceptionTest() {
        String characteristic = "SUGARY";
        String name = "s";

        when(productRepository.getProductsByNameAndCharacteristic(
                name, ProductCharacteristic.valueOf(characteristic))).thenReturn(productListEmpty);
        when(productMapper.productsToProductsDto(productListEmpty)).thenReturn(productDtoListEmpty);
        Assertions.assertThrows(ListException.class,
                () -> productService.getProductsByNameAndCharacteristic(name, characteristic));
        Mockito.verify(productRepository).getProductsByNameAndCharacteristic(
                name, ProductCharacteristic.valueOf(characteristic));
        Mockito.verify(productMapper).productsToProductsDto(productListEmpty);
    }

    //-----------------------------------------getProductsByCapacityAndCharacteristic()---------------------------------
    @Test
    void getProductsByCapacityAndCharacteristic() {
        String characteristic = "sugary";
        String capacityType = "big";
        when(productRepository
                .getProductsByCapacityAndCharacteristic(ProductCapacityType.valueOf(capacityType.toUpperCase()),
                        (ProductCharacteristic.valueOf(characteristic.toUpperCase()))))
                .thenReturn(productList);
        when(productMapper.productsToProductsDto(productList)).thenReturn(productDtoList);
        assertEquals(
                1, productService.getProductsByCapacityAndCharacteristic(
                        ((capacityType.toUpperCase())), (characteristic)).size());
        Mockito.verify(productRepository)
                .getProductsByCapacityAndCharacteristic(ProductCapacityType.valueOf((capacityType.toUpperCase())),
                        ProductCharacteristic.valueOf(characteristic.toUpperCase()));
        Mockito.verify(productMapper).productsToProductsDto(productList);
    }

    @Test
    void getProductsByCapacityAndCharacteristicCapacityNotFoundExceptionTest() {
        String capacityType = "big!";
        String characteristic = "sugary";
        Assertions.assertThrows(CapacityNotFoundException.class, () ->
                productService.getProductsByCapacityAndCharacteristic(capacityType, characteristic));
    }

    @Test
    void getProductsByCapacityAndCharacteristicCharacteristicNotFoundExceptionTest() {
        String capacityType = "big";
        String characteristic = "sugaryS";
        Assertions.assertThrows(CharacteristicNotFoundException.class, () ->
                productService.getProductsByCapacityAndCharacteristic(capacityType, characteristic));
    }

    @Test
    void getProductsByCapacityAndCharacteristicListExceptionTest() {
        String capacityType = "BIG";
        String characteristic = "SUGARY";

        when(productRepository.getProductsByCapacityAndCharacteristic(
                ProductCapacityType.valueOf(capacityType),
                ProductCharacteristic.valueOf(characteristic)))
                .thenReturn(productListEmpty);
        when(productMapper.productsToProductsDto(productListEmpty)).thenReturn(productDtoListEmpty);

        Assertions.assertThrows(ListException.class,
                () -> productService.getProductsByCapacityAndCharacteristic(capacityType, characteristic));

        Mockito.verify(productRepository).getProductsByCapacityAndCharacteristic(
                ProductCapacityType.valueOf(capacityType),
                ProductCharacteristic.valueOf(characteristic));
        Mockito.verify(productMapper).productsToProductsDto(productListEmpty);
    }
}
