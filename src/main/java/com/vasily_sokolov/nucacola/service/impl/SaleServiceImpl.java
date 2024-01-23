package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.repository.SaleRepository;
import com.vasily_sokolov.nucacola.service.interf.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Override
    public Sale getSaleById(String saleId) {
        return saleRepository.findById(UUID.fromString(saleId)).orElse(null);
    }

    @Override
    public List<Sale> getSalesByCustomerName(String customerName) {
        return saleRepository.getSalesByCustomerName(customerName);
    }

    @Override
    @Transactional
    public Sale postCreateNewSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    @Transactional
    public void updateSaleCustomerName(String saleId, String customerName) {
        saleRepository.updateSaleCustomerName(UUID.fromString(saleId), customerName);
    }

    @Override
    public void deleteSaleById(String saleId) {
        saleRepository.deleteById(UUID.fromString(saleId));
    }
}
