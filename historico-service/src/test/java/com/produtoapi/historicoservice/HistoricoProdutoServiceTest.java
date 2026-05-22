package com.produtoapi.historicoservice;

import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.exception.HistoricoNotFoundException;
import com.produtoapi.historicoservice.mapper.HistoricoProdutoMapper;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import com.produtoapi.historicoservice.service.HistoricoProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricoProdutoServiceTest {

    @InjectMocks
    private HistoricoProdutoService service;

    @Mock
    private HistoricoProdutoRepository repository;

    @Mock
    private HistoricoProdutoMapper mapper;

    @Test
    void deveBuscarPorIdComSucesso() {

        Long id = 1L;

        HistoricoProduto entity = new HistoricoProduto();
        HistoricoProdutoResponseDTO dto = new HistoricoProdutoResponseDTO();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        HistoricoProdutoResponseDTO result = service.buscarPorId(id);

        assertNotNull(result);
        verify(repository).findById(id);
        verify(mapper).toDTO(entity);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarPorId() {

        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(HistoricoNotFoundException.class,
                () -> service.buscarPorId(id));

        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void deveBuscarPorPeriodoComSucesso() {

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now();

        HistoricoProduto entity = new HistoricoProduto();
        HistoricoProdutoResponseDTO dto = new HistoricoProdutoResponseDTO();

        when(repository.findByDataRegistroBetween(inicio, fim))
                .thenReturn(List.of(entity));

        when(mapper.toDTO(entity)).thenReturn(dto);

        List<HistoricoProdutoResponseDTO> result =
                service.buscarPorPeriodo(inicio, fim);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(repository).findByDataRegistroBetween(inicio, fim);
        verify(mapper).toDTO(entity);
    }

    @Test
    void deveLancarErroQuandoDataInicioForMaiorQueFim() {

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = LocalDateTime.now().minusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> service.buscarPorPeriodo(inicio, fim));

        verifyNoInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverHistorico() {

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now();

        when(repository.findByDataRegistroBetween(inicio, fim))
                .thenReturn(List.of());

        List<HistoricoProdutoResponseDTO> result =
                service.buscarPorPeriodo(inicio, fim);

        assertTrue(result.isEmpty());

        verify(repository).findByDataRegistroBetween(inicio, fim);
    }
}