package com.produtoapi.categoria;
import com.produtoapi.categoria.service.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtoapi.categoria.controller.CategoriaController;
import com.produtoapi.categoria.dto.CategoriaRequestDTO;
import com.produtoapi.categoria.dto.CategoriaResponseDTO;

import com.produtoapi.produto.controller.ProdutoController;
import com.produtoapi.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriaService service;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Nested
    class SalvarCategoria {

        @Test
        @DisplayName("Deve salvar categoria com sucesso")
        void deveSalvarCategoria() throws Exception {

            CategoriaRequestDTO request = new CategoriaRequestDTO();
            request.setNomeCategoria("Eletrônicos");
            request.setDecricaoCategoria("Produtos eletrônicos");

            CategoriaResponseDTO response = new CategoriaResponseDTO();

            when(service.salvarCategoria(any()))
                    .thenReturn(response);

            mockMvc.perform(post("/categorias")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message")
                            .value("Categoria salva com sucesso"));
        }
    }

    @Nested
    class BuscarPorId {

        @Test
        @DisplayName("Deve buscar categoria por id")
        void deveBuscarCategoriaPorId() throws Exception {

            CategoriaResponseDTO response = new CategoriaResponseDTO();

            when(service.listarIdCategoria(1L))
                    .thenReturn(Optional.of(response));

            mockMvc.perform(get("/categorias/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message")
                            .value("Categoria listada com sucesso"));
        }

        @Test
        @DisplayName("Deve retornar erro quando categoria não existir")
        void deveRetornarErroQuandoCategoriaNaoExistir() throws Exception {

            when(service.listarIdCategoria(1L))
                    .thenReturn(Optional.empty());

            mockMvc.perform(get("/categorias/1"))
                    .andExpect(status().is5xxServerError());
        }
    }

    @Nested
    class ListarCategorias {

        @Test
        @DisplayName("Deve listar todas as categorias")
        void deveListarTodasCategorias() throws Exception {

            CategoriaResponseDTO categoria1 =
                    new CategoriaResponseDTO();

            CategoriaResponseDTO categoria2 =
                    new CategoriaResponseDTO();

            when(service.listAllCategory())
                    .thenReturn(List.of(categoria1, categoria2));

            mockMvc.perform(get("/categorias"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message")
                            .value("Categorias listadas com sucesso"));
        }
    }
}