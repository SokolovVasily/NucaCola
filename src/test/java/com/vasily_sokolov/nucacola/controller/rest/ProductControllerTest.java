package com.vasily_sokolov.nucacola.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
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

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String PRODUCT_ID = "10d83df1-7247-4a7e-af09-96d418317ec2";
    private static final String PRODUCT_ID_NOT_EXIST = "10d83df1-7247-4a7e-af09-96d418317ec3";
    private static final String MORE_45_CHAR = "123456789012345678901234567890123456789012345";

    //--------------------------------------------findById-------------------------------------------------------------

    @Test
    void findByIdPositiveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/productId/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(
                        PRODUCT_ID)));
    }

    @Test
    void findByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/productId/" + PRODUCT_ID_NOT_EXIST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10d83df1-7247-4a7e-af09-96d418317ec",
            "10d83df1-7247-4a7e-af09-96d418317ec22"
    })
    void findByIdTest500(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/productId/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------getProductsByName-----------------------------------------------------
    @Test
    void getProductsByNamePositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/name/" + "Nuca-Cola")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProductsName = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProductsName, new TypeReference<>() {
        });
        Assertions.assertEquals(3, list.size());
    }

    @Test
    void getProductsByNameTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/name/" + "Nuca-Cola!!!")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "N",
            MORE_45_CHAR
    })
    void getProductsByNameTest500(String name) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/name/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------getAllProducts--------------------------------------------------------
    @Test
    void getAllProductsPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProducts = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProducts, new TypeReference<>() {
        });
        Assertions.assertEquals(6, list.size());
    }

    //--------------------------------------------getProductsByCharacteristic-------------------------------------------
    @ParameterizedTest
    @CsvSource(value = {
            "SUGARY",
            "sugary",
            "sUgArY",
            "NOT_SUGARY",
            "not_sugary",
            "NOT_sUgary"
    })
    void getProductsByCharacteristicPositiveTest(String characteristic) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/characteristic/" +
                                characteristic)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProductsByCharacteristic = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProductsByCharacteristic, new TypeReference<>() {
        });
        Assertions.assertEquals(3, list.size());
    }

    @Test
    void getProductsByCharacteristicTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/characteristic/" +
                                "superSugary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //--------------------------------------------getProductsByNameAndCharacteristic------------------------------------

    @ParameterizedTest
    @CsvSource(value = {
            "NUCA-COLA,sugary",
            "nuca-cola,SUGARY",
            "NuCa-cola,sUgArY",
            "nuca-cola zero,not_sugary",
            "NUCA-Cola ZERO,NOT_SUGARY",
            "NUCA-Cola zeRO,NoT_SUGARY"
    })
    void getProductsByNameAndCharacteristicPositiveTest(String name, String characteristic) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/nameAndCharacteristic/")
                                .param("name", name)
                                .param("characteristic", characteristic)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProductsByCharacteristic = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProductsByCharacteristic, new TypeReference<>() {
        });
        Assertions.assertEquals(3, list.size());
    }

    @Test
    void getProductsByNameAndCharacteristicTest404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/nameAndCharacteristic/")
                                .param("name", "Nuca-Cola")
                                .param("characteristic", "superSugary")
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
                        MockMvcRequestBuilders.get("/product/nameAndCharacteristic/")
                                .param("name", name)
                                .param("characteristic", "sugary")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------getProductsByCapacityAndCharacteristic--------------------------------
    @ParameterizedTest
    @CsvSource(value = {
            "BIG,sugary",
            "big,SUGARY",
            "small,sUgArY",
            "medium,Not_sugary",
            "BIG,SUGARY"
    })
    void getProductsByCapacityAndCharacteristicPositiveTest(String capacity, String characteristic) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/capacityTypeAndCharacteristic/")
                                .param("capacity", capacity)
                                .param("characteristic", characteristic)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProductsByCharacteristic = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allProductsByCharacteristic, new TypeReference<>() {
        });
        Assertions.assertEquals(1, list.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "BIG,superSugary",
            "BIGG,sugary",
            "bigg,SUGary",
            "bigg,sugaryss"
    })
    void getProductsByCapacityAndCharacteristicTest404(String capacity, String characteristic) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/capacityTypeAndCharacteristic/")
                                .param("capacity", capacity)
                                .param("characteristic", characteristic)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //--------------------------------------------postCreateProduct-----------------------------------------------------
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void postCreateProductPositiveTest() throws Exception {
        ProductDto productDto = new ProductDto(
                "Nuca-Cola orange",
                BigDecimal.valueOf(1.99d),
                ProductCapacityType.BIG,
                ProductCharacteristic.NOT_SUGARY
        );
        String requestBody = objectMapper.writeValueAsString(productDto);
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/product/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(7, getAll().size());
    }


    @Test
    void postCreateProductTest403() throws Exception {
        ProductDto productDto = new ProductDto(
                "Nuca-Cola orange",
                BigDecimal.valueOf(1.99d),
                ProductCapacityType.BIG,
                ProductCharacteristic.NOT_SUGARY
        );
        String requestBody = objectMapper.writeValueAsString(productDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/product/create")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "-0.01",
            "0",
            "1000000"
    })
    void postCreateProductTest500(String price) throws Exception {
        ProductDto productDto = new ProductDto(
                "Nuca-Cola orange",
                BigDecimal.valueOf(Double.parseDouble(price)),
                ProductCapacityType.BIG,
                ProductCharacteristic.NOT_SUGARY
        );
        String requestBody = objectMapper.writeValueAsString(productDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/product/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------updateProductPrice----------------------------------------------------
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateProductPricePositiveTest() throws Exception {
        String productPrice = "100.00";
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/product/put")
                                .param("productId", PRODUCT_ID)
                                .param("productPrice", productPrice)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(productPrice, String.valueOf(getById().getProductPrice()));
    }

    @Test
    void updateProductPriceTest403() throws Exception {
        String productPrice = "100.00";
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/product/put")
                                .param("productId", PRODUCT_ID)
                                .param("productPrice", productPrice)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateProductPriceTest404() throws Exception {
        String productPrice = "100.00";
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/product/put")
                                .param("productId", PRODUCT_ID_NOT_EXIST)
                                .param("productPrice", productPrice)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "10d83df1-7247-4a7e-af09-96d418317ec,1.99",
            "10d83df1-7247-4a7e-af09-96d418317ec22,1.99",
            "10d83df1-7247-4a7e-af09-96d418317ec2,-0.01",
            "10d83df1-7247-4a7e-af09-96d418317ec2,0",
            "10d83df1-7247-4a7e-af09-96d418317ec2,1000000"
    })
    void updateProductPriceTest500(String productId, String productPrice) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/product/put")
                                .param("productId", productId)
                                .param("productPrice", productPrice)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------deleteProductById-----------------------------------------------------
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteProductByIdPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/product/delete/" + PRODUCT_ID)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(5, getAll().size());
    }


    @Test
    void deleteProductByIdTest403() throws Exception {
       mockMvc.perform(
                        MockMvcRequestBuilders.delete("/product/delete/" + PRODUCT_ID)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteProductByIdTest404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/product/delete/" + PRODUCT_ID_NOT_EXIST)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "10d83df1-7247-4a7e-af09-96d418317ec",
            "10d83df1-7247-4a7e-af09-96d418317ec22",
    })
    void deleteProductByIdTest500(String productID) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/product/delete/" + productID)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //--------------------------------------------privateMethods-----------------------------------------------------
    private List<ProductDto> getAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProducts = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(allProducts, new TypeReference<>() {
        });
    }

    private Product getById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/productId/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String productJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(productJson, Product.class);
    }
}
