package com.produtoapi.historicoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtoapi.historicoservice.controller.HistoricoProdutoController;
import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.security.filter.JwtAuthenticationFilter;
import com.produtoapi.historicoservice.security.service.JwtService;
import com.produtoapi.historicoservice.service.HistoricoProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoricoProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
class HistoricoProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoricoProdutoService service;
    @MockBean
    JwtService jwtService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    private ObjectMapper objectMapper;




    @Test
    void deveBuscarPorPeriodo() throws Exception {

        when(service.buscarPorPeriodo(any(), any())).thenReturn(List.of());

        mockMvc.perform(get("/eventos/historico")
                        .param("inicio", LocalDateTime.now().minusDays(5).toString())
                        .param("fim", LocalDateTime.now().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deveBuscarPorId() throws Exception {

        HistoricoProdutoResponseDTO response = new HistoricoProdutoResponseDTO();

        when(service.buscarPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/eventos/1"))
                .andExpect(status().isOk());
    }}

