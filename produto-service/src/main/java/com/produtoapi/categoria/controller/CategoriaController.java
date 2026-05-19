package com.produtoapi.categoria.controller;

import com.produtoapi.categoria.ApiResponse;
import com.produtoapi.categoria.dto.CategoriaRequestDTO;
import com.produtoapi.categoria.dto.CategoriaResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.produtoapi.categoria.service.*;
import java.util.Optional;

@RequestMapping("/categorias")
@RestController
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;



    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponseDTO>> salvarCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {

        CategoriaResponseDTO salvar = service.salvarCategoria(dto);

        ApiResponse<CategoriaResponseDTO> response = new ApiResponse<>("Categoria salva com sucesso", salvar);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponseDTO>> listIdCategoria(@PathVariable Long id) {

        CategoriaResponseDTO listarPorId = service
                .listarIdCategoria(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ApiResponse<CategoriaResponseDTO> response =
                new ApiResponse<>(
                        "Categoria listada com sucesso",
                        listarPorId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponseDTO>>> listAllCategory() {

        List<CategoriaResponseDTO> listAllCategory = service.listAllCategory();

        ApiResponse<List<CategoriaResponseDTO>> response =
                new ApiResponse<>(
                        "Categorias listadas com sucesso",
                        listAllCategory
                );

        return ResponseEntity.ok(response);
    }

}








