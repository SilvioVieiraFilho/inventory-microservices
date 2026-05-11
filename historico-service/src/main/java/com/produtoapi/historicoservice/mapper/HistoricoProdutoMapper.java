package com.produtoapi.historicoservice.mapper;

import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel  = "spring")

public interface HistoricoProdutoMapper {

    HistoricoProduto toEntity(HistoricoProdutoRequestDTO dto);

    HistoricoProdutoResponseDTO toDTO(HistoricoProduto historicoProduto);

}