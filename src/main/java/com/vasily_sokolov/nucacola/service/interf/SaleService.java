package com.vasily_sokolov.nucacola.service.interf;

import com.vasily_sokolov.nucacola.entity.Sale;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface SaleService {

    Sale getSaleById(String saleId);

    List<Sale> getSalesByCustomerName(String customerName);
}
