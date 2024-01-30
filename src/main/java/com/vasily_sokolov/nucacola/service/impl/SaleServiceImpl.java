package com.vasily_sokolov.nucacola.service.impl;


import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.dto.SaleDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.exception.exceptions.SaleNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.StringNotCorrectException;
import com.vasily_sokolov.nucacola.exception.message.ErrorMessage;
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
public class  SaleServiceImpl implements SaleService {

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
    @Override
    public SaleDto getSaleById(String saleId) {
        return saleMapper.toDto(saleRepository.findById(UUID.fromString(saleId)).orElseThrow(
                () -> new SaleNotFoundException(ErrorMessage.SALE_NOT_FOUND)));
    }

    /**
     * The method finds a Sale in the database by customerName;
     * In this method, for the name parameter, a check occurs in the
     * {@link SaleServiceImpl#checkString45Length(String)} method,
     * when return FALSE a StringNotCorrectException error is thrown.
     * @param customerName Sale Identifier
     * @return If the Sales is found, it returns the List<{@link SaleDto}> or empty List.
     *StringNotCorrectException
     **/

    @Override
    public List<SaleDto> getSalesByCustomerName(String customerName) {
        if (!checkString45Length(customerName)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        return saleMapper.salesToSalesDto(saleRepository.getSalesByCustomerName(customerName));
    }

    @Override
    public List<SaleDto> getAllSales() {
        return saleMapper.salesToSalesDto(saleRepository.findAll());
    }


    /**
     * The method finds a Sale in the database by customerName and productName;
     * In this method, for the customerName parameter, a check occurs in the
     * {@link SaleServiceImpl#checkString45Length(String)} method,
     * when return FALSE a StringNotCorrectException error is thrown.
     * ProductName parameter, a check occurs in the
     * {@link SaleServiceImpl#checkString45Length(String)} method,
     * when return FALSE a StringNotCorrectException error is thrown.
     * @param customerName Sale Identifier 1
     * @param productName   Sale Identifier 2
     * @return If the products is found, it returns the List<{@link SaleDto}> or empty List.

     **/
    @Override
    public List<SaleDto> getSalesByCustomerNameAndProductName(String customerName, String productName) {
        if (!checkString45Length(customerName)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        if (!checkString45Length(productName)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }

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
        getSaleById(saleId);
        if (!checkString45Length(customerName)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        saleRepository.updateSaleCustomerName(UUID.fromString(saleId), customerName);
    }

    @Override
    public void deleteSaleById(String saleId) {
        getSaleById(saleId);
        saleRepository.deleteById(UUID.fromString(saleId));
    }

    private boolean checkString45Length(String name) {
        return name.length() > 1 && name.length() < 45;
    }
}
