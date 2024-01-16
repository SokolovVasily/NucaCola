package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.repository.SaleRepository;
import com.vasily_sokolov.nucacola.service.interf.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl  implements SaleService {

    private final SaleRepository saleRepository;

    @Override
    public Sale getSaleById(String saleId) {
        return saleRepository.getSaleById(saleId) ;
    }

    @Override
    public List<Sale> getSalesByCustomerName(String customerName) {
        return saleRepository.getSalesByCustomerName(customerName);
    }
}
