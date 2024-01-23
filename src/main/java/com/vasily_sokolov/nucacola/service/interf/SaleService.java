package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.entity.Sale;

import java.util.List;


public interface SaleService {

    Sale getSaleById(String saleId);

    List<Sale> getSalesByCustomerName(String customerName);

    Sale postCreateNewSale(Sale sale);

    void updateSaleCustomerName(String saleId, String customerName);

    void deleteSaleById(String saleId);

}
