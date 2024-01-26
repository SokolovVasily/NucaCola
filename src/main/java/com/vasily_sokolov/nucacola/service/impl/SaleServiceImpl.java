package com.vasily_sokolov.nucacola.service.impl;


import com.vasily_sokolov.nucacola.dto.SaleDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.mapper.SaleMapper;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
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
    private final SaleMapper saleMapper;
    private final ProductRepository productRepository;

    @Override
    public SaleDto getSaleById(String saleId) {
        return saleMapper.toDto(saleRepository.findById(UUID.fromString(saleId)).orElse(null));
    }

    @Override
    public List<SaleDto> getSalesByCustomerName(String customerName) {
        return saleMapper.salesToSalesDto(saleRepository.getSalesByCustomerName(customerName));
    }

    @Override
    public List<SaleDto> getAllSales() {
        return saleMapper.salesToSalesDto(saleRepository.findAll());
    }


    @Override
    public List<SaleDto> getSalesByCustomerNameAndProductName(String customerName, String productName) {
        return saleMapper.salesToSalesDto(
                saleRepository.getSalesByCustomerNameAndProductName(
                        customerName,
                        productName));
    }

    @Override
    public Sale postCreateNewSale(SaleDto saleDto) {
        Product product = productRepository.findById(saleDto.getProductId())
                .orElse(null);
        if(product == null){
            return null;
        }
        return saleRepository.save(saleMapper.toEntity(saleDto));
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
