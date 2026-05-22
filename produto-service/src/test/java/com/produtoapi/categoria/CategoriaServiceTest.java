package com.produtoapi.categoria;

import static org.mockito.Mockito.*;

import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.dto.CategoriaRequestDTO;
import com.produtoapi.categoria.dto.CategoriaResponseDTO;
import com.produtoapi.categoria.repository.CategoriaRepository;
import com.produtoapi.categoria.service.*;
import com.produtoapi.exception.BusinessException;
import com.produtoapi.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @Mock
    private MapperCategoria mapper;

    @InjectMocks
    private CategoriaService service;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Nested
    class SalvarCategoria {

        @Test
        void deveSalvarCategoriaQuandoNaoExistir() {

            CategoriaRequestDTO dto = new CategoriaRequestDTO();
            dto.setNomeCategoria("Eletrônicos");
            dto.setDecricaoCategoria("Produtos eletrônicos");

            Categoria categoriaSalva = Categoria.builder().id(1L).nomeCategoria("Eletrônicos").build();

            CategoriaResponseDTO response = new CategoriaResponseDTO();

            when(repository.findByNomeCategoria("Eletrônicos")).thenReturn(null);

            when(repository.save(any(Categoria.class))).thenReturn(categoriaSalva);

            when(mapper.toResponse(categoriaSalva)).thenReturn(response);

            CategoriaResponseDTO resultado = service.salvarCategoria(dto);
            assertNotNull(resultado);

            verify(repository).findByNomeCategoria("Eletrônicos");
            verify(repository).save(any(Categoria.class));
        }

        @Test
        void deveLancarExcecaoQuandoCategoriaJaExistir() {

            CategoriaRequestDTO dto = new CategoriaRequestDTO();
            dto.setNomeCategoria("Eletrônicos");

            Categoria categoriaExistente = Categoria.builder().id(1L).nomeCategoria("Eletrônicos").build();

            when(repository.findByNomeCategoria("Eletrônicos")).thenReturn(categoriaExistente);

            BusinessException exception = assertThrows(BusinessException.class, () -> service.salvarCategoria(dto));

            assertEquals("Categoria ja cadastrada", exception.getMessage());

            verify(repository, never()).save(any(Categoria.class));
        }
    }

    @Nested
    class BuscarCategoriaPorId {

        @Test
        void deveRetornarCategoriaQuandoExistir() {

            Categoria categoria = Categoria.builder().id(1L).nomeCategoria("Eletrônicos").build();

            CategoriaResponseDTO dto = new CategoriaResponseDTO();

            when(repository.findById(1L)).thenReturn(Optional.of(categoria));

            when(mapper.toDTO(categoria)).thenReturn(dto);

            Optional<CategoriaResponseDTO> resultado = service.listarIdCategoria(1L);

            assertTrue(resultado.isPresent());
        }

        @Test
        void deveRetornarOptionalVazioQuandoNaoExistir() {

            when(repository.findById(1L)).thenReturn(Optional.empty());

            Optional<CategoriaResponseDTO> resultado = service.listarIdCategoria(1L);

            assertTrue(resultado.isEmpty());
        }
    }

    @Nested
    class ListarTodasCategorias {

        @Test
        void deveListarTodasCategorias() {

            Categoria categoria1 = Categoria.builder().id(1L).build();

            Categoria categoria2 = Categoria.builder().id(2L).build();

            when(repository.findAll()).thenReturn(List.of(categoria1, categoria2));

            when(mapper.toDTO(any(Categoria.class))).thenReturn(new CategoriaResponseDTO());

            List<CategoriaResponseDTO> resultado = service.listAllCategory();

            assertEquals(2, resultado.size());
        }
    }
}