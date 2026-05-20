package com.produtoapi.produto.mapper;

import java.util.List;

import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.dto.ProdutoResponseDTO;
import org.mapstruct.Mapper;

import com.produtoapi.produto.dto.ProdutoRequestDTO;
import org.mapstruct.Mapping;

@Mapper(componentModel  = "spring")

public interface ProdutoMapper {

    Produto toEntity(ProdutoRequestDTO dto);
    @Mapping(target = "categoria_id", source = "categoria.id")
    ProdutoResponseDTO toDTO(Produto produto);

    List<Produto> toEntityList(List<ProdutoRequestDTO> dtoList);

    List<ProdutoResponseDTO> toDTOList(List<Produto> entityList);
}