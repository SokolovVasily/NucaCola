package com.vasily_sokolov.nucacola.controller.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class
WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //--------------------------------------------------------getWarehouseById()----------------------------------------
    @Test
    void getWarehouseByIdPositiveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/warehouse/warehouseId/" + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(
                        "1")));
    }

    @Test
    void getWarehouseByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/warehouse/warehouseId/" + "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(
                        "2")));
    }

    @Test
    void getWarehouseByIdTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/warehouse/warehouseId/" + "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    }
}
