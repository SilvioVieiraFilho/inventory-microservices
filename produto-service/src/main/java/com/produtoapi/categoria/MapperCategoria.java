package com.produtoapi.categoria;

import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.dto.CategoriaResponseDTO;
import com.produtoapi.usuario.domain.Usuario;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperCategoria {

    CategoriaResponseDTO toResponse(Categoria categoria);

    Categoria toEntity(CategoriaResponseDTO dto);


    CategoriaResponseDTO toDTO(Categoria categoria);



}
