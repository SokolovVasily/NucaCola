package com.vasily_sokolov.nucacola.controller.page;

import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/name/{name}")    //("/{не отображается  в запросе}")
    public List<Product> getProductsByName(@PathVariable("name") String name) {
        return productService.getProductsByName(name);
    }
    // http://localhost:8080/product/name/Nuca-Cola
    // http://localhost:8080/product/name/Nuca-Cola%20zero

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    // http://localhost:8080/product/all

    @GetMapping("/characteristic/{characteristic}")
    public List<Product> getProductByCharacteristic(@PathVariable("characteristic") String characteristic) {
        return productService.getProductsByCharacteristic(characteristic);
    }
    // http://localhost:8080/product/characteristic/SUGARY
    // http://localhost:8080/product/characteristic/NOT_SUGARY

}
