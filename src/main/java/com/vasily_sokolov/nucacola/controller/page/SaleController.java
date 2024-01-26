package com.vasily_sokolov.nucacola.controller.page;


import com.vasily_sokolov.nucacola.dto.SaleDto;

import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.service.interf.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    @GetMapping("/saleId/{saleId}")
    public SaleDto getSaleById(@PathVariable("saleId") String saleId) {
        return saleService.getSaleById(saleId);
    }
    // http://localhost:8080/sale/saleId/1
    // http://localhost:8080/sale/saleId/2

    @GetMapping("/customerName/{customerName}")
    public List<SaleDto> getSalesByCustomerName(@PathVariable("customerName") String customerName) {
        return saleService.getSalesByCustomerName(customerName);
    }
    // http://localhost:8080/sale/customerName/Supermarket 1

    @GetMapping("/all")
    public List<SaleDto> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/CustomerNameAndProductName/")
    public List<SaleDto> getSalesByCustomerNameAndProductName(
            @RequestParam String customerName,
            @RequestParam String productName
    ) {
        return saleService.getSalesByCustomerNameAndProductName(customerName, productName);
    }

    @PostMapping("/create")
    public Sale postCreateNewSale(@RequestBody SaleDto saleDto) {
        return saleService.postCreateNewSale(saleDto);
    }

    @PutMapping("/put") //? назв параметра_поле = value
    public void updateSaleCustomerName(
            @RequestParam String saleId,
            @RequestParam String customerName
    ) {
        saleService.updateSaleCustomerName(saleId, customerName);
    }

    @DeleteMapping("/delete/{saleId}") //delete/b5310470-4943-4718-8899-2329a4dec393
    public void deleteSaleById(@PathVariable("saleId") String saleId) {
        saleService.deleteSaleById(saleId);
    }


}