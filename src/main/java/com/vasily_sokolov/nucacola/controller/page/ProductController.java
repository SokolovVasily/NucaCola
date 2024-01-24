package com.vasily_sokolov.nucacola.controller.page;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/name/{name}")    //("/{не отображается  в запросе}")
    public List<ProductDto> getProductsByName(@PathVariable("name") String name) {
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
    public List<ProductDto> getProductByCharacteristic(@PathVariable("characteristic") String characteristic) {
        return productService.getProductsByCharacteristic(characteristic);
    }
    // http://localhost:8080/product/characteristic/SUGARY
    // http://localhost:8080/product/characteristic/NOT_SUGARY

    @GetMapping("/nameAndCharacteristic/")
    public List<ProductDto> getProductsByNameAndCharacteristic(
            @RequestParam String name,
            @RequestParam String characteristic
    ) {
        return productService.getProductsByNameAndCharacteristic(name, characteristic);
    }

    @GetMapping("/capacityTypeAndCharacteristic/")
    public List<ProductDto> getProductsByCapacityAndCharacteristic(
            @RequestParam String capacity,
            @RequestParam String characteristic
    ) {
        return productService.getProductsByCapacityAndCharacteristic(capacity, characteristic);
    }

    @PostMapping("/create")
    public ProductDto postCreateProduct(@RequestBody ProductDto productDto) {
        return productService.postCreateProduct(productDto);
    }

    @PutMapping("/put")
    public void updateProductPrice(
            @RequestParam String productId,
            @RequestParam String productPrice
    ) {
        productService.updateProductPrice(productId, productPrice);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProductById(@PathVariable("productId") String productId) {
        productService.deleteProductById(productId);
    }
}
