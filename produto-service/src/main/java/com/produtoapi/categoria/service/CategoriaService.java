package com.produtoapi.categoria.service;

import com.produtoapi.categoria.MapperCategoria;
import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.enums.StatusCategoria;
import com.produtoapi.categoria.repository.CategoriaRepository;
import com.produtoapi.categoria.dto.CategoriaRequestDTO;
import com.produtoapi.categoria.dto.CategoriaResponseDTO;
import com.produtoapi.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoriaService {

    private final MapperCategoria mapper;
    private final CategoriaRepository repository;

    public CategoriaResponseDTO salvarCategoria(CategoriaRequestDTO dto) {

        Categoria categoria = Categoria.builder()
                .nomeCategoria(dto.getNomeCategoria())
                .decricaoCategoria(dto.getDescricaoCategoria())
                .dataDeCriacao(LocalDateTime.now())
                .statusCategoria(StatusCategoria.ATIVO)
                .build();

        Categoria categoria1 = repository.findByNomeCategoria(dto.getNomeCategoria());

        if (categoria1 != null) {
            throw new BusinessException("Categoria ja cadastrada");

        }

        Categoria salvo = repository.save(categoria);

        return mapper.toResponse(salvo);


    }

    public Optional<CategoriaResponseDTO> listarIdCategoria(Long id) {

        Optional<Categoria> listaCategoria = repository.findById(id);

        if (listaCategoria.isEmpty()) {
            return Optional.empty();
        }

        return listaCategoria.map(mapper::toDTO);

    }

    public List<CategoriaResponseDTO> listAllCategory() {
        return repository.findAll().stream().map(mapper::toDTO).toList();


    }
}


