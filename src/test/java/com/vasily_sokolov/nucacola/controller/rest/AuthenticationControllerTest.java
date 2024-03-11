package com.vasily_sokolov.nucacola.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasily_sokolov.nucacola.dto.authentication.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticatePositiveTest() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(
                "Administrator",
                "1979"
        );
        String requestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/authenticate")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @CsvSource(value = {
            "a,1979",
            "Administrator,19799",
            "Administratorrr,197900"
    })
    void authenticateTest500(String userName, String password) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(
                userName,
                password
        );
        String requestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/authenticate")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
