package com.produtoapi.categoria.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequestDTO {
    private String nomeCategoria;
    private String decricaoCategoria;


}
