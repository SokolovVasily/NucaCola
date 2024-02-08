package com.vasily_sokolov.nucacola.controller.rest;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import com.vasily_sokolov.nucacola.validation.interf.Str45LengthCheck;
import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/productId/{productId}")
    public Product findById(@UuidCheck @PathVariable("productId") String productId) { //оба метода валидации кастомн
        return productService.findById(productId);
    }
    // http://localhost:8080/product/productId/8bc6ca0f-f8b8-4714-85b4-8b05e6cc4680


    @GetMapping("/name/{name}")
    public List<ProductDto> getProductsByName(
            @Str45LengthCheck @PathVariable("name") String name
    ) {
        return productService.getProductsByName(name);
    }
    // http://localhost:8080/product/name/Nuca-Cola
    // http://localhost:8080/product/name/Nuca-Cola%20zero

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
    // http://localhost:8080/product/all

    @GetMapping("/characteristic/{characteristic}")
    public List<ProductDto> getProductByCharacteristic(@NotNull @PathVariable("characteristic") String characteristic) {
        return productService.getProductsByCharacteristic(characteristic);
    }
    // http://localhost:8080/product/characteristic/SUGARY
    // http://localhost:8080/product/characteristic/NOT_SUGARY

    @GetMapping("/nameAndCharacteristic/")
    public List<ProductDto> getProductsByNameAndCharacteristic(
            @Str45LengthCheck @RequestParam String name,
            @NotNull @RequestParam String characteristic
    ) {
        return productService.getProductsByNameAndCharacteristic(name, characteristic);
    }

    @GetMapping("/capacityTypeAndCharacteristic/")
    public List<ProductDto> getProductsByCapacityAndCharacteristic(
            @NotNull @RequestParam String capacity,
            @NotNull @RequestParam String characteristic
    ) {
        return productService.getProductsByCapacityAndCharacteristic(capacity, characteristic);
    }

    @PostMapping("/create")
    public ProductDto postCreateProduct(@RequestBody ProductDto productDto) {
        return productService.postCreateProduct(productDto);
    }

    @PutMapping("/put")
    public void updateProductPrice(
            @UuidCheck @RequestParam String productId,
            @Positive(message = "Price must be greater than 0 !")
            @NotNull(message = "Product price shouldn`t be null !")
            @DecimalMin(value = "0.01", message = "Price should be greater than 0 ")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00 !")
            @RequestParam BigDecimal productPrice
    ) {
        productService.updateProductPrice(productId, productPrice);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProductById(@UuidCheck @PathVariable("productId") String productId) {
        productService.deleteProductById(productId);
    }
}
