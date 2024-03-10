package com.vasily_sokolov.nucacola.controller.rest;

import com.vasily_sokolov.nucacola.dto.ErrorDto;
import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.dto.SaleDto;
import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.service.interf.SaleService;
import com.vasily_sokolov.nucacola.validation.interf.Str45LengthCheck;
import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SaleController", description = "Class for processing sale requests")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    @Operation(summary = "Finds the sale",
            description = "finds a sale by UUID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = Sale.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because sale is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/saleId/{saleId}")
    public SaleDto getSaleById(@UuidCheck @PathVariable("saleId") String saleId) {
        return saleService.getSaleById(saleId);
    }
    // http://localhost:8080/sale/saleId/1
    // http://localhost:8080/sale/saleId/2

    @Operation(summary = "Finds the sales",
            description = "Finds a sales by name",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = SaleDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because sale is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/customerName/{customerName}")
    public List<SaleDto> getSalesByCustomerName(
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of customer",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @PathVariable("customerName") String customerName
    ) {
        return saleService.getSalesByCustomerName(customerName);
    }
    // http://localhost:8080/sale/customerName/Supermarket 1

    @Operation(summary = "Finds the sales",
            description = "Finds all sales",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = SaleDto.class),
                                    mediaType = "application/json")}),
            })
    @GetMapping("/all")
    public List<SaleDto> getAllSales() {
        return saleService.getAllSales();
    }

    @Operation(summary = "Finds the sales",
            description = "Finds a sales by customerName and productName",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = SaleDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because sale is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @GetMapping("/CustomerNameAndProductName/")
    public List<SaleDto> getSalesByCustomerNameAndProductName(
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of customer",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @RequestParam String customerName,
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of product",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @RequestParam String productName
    ) {
        return saleService.getSalesByCustomerNameAndProductName(customerName, productName);
    }

    @Operation(summary = "Saves the saleDto",
            description = "Stores SaleDto and returns Sale",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "SaleDto data for saving",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = SaleDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "return Error Message user is not autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @PostMapping("/create")
    public Sale postCreateNewSale(@RequestBody @Valid SaleDto saleDto) {
        return saleService.postCreateNewSale(saleDto);
    }

    @Operation(summary = "Change name customer of sale",
            description = "Changing the name customer of a sale through it id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "return Error Message user is not autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because sale is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @PutMapping("/put")
    public void updateSaleCustomerName(
            @Schema(description = "Id of sale",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @RequestParam String saleId,
            @Schema(minLength = 2, maxLength = 44,
                    description = "Name of customer",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @Str45LengthCheck @RequestParam String customerName
    ) {
        saleService.updateSaleCustomerName(saleId, customerName);
    }

    @Operation(summary = "Delete of sale",
            description = "Delete sale through it id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "return Error Message user is not autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "return Error Message because sale is not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because parameter is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @DeleteMapping("/delete/{saleId}") //delete/b5310470-4943-4718-8899-2329a4dec393
    public void deleteSaleById(
            @Schema(description = "Id of sale",
                    requiredMode = Schema.RequiredMode.REQUIRED)
            @UuidCheck @PathVariable("saleId") String saleId
    ) {
        saleService.deleteSaleById(saleId);
    }
}
