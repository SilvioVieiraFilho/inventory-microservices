package com.produtoapi.historicoproduto.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoProdutoRequestDTO {

    private Long produtoId;

    private String nomeProduto;

    private TipoEvento tipoEvento;

    private Integer quantidadeAnterior;

    private Integer quantidadeNova;

    private Integer diferenca;
}