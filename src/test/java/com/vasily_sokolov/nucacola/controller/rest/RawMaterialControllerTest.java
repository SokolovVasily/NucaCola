package com.vasily_sokolov.nucacola.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasily_sokolov.nucacola.dto.ProductDto;
import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class RawMaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String MORE_45_CHAR = "123456789012345678901234567890123456789012345";
    private static final String RAW_MATERIAL_ID = "7d2000c1-8111-420c-a42a-e4eca5b50090";
    private static final String RAW_MATERIAL_ID_NOT_EXIST = "7d2000c1-8111-420c-a42a-e4eca5b50099";

    //-----------------------------------------------getRawMaterialsByName()--------------------------------------------

    @ParameterizedTest
    @CsvSource(value = {
            "secret ingredient for not sugar drink",
            "SeCreT ingredient For NOT sugAr dRInk",
            "SECRET INGREDIENT FOR NOT SUGAR DRINK",
            "secret ingredient for  sugar drink",
            "SeCreT ingredient For  sugAr dRInk",
            "SECRET INGREDIENT FOR  SUGAR DRINK"
    })
    void getRawMaterialsByNamePositiveTest(String name) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/rawMaterial/name/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String rawMaterialName = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(rawMaterialName, new TypeReference<>() {
        });
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void getRawMaterialsByNameTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rawMaterial/name/" +
                                "secret ingredient for not sugar drink!!!")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "N",
            MORE_45_CHAR
    })
    void getRawMaterialsByNameTest500(String name) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rawMaterial/name/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-------------------------------------------------getAllRawMaterials()---------------------------------------------
    @Test
    void getAllRawMaterialsPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/rawMaterial/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allRawMaterials = mvcResult.getResponse().getContentAsString();
        List<ProductDto> list = objectMapper.readValue(allRawMaterials, new TypeReference<>() {
        });
        Assertions.assertEquals(2, list.size());
    }

    //----------------------------------------------postCreateRawMaterial()---------------------------------------------

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void postCreateRawMaterialPositiveTest() throws Exception {
        RawMaterialDto rawMaterialDto = new RawMaterialDto(
                "secret ingredient for dietetic drink",
                "Jupiter"
        );
        String requestBody = objectMapper.writeValueAsString(rawMaterialDto);
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/rawMaterial/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(3, getAll().size());
    }

    @Test
    void postCreateRawMaterialTest403() throws Exception {
        RawMaterialDto rawMaterialDto = new RawMaterialDto(
                "secret ingredient for dietetic drink",
                "Jupiter"
        );
        String requestBody = objectMapper.writeValueAsString(rawMaterialDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/rawMaterial/create")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "secret ingredient for dietetic drink,S",
            "s,Saturn",
            "secret ingredient for dietetic drink secret for  ,Saturn",
            "secret ingredient for dietetic drink,Saturn_nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
    })
    void postCreateRawMaterialTest500(String rawMaterialName, String supplierName) throws Exception {
        RawMaterialDto rawMaterialDto = new RawMaterialDto(
                rawMaterialName,
                supplierName
        );
        String requestBody = objectMapper.writeValueAsString(rawMaterialDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/rawMaterial/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //----------------------------------------------updateRawMaterialName()---------------------------------------------

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateRawMaterialNamePositiveTest() throws Exception {
        String rawMaterialName = "secret ingredient for <parcel> sugar drink";
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/rawMaterial/put")
                                .param("rawMaterialId", RAW_MATERIAL_ID)
                                .param("name", rawMaterialName)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void updateRawMaterialNameTest403() throws Exception {
        String rawMaterialName = "secret ingredient for <parcel> sugar drink";
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/rawMaterial/put")
                                .param("rawMaterialId", RAW_MATERIAL_ID)
                                .param("name", rawMaterialName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void updateRawMaterialNameTest404() throws Exception {
        String rawMaterialName = "secret ingredient for <parcel> sugar drink";
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/rawMaterial/put")
                                .param("rawMaterialId", RAW_MATERIAL_ID_NOT_EXIST)
                                .param("name", rawMaterialName)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "7d2000c1-8111-420c-a42a-e4eca5b5009,secret ingredient for <parcel> sugar drink ",
            "7d2000c1-8111-420c-a42a-e4eca5b500901,secret ingredient for <parcel> sugar drink ",
            "7d2000c1-8111-420c-a42a-e4eca5b50090,s",
            "7d2000c1-8111-420c-a42a-e4eca5b50090,secret ingredient for <parcel> sugar drink for"
    })
    void updateRawMaterialNameTest500(String rawMaterialId, String rawMaterialName) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/rawMaterial/put")
                                .param("rawMaterialId", rawMaterialId)
                                .param("name", rawMaterialName)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //----------------------------------deleteRawMaterialById-----------------------------------------------------------
    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteRawMaterialByIdPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rawMaterial/delete/" + RAW_MATERIAL_ID)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(1, getAll().size());
    }

    @Test
    void deleteRawMaterialByIdTest403() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rawMaterial/delete/" + RAW_MATERIAL_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @Test
    void deleteRawMaterialByIdTest404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rawMaterial/delete/" + RAW_MATERIAL_ID_NOT_EXIST)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(username = "MockAdmin", password = "1234", roles = {"ADMIN"})
    @ParameterizedTest
    @CsvSource(value = {
            "7d2000c1-8111-420c-a42a-e4eca5b5009",
            "7d2000c1-8111-420c-a42a-e4eca5b500900",
    })
    void deleteRawMaterialByIdTest500(String rawMaterialId) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rawMaterial/delete/" + rawMaterialId)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //----------------------------------PrivateMethods------------------------------------------------------------------
    private List<RawMaterialDto> getAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/rawMaterial/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allRawMaterials = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(allRawMaterials, new TypeReference<>() {
        });
    }
}
