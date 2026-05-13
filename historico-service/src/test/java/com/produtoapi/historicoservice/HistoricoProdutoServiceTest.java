package com.produtoapi.historicoservice;

import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.enums.TipoEvento;
import com.produtoapi.historicoservice.exception.HistoricoNotFoundException;
import com.produtoapi.historicoservice.mapper.HistoricoProdutoMapper;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import com.produtoapi.historicoservice.service.HistoricoProdutoService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricoProdutoServiceTest {

    @Mock
    private HistoricoProdutoMapper mapper;

    @Mock
    private HistoricoProdutoRepository repository;

    @InjectMocks
    private HistoricoProdutoService service;

    @Nested
    class SalvarHistoricoComSucesso {

        @Test
        void deveSalvarHistoricoComSucesso() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .nomeProduto("Pilha")
                            .quantidadeAnterior(2)
                            .quantidadeNova(5)
                            .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                            .build();

            HistoricoProduto entity =
                    HistoricoProduto.builder()
                            .nomeProduto("Pilha")
                            .build();

            HistoricoProduto historicoSalvo =
                    HistoricoProduto.builder()
                            .id(1L)
                            .nomeProduto("Pilha")
                            .diferenca(3)
                            .build();

            HistoricoProdutoResponseDTO responseDTO =
                    HistoricoProdutoResponseDTO.builder()
                            .id(1L)
                            .nomeProduto("Pilha")
                            .build();

            when(mapper.toEntity(dto)).thenReturn(entity);
            when(repository.save(entity)).thenReturn(historicoSalvo);
            when(mapper.toDTO(historicoSalvo)).thenReturn(responseDTO);

            HistoricoProdutoResponseDTO response = service.salvar(dto);

            assertEquals(1L, response.getId());
            assertEquals("Pilha", response.getNomeProduto());

            assertEquals(3, entity.getDiferenca());
            assertNotNull(entity.getDataRegistro());

            verify(mapper, times(1)).toEntity(dto);
            verify(repository, times(1)).save(entity);
            verify(mapper, times(1)).toDTO(historicoSalvo);
        }
    }

    @Nested
    class Validacoes {

        @Test
        void deveLancarExcecaoQuandoTipoEventoForNulo() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .nomeProduto("Pilha")
                            .quantidadeAnterior(2)
                            .quantidadeNova(5)
                            .build();

            RuntimeException ex =
                    assertThrows(RuntimeException.class,
                            () -> service.salvar(dto));

            assertEquals("TipoEvento não pode ser nulo", ex.getMessage());

            verify(repository, never()).save(any());
        }

        @Test
        void deveLancarExcecaoQuandoQuantidadeAnteriorForNula() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .nomeProduto("Pilha")
                            .quantidadeNova(5)
                            .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                            .build();

            RuntimeException ex =
                    assertThrows(RuntimeException.class,
                            () -> service.salvar(dto));

            assertEquals("Quantidades não podem ser nulas", ex.getMessage());

            verify(repository, never()).save(any());
        }

        @Test
        void deveLancarExcecaoQuandoQuantidadeNovaForNula() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .nomeProduto("Pilha")
                            .quantidadeAnterior(5)
                            .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                            .build();

            RuntimeException ex =
                    assertThrows(RuntimeException.class,
                            () -> service.salvar(dto));

            assertEquals("Quantidades não podem ser nulas", ex.getMessage());

            verify(repository, never()).save(any());
        }
    }

    @Nested
    class CalculoDeDiferenca {

        @Test
        void deveCalcularDiferencaPositiva() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .quantidadeAnterior(2)
                            .quantidadeNova(5)
                            .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                            .build();

            HistoricoProduto entity = new HistoricoProduto();

            when(mapper.toEntity(dto)).thenReturn(entity);
            when(repository.save(any())).thenReturn(entity);
            when(mapper.toDTO(any())).thenReturn(new HistoricoProdutoResponseDTO());

            service.salvar(dto);

            assertEquals(3, entity.getDiferenca());
        }

        @Test
        void deveCalcularDiferencaNegativa() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .quantidadeAnterior(10)
                            .quantidadeNova(5)
                            .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                            .build();

            HistoricoProduto entity = new HistoricoProduto();

            when(mapper.toEntity(dto)).thenReturn(entity);
            when(repository.save(any())).thenReturn(entity);
            when(mapper.toDTO(any())).thenReturn(new HistoricoProdutoResponseDTO());

            service.salvar(dto);

            assertEquals(-5, entity.getDiferenca());
        }

        @Test
        void deveCalcularDiferencaZero() {

            HistoricoProdutoRequestDTO dto =
                    HistoricoProdutoRequestDTO.builder()
                            .quantidadeAnterior(10)
                            .quantidadeNova(10)
                            .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                            .build();

            HistoricoProduto entity = new HistoricoProduto();

            when(mapper.toEntity(dto)).thenReturn(entity);
            when(repository.save(any())).thenReturn(entity);
            when(mapper.toDTO(any())).thenReturn(new HistoricoProdutoResponseDTO());

            service.salvar(dto);

            assertEquals(0, entity.getDiferenca());
        }
    }

    @Nested
    class ListarPorId {

        @Test
        void deveRetornarHistoricoQuandoIdExistir() {

            Long id = 1L;

            HistoricoProduto entity =
                    HistoricoProduto.builder()
                            .id(id)
                            .nomeProduto("Pilha")
                            .build();

            HistoricoProdutoResponseDTO responseDTO =
                    HistoricoProdutoResponseDTO.builder()
                            .id(id)
                            .nomeProduto("Pilha")
                            .build();

            when(repository.findById(id))
                    .thenReturn(Optional.of(entity));

            when(mapper.toDTO(entity))
                    .thenReturn(responseDTO);

            HistoricoProdutoResponseDTO response =
                    service.listarPorid(id);

            assertEquals(id, response.getId());
            assertEquals("Pilha", response.getNomeProduto());

            verify(repository, times(1)).findById(id);
            verify(mapper, times(1)).toDTO(entity);
        }

        @Test
        void deveLancarExcecaoQuandoIdNaoExistir() {

            Long id = 99L;

            when(repository.findById(id))
                    .thenReturn(Optional.empty());

            assertThrows(
                    HistoricoNotFoundException.class,
                    () -> service.listarPorid(id)
            );

            verify(repository, times(1)).findById(id);
            verify(mapper, never()).toDTO(any());
        }


    }

    @Nested
    class BuscarPorPeriodo {

        @Test
        void deveRetornarListaQuandoPeriodoValido() {

            LocalDateTime inicio = LocalDateTime.now().minusDays(5);
            LocalDateTime fim = LocalDateTime.now();

            HistoricoProduto entity =
                    HistoricoProduto.builder()
                            .id(1L)
                            .nomeProduto("Pilha")
                            .build();

            HistoricoProdutoResponseDTO dto =
                    HistoricoProdutoResponseDTO.builder()
                            .id(1L)
                            .nomeProduto("Pilha")
                            .build();

            when(repository.findByDataRegistroBetween(inicio, fim))
                    .thenReturn(List.of(entity));

            when(mapper.toDTO(entity))
                    .thenReturn(dto);

            List<HistoricoProdutoResponseDTO> response =
                    service.buscarPorPeriodo(inicio, fim);

            assertEquals(1, response.size());
            assertEquals("Pilha", response.get(0).getNomeProduto());

            verify(repository, times(1))
                    .findByDataRegistroBetween(inicio, fim);

            verify(mapper, times(1))
                    .toDTO(entity);
        }

        @Test
        void deveLancarExcecaoQuandoInicioForMaiorQueFim() {

            LocalDateTime inicio = LocalDateTime.now();
            LocalDateTime fim = LocalDateTime.now().minusDays(1);

            IllegalArgumentException ex =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> service.buscarPorPeriodo(inicio, fim)
                    );

            assertEquals(
                    "Data de início não pode ser maior que a data fim.",
                    ex.getMessage()
            );

            verify(repository, never())
                    .findByDataRegistroBetween(any(), any());

            verify(mapper, never())
                    .toDTO(any());
        }

        @Test
        void deveRetornarListaVaziaQuandoNaoHouverRegistros() {

            LocalDateTime inicio = LocalDateTime.now().minusDays(5);
            LocalDateTime fim = LocalDateTime.now();

            when(repository.findByDataRegistroBetween(inicio, fim))
                    .thenReturn(List.of());

            List<HistoricoProdutoResponseDTO> response =
                    service.buscarPorPeriodo(inicio, fim);

            assertTrue(response.isEmpty());

            verify(repository, times(1))
                    .findByDataRegistroBetween(inicio, fim);

            verify(mapper, never())
                    .toDTO(any());
        }
    }
}