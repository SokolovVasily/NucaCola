package com.vasily_sokolov.nucacola.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.dto.SaleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String SALE_ID = "d092f5f9-c2b1-4fc3-bb8d-502ccd215c9f";
    private static final String SALE_ID_NOT_EXIST = "b5310470-4943-4718-8899-2329a4dec392";
    private static final String MORE_45_CHAR = "123456789012345678901234567890123456789012345";
    private static final String CUSTOMER_NAME = "Supermarket 1";
    private static final String CUSTOMER_NAME_NOT_VALID = "Supermarket 11";

    //---------------------------------------getSaleById()--------------------------------------------------------------
    @Test
    void getSaleByIdPositiveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sale/saleId/" + SALE_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSaleByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sale/saleId/" + SALE_ID_NOT_EXIST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "d092f5f9-c2b1-4fc3-bb8d-502ccd215c9f!",
            "d092f5f9-c2b1-4fc3-bb8d-502ccd215c9"
    })
    void getSaleByIdTest500(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sale/saleId/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //---------------------------------------getSaleByName()------------------------------------------------------------
    @Test
    void getSalesByNamePositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sale/customerName/" + CUSTOMER_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProductsName = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProductsName, new TypeReference<>() {
        });
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void getSalesByNameTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sale/customerName/" + CUSTOMER_NAME_NOT_VALID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "N",
            MORE_45_CHAR
    })
    void getSalesByNameTest500(String name) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sale/customerName/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //---------------------------------------getAllSales()--------------------------------------------------------------
    @Test
    void getAllSalesPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sale/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProducts = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProducts, new TypeReference<>() {
        });
        Assertions.assertEquals(2, list.size());
    }

    //---------------------------------------getSalesByCustomerNameAndProductName()--------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(value = {
            "Supermarket 1,Nuca-Cola",
            "supermarket 1,NUCA-Cola ",
            "SUPERMARKET 1,NuCa-cola",
            "SuPermaRket 1,NuCa-colA "
    })
    void getSalesByCustomerNameAndProductNamePositiveTest(String customerName, String productName) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/sale/CustomerNameAndProductName/")
                                .param("customerName", customerName)
                                .param("productName", productName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProductsByCharacteristic = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProductsByCharacteristic, new TypeReference<>() {
        });
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void getSalesByCustomerNameAndProductNameTest404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sale/CustomerNameAndProductName/")
                                .param("customerName", "Supermarket 1")
                                .param("productName", "Nuca-Cola!!!")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1",
            MORE_45_CHAR
    })
    void getProductsByNameAndCharacteristicTest500(String name) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sale/CustomerNameAndProductName/")
                                .param("customerName", name)
                                .param("productName", "Nuca-Cola!!!")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //---------------------------------------postCreateNewSale()--------------------------------------------------------

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void postCreateSalePositiveTest() throws Exception {
        SaleDto saleDto = new SaleDto(
                "Supermarket 10",
                UUID.fromString("10d83df1-7247-4a7e-af09-96d418317ec2"),
                "Nuca-Cola"
        );
        String requestBody = objectMapper.writeValueAsString(saleDto);
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/sale/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(3, getAll().size());
    }

    @Test
    void postCreateSaleTest403() throws Exception {
        SaleDto saleDto = new SaleDto(
                "Supermarket 10",
                UUID.fromString("10d83df1-7247-4a7e-af09-96d418317ec2"),
                "Nuca-Cola"
        );
        String requestBody = objectMapper.writeValueAsString(saleDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sale/create")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "s,1c27bc9d-c00b-46c3-bf1c-393a39160168,Nuca-Cola zero",
            "123456789012345678901234567890123456789012345,1c27bc9d-c00b-46c3-bf1c-393a39160168,Nuca-Cola zero",
            "Supermarket 10,1c27bc9d-c00b-46c3-bf1c-393a39160168,n",
            "Supermarket 10,1c27bc9d-c00b-46c3-bf1c-393a39160168,123456789012345678901234567890123456789012345",
    })
    void postCreateSaleTest500(String customerName, String saleId, String productName) throws Exception {
        SaleDto saleDto = new SaleDto(
                customerName,
                UUID.fromString(saleId),
                productName
        );
        String requestBody = objectMapper.writeValueAsString(saleDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sale/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------updateSaleCustomerName()----------------------------------------------
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateSaleCustomerNamePositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/sale/put")
                                .param("saleId", SALE_ID)
                                .param("customerName", CUSTOMER_NAME)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateSaleCustomerNameTest403() throws Exception {
         mockMvc.perform(
                        MockMvcRequestBuilders.put("/sale/put")
                                .param("saleId", SALE_ID)
                                .param("customerName", CUSTOMER_NAME)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateSaleCustomerNameTest404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/sale/put")
                                .param("saleId", SALE_ID_NOT_EXIST)
                                .param("customerName", CUSTOMER_NAME)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "b5310470-4943-4718-8899-2329a4dec393,Supermarket 100000000000000000000000000000000",
            "b5310470-4943-4718-8899-2329a4dec393,S",
            "b5310470-4943-4718-8899-2329a4dec3933,Supermarket 1",
            "b5310470-4943-4718-8899-2329a4dec39,Supermarket 1",
    })
    void updateSaleCustomerNameTest500(String saleId,String customerName) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/sale/put")
                                .param("saleId", saleId)
                                .param("customerName", customerName)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
    //--------------------------------------------deleteProductById-----------------------------------------------------

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteSaleByIdPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/sale/delete/" + SALE_ID)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(1, getAll().size());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteSaleByIdTest403() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/sale/delete/" + SALE_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteSaleByIdTest404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/sale/delete/" + SALE_ID_NOT_EXIST)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "b5310470-4943-4718-8899-2329a4dec3933",
            "b5310470-4943-4718-8899-2329a4dec39",
    })
    void deleteSaleByIdTest500(String saleId) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/sale/delete/" + saleId)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    }
    //--------------------------------------------privateMethods--------------------------------------------------------

    private List<SaleDto> getAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sale/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allSales = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(allSales, new TypeReference<>() {
        });
    }
}

