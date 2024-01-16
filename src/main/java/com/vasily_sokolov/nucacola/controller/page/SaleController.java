package com.vasily_sokolov.nucacola.controller.page;

import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.service.interf.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;
    @GetMapping("/saleId/{saleId}")
    public Sale getSaleById(@PathVariable("saleId")String saleId){
        return saleService.getSaleById(saleId);
    }
    // http://localhost:8080/sale/saleId/1
    // http://localhost:8080/sale/saleId/2
    @GetMapping("/customerName/{customerName}")
    public List<Sale> getSalesByCustomerName(@PathVariable("customerName")String customerName){
        return saleService.getSalesByCustomerName(customerName);
    }
    // http://localhost:8080/sale/customerName/Supermarket 1

}