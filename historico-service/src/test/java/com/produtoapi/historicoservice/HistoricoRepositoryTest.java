package com.produtoapi.historicoservice;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.enums.TipoEvento;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HistoricoProdutoRepositoryTest {

    @Autowired
    private HistoricoProdutoRepository repository;

    @Test
    void deveBuscarHistoricoPorPeriodo() {

        LocalDateTime inicio = LocalDateTime.now().minusDays(5);
        LocalDateTime fim = LocalDateTime.now();

        HistoricoProduto h1 = HistoricoProduto.builder()
                .nomeProduto("Pilha")
                .quantidadeAnterior(2)
                .quantidadeNova(5)
                .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                .dataRegistro(LocalDateTime.now().minusDays(2))
                .diferenca(3)
                .produtoId(1L)
                .build();

        HistoricoProduto h2 = HistoricoProduto.builder()
                .nomeProduto("Teclado")
                .quantidadeAnterior(1)
                .quantidadeNova(3)
                .tipoEvento(TipoEvento.PRODUTO_CRIADO)
                .dataRegistro(LocalDateTime.now().minusDays(1))
                .diferenca(2)
                .produtoId(2L)
                .build();

        repository.save(h1);
        repository.save(h2);

        List<HistoricoProduto> result =
                repository.findByDataRegistroBetween(inicio, fim);

        assertEquals(2, result.size());
    }
}