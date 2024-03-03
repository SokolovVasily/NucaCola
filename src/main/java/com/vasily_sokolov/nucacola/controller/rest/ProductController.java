package com.vasily_sokolov.nucacola.controller.rest;

import com.vasily_sokolov.nucacola.dto.ErrorDto;
import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.service.interf.ProductService;
import com.vasily_sokolov.nucacola.validation.interf.Str45LengthCheck;
import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "ProductController", description = "Class for processing product requests")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Finds the product",
            description = "finds a product by UUID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = Product.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/productId/{productId}")
    public Product findById(
            @Schema(description = "Id of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @PathVariable("productId") String productId
    ) {
        return productService.findById(productId);
    }
    // http://localhost:8080/product/productId/8bc6ca0f-f8b8-4714-85b4-8b05e6cc4680

    @Operation(summary = "Finds the products",
            description = "Finds a products by name",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/name/{name}")
    public List<ProductDto> getProductsByName(
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @PathVariable("name") String name
    ) {
        return productService.getProductsByName(name);
    }
    // http://localhost:8080/product/name/Nuca-Cola
    // http://localhost:8080/product/name/Nuca-Cola%20zero

    @Operation(summary = "Finds the product",
            description = "Finds all products",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
            })
    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
    // http://localhost:8080/product/all

    @Operation(summary = "Finds the products",
            description = "Finds a product by characteristic",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/characteristic/{characteristic}")
    public List<ProductDto> getProductsByCharacteristic(
            @Schema(minLength = 1,
                    description = "Characteristic of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull @PathVariable("characteristic") String characteristic
    ) {
        return productService.getProductsByCharacteristic(characteristic);
    }
    // http://localhost:8080/product/characteristic/SUGARY
    // http://localhost:8080/product/characteristic/NOT_SUGARY

    @Operation(summary = "Finds the products",
            description = "Finds a product by name and characteristic",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/nameAndCharacteristic/")
    public List<ProductDto> getProductsByNameAndCharacteristic(
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @RequestParam String name,
            @Schema(minLength = 1,
                    description = "Characteristic of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull @RequestParam String characteristic
    ) {
        return productService.getProductsByNameAndCharacteristic(name, characteristic);
    }

    @Operation(summary = "Finds the products",
            description = "Finds a product by capacity and characteristic",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/capacityTypeAndCharacteristic/")
    public List<ProductDto> getProductsByCapacityAndCharacteristic(
            @Schema(minLength = 1,
                    description = "Capacity of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull @RequestParam String capacity,
            @Schema(minLength = 1,
                    description = "Characteristic of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull @RequestParam String characteristic
    ) {
        return productService.getProductsByCapacityAndCharacteristic(capacity, characteristic);
    }

    @Operation(summary = "Saves the productDto",
            description = "Stores ProductDto and returns ProductDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ProductDto data for saving",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @PostMapping("/create")
    public ProductDto postCreateProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.postCreateProduct(productDto);
    }

    @Operation(summary = "Change price of product",
            description = "Changing the price of a product through it id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })

    @PutMapping("/put")
    public void updateProductPrice(
            @Schema(description = "Id of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @RequestParam String productId,
            @Schema(minimum = "0.01", maximum = "999999.99",
                    description = "New price of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Positive(message = "Price must be greater than 0 !")
            @NotNull(message = "Product price shouldn`t be null !")
            @DecimalMin(value = "0.01", message = "Price should be greater than 0 ")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00 !")
            @RequestParam BigDecimal productPrice
    ) {
        productService.updateProductPrice(productId, productPrice);
    }

    @Operation(summary = "Delete of product",
            description = "Delete product through it id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @DeleteMapping("/delete/{productId}")
    public void deleteProductById(
            @Schema(description = "Id of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @PathVariable("productId") String productId
    ) {
        productService.deleteProductById(productId);
    }
}
