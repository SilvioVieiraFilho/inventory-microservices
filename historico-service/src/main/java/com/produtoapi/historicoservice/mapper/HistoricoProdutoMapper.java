package com.produtoapi.historicoservice.mapper;

import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import org.springframework.stereotype.Component;

@Component
public class HistoricoProdutoMapper {

//    public HistoricoProduto toEntity(HistoricoProdutoRequestDTO dto) {
//
//        return HistoricoProduto.builder()
//                .produtoId(dto.getProdutoId())
//                .quantidadeAnterior(dto.getQuantidadeAnterior())
//                .quantidadeNova(dto.getQuantidadeNova())
//                .diferenca(dto.getDiferenca())
//                .build();
//    }

    // 🔥 aqui é o "toResponse" correto
    public HistoricoProdutoResponseDTO toResponse(HistoricoProduto entity) {

        return HistoricoProdutoResponseDTO.builder()
                .id(entity.getId())
                .produtoId(entity.getProdutoId())
                .nomeProduto(entity.getNomeProduto())
                .quantidadeAnterior(entity.getQuantidadeAnterior())
                .quantidadeNova(entity.getQuantidadeNova())
                .diferenca(entity.getDiferenca())
                .dataRegistro(entity.getDataRegistro())
                .build();
    }

    public HistoricoProdutoResponseDTO toResponseDTO(HistoricoProduto entity) {

        return HistoricoProdutoResponseDTO.builder()
                .id(entity.getId())
                .produtoId(entity.getProdutoId())
                .nomeProduto(entity.getNomeProduto())
                .quantidadeAnterior(entity.getQuantidadeAnterior())
                .quantidadeNova(entity.getQuantidadeNova())
                .diferenca(entity.getDiferenca())
                .dataRegistro(entity.getDataRegistro())
                .build();
    }
}