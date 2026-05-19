package com.produtoapi.categoria.dto;

import com.produtoapi.categoria.enums.StatusCategoria;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CategoriaResponseDTO {

    private Long id;

    private String nomeCategoria;


    private String decricaoCategoria;


    private StatusCategoria statusCategoria;


    private LocalDateTime dataDeCriacao;



}
