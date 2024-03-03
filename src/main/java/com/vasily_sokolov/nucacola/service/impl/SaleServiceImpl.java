package com.vasily_sokolov.nucacola.service.impl;


import com.vasily_sokolov.nucacola.dto.SaleDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.exception.exceptions.ListException;
import com.vasily_sokolov.nucacola.exception.exceptions.SaleNotFoundException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
import com.vasily_sokolov.nucacola.mapper.SaleMapper;
import com.vasily_sokolov.nucacola.repository.ProductRepository;
import com.vasily_sokolov.nucacola.repository.SaleRepository;
import com.vasily_sokolov.nucacola.service.interf.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final ProductRepository productRepository;


    /**
     * The method finds a Sale in the database by id;
     *
     * @param saleId Unique Sale Identifier
     * @return If the sale is found, it returns the sale, otherwise it throws it away
     * SaleNotFoundException error.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public SaleDto getSaleById(String saleId) {
        return saleMapper.toDto(saleRepository.findById(UUID.fromString(saleId)).orElseThrow(
                () -> new SaleNotFoundException(ErrorMessage.SALE_NOT_FOUND)));
    }

    /**
     * The method finds a Sale in the database by customerName;
     *
     * @param customerName Sale Identifier
     * @return If the Sales is found, it returns the List<{@link SaleDto}> or empty List.
     * StringNotCorrectException
     **/
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<SaleDto> getSalesByCustomerName(String customerName) {
        List<SaleDto> saleDtoList = saleMapper.salesToSalesDto(saleRepository.getSalesByCustomerName(customerName));
        if (saleDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.SALE_NOT_FOUND);
        } else {
            return saleDtoList;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<SaleDto> getAllSales() {
        List<SaleDto> saleDtoList = saleMapper.salesToSalesDto(saleRepository.findAll());
        if (saleDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.SALE_NOT_FOUND);
        } else {
            return saleDtoList;
        }
    }

    /**
     * The method finds a Sale in the database by customerName and productName;
     *
     * @param customerName Sale Identifier 1
     * @param productName  Sale Identifier 2
     * @return If the products is found, it returns the List<{@link SaleDto}> or empty List.
     **/
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<SaleDto> getSalesByCustomerNameAndProductName(String customerName, String productName) {
        List<SaleDto> saleDtoList = saleMapper.salesToSalesDto(
                saleRepository.getSalesByCustomerNameAndProductName(
                        customerName,
                        productName));
        if (saleDtoList.isEmpty()) {
            throw new ListException(ErrorMessage.SALE_NOT_FOUND);
        } else {
            return saleDtoList;
        }
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Sale postCreateNewSale(SaleDto saleDto) {
        Product product = productRepository.findById(saleDto.getProductId())
                .orElse(null);
        if (product == null) {
            return null;
        }
        return saleRepository.save(saleMapper.toEntity(saleDto));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateSaleCustomerName(String saleId, String customerName) {
        getSaleById(saleId);
        saleRepository.updateSaleCustomerName(UUID.fromString(saleId), customerName);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteSaleById(String saleId) {
        getSaleById(saleId);
        saleRepository.deleteById(UUID.fromString(saleId));
    }

}
