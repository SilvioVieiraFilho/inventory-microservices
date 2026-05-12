package com.produtoapi.historicoproduto.service;

import com.produtoapi.historicoproduto.client.HistoricoClient;
import com.produtoapi.historicoproduto.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoproduto.enums.TipoEvento;
import com.produtoapi.produto.domain.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoIntegrationService {

    private final HistoricoClient historicoClient;

    public void registrarEvento(Produto produto, TipoEvento tipoEvento, Integer quantidadeAnterior) {

        historicoClient.registrarEvento(

                HistoricoProdutoRequestDTO.builder().produtoId(produto.getId()).nomeProduto(produto.getNome()).tipoEvento(tipoEvento).quantidadeAnterior(quantidadeAnterior).quantidadeNova(produto.getQuantidade()).diferenca(produto.getQuantidade() - quantidadeAnterior).build());
    }
}