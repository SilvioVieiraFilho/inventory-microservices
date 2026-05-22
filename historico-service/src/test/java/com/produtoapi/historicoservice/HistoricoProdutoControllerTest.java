package com.produtoapi.historicoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.service.HistoricoProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
class HistoricoProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoricoProdutoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveSalvarHistorico() throws Exception {

        HistoricoProdutoResponseDTO response = new HistoricoProdutoResponseDTO();

        when(service.salvar(any())).thenReturn(response);

        HistoricoProdutoRequestDTO request = new HistoricoProdutoRequestDTO();

        mockMvc.perform(post("/eventos/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

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

//        when(service.buscarPorProdutoId(1L)).thenReturn(response);

        mockMvc.perform(get("/eventos/1"))
                .andExpect(status().isOk());
    }
}
