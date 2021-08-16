package com.basta.vat.controller;

import com.basta.vat.config.VatConfig;
import com.basta.vat.service.Converter;
import com.basta.vat.service.Evaluator;
import com.basta.vat.service.VatLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VatControllerTest {

    @Mock
    private VatLoader vatLoader;
    @Mock
    private Converter converter;
    @Mock
    private Evaluator evaluator;
    @Mock
    private VatConfig vatConfig;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VatController(vatLoader, converter, evaluator, vatConfig)).build();
    }

    @Test
    void calculateVat() throws Exception {

        var request = MockMvcRequestBuilders.get("/vat/");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }
}