package com.produtoapi.produto.dto;

import com.produtoapi.produto.enums.TipoEvento;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEventoDTO {
    private UUID eventId;

    private Long produtoId;
    private String nome;
    private Double preco;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private TipoEvento tipoEvento;
}