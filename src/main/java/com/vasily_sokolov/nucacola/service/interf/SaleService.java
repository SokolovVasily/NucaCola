package com.vasily_sokolov.nucacola.service.interf;


import com.vasily_sokolov.nucacola.dto.SaleDto;
import com.vasily_sokolov.nucacola.entity.Sale;


import java.util.List;


public interface SaleService {

    SaleDto getSaleById(String saleId);

    List<SaleDto> getAllSales();

    List<SaleDto> getSalesByCustomerName(String customerName);

    List<SaleDto> getSalesByCustomerNameAndProductName(String customerName, String productName);

    Sale postCreateNewSale(SaleDto saleDto);

    void updateSaleCustomerName(String saleId, String customerName);

    void deleteSaleById(String saleId);




}
