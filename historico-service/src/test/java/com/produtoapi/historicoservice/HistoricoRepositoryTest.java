package com.produtoapi.historicoservice;

import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.enums.TipoEvento;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HistoricoRepositoryTest {

    @Autowired
    private HistoricoProdutoRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void deveBuscarHistoricoPorPeriodo() {

        // 🔥 tempo fixo para evitar flakiness
        LocalDateTime base = LocalDateTime.of(2026, 1, 1, 12, 0);

        LocalDateTime inicio = base.minusDays(5);
        LocalDateTime fim = base.plusDays(1);

        HistoricoProduto h1 = HistoricoProduto.builder()
                .quantidadeAnterior(2)
                .quantidadeNova(5)
                .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                .dataRegistro(base.minusDays(2))
                .diferenca(3)
                .produtoId(1L)
                .eventId(UUID.randomUUID())
                .build();

        HistoricoProduto h2 = HistoricoProduto.builder()
                .quantidadeAnterior(1)
                .quantidadeNova(3)
                .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                .dataRegistro(base.minusDays(1))
                .diferenca(2)
                .produtoId(2L)
                .eventId(UUID.randomUUID())
                .build();

        repository.save(h1);
        repository.save(h2);

        List<HistoricoProduto> result =
                repository.findByDataRegistroBetween(inicio, fim);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}