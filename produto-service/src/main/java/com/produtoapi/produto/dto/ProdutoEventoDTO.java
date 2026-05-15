package com.produtoapi.produto.dto;

import com.produtoapi.historicoproduto.enums.TipoEvento;
import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEventoDTO {

    private Long produtoId;
    private String nome;
    private Double preco;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private TipoEvento tipoEvento;
}