package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.dto.SaleDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.Sale;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import com.vasily_sokolov.nucacola.exception.exceptions.CapacityNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.CharacteristicNotFoundException;
import com.vasily_sokolov.nucacola.exception.exceptions.ListException;
import com.vasily_sokolov.nucacola.exception.exceptions.SaleNotFoundException;
import com.vasily_sokolov.nucacola.mapper.SaleMapper;
import com.vasily_sokolov.nucacola.repository.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleMapper saleMapper;

    private final String uuid = "b5310470-4943-4718-8899-2329a4dec393";

    private List<Sale> saleList;
    private List<SaleDto> saleDtoList;

    private final List<Sale> saleListEmpty = new ArrayList<>();
    private final List<SaleDto> saleDtoListEmpty = new ArrayList<>();

    @BeforeEach

    public void init() {
        saleList = List.of(new Sale());
        saleDtoList = List.of(new SaleDto());
    }

    //1.------------------------------------------getSaleById()---------------------------------------------

    @Test
    void getSaleByIdPositiveTest() {
        Mockito.when(saleRepository.findById(UUID.fromString(uuid)))
                .thenReturn(Optional.of(new Sale()));
        Assertions.assertEquals((new Sale()), saleRepository.findById(UUID.fromString(uuid)).orElseThrow());
        Mockito.verify(saleRepository).findById(UUID.fromString(uuid));
    }

    @Test
    void getSaleByIdNotFoundExceptionTest() {
        Assertions.assertThrows(SaleNotFoundException.class, () -> saleService.getSaleById(uuid));
    }

    //2.------------------------------------------getSalesByCustomerName()---------------------------------------------
    @Test
    void getSalesByCustomerNamePositiveTest() {
        String customerName = "s";
        Mockito.when(saleRepository.getSalesByCustomerName(customerName))
                .thenReturn(saleList);
        Mockito.when(saleMapper.salesToSalesDto(saleList))
                .thenReturn(saleDtoList);
        Assertions.assertEquals(1, saleService.getSalesByCustomerName(customerName).size());
        Mockito.verify(saleRepository).getSalesByCustomerName(customerName);
        Mockito.verify(saleMapper).salesToSalesDto(saleList);
    }

    @Test
    void getProductsByCustomerNameListExceptionTest() {
        String name = "s";
        Assertions.assertThrows(ListException.class, () -> saleService.getSalesByCustomerName(name).size());

    }

    //3.------------------------------------------getAllSales()---------------------------------------------
    @Test
    void getAllProductsPositiveTest() {
        Mockito.when(saleRepository.findAll())
                .thenReturn(saleList);
        Mockito.when(saleMapper.salesToSalesDto(saleList))
                .thenReturn(saleDtoList);
        Assertions.assertEquals(1, saleService.getAllSales().size());
        Mockito.verify(saleRepository).findAll();
        Mockito.verify(saleMapper).salesToSalesDto(saleList);
    }

    @Test
    void getAllProductsListExceptionTest() {
        Assertions.assertThrows(ListException.class, () -> saleService.getAllSales().size());
    }

    //4.------------------------------------------getSalesByCustomerNameAndProductName()---------------------------------------------
    @Test
    void getSalesByCustomerNameAndProductNamePositiveTest() {
        String customerName = "s";
        String productName = "p";
        Mockito.when(saleRepository.getSalesByCustomerNameAndProductName(
                customerName, productName)).thenReturn(saleList);
        Mockito.when(saleMapper.salesToSalesDto(saleList)).thenReturn(saleDtoList);
        Assertions.assertEquals(1, saleService.getSalesByCustomerNameAndProductName(
                customerName, productName).size());
        Mockito.verify(saleRepository).getSalesByCustomerNameAndProductName(customerName,productName);
        Mockito.verify(saleMapper).salesToSalesDto(saleList);
    }
    @Test
    void getSalesByCustomerNameAndProductNameListExceptionTest() {
        String customerName = "Supermarket 1";
        String productName = "Nuca-Cola";

        when(saleRepository.getSalesByCustomerNameAndProductName(
               (customerName),
               (productName)))
                .thenReturn(saleListEmpty);
        when(saleMapper.salesToSalesDto(saleListEmpty)).thenReturn(saleDtoListEmpty);

        Assertions.assertThrows(ListException.class,
                () -> saleService.getSalesByCustomerNameAndProductName(customerName, productName));

        Mockito.verify(saleRepository).getSalesByCustomerNameAndProductName(
                (customerName),
                (productName));
        Mockito.verify(saleMapper).salesToSalesDto(saleListEmpty);
    }




    //5.------------------------------------------()------------






}
