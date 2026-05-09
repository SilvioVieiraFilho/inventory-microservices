package com.produtoapi.historicoservice.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.produtoapi.historicoservice.enums.TipoEvento;
import lombok.Builder;
import lombok.Data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoProdutoRequestDTO {

    private Long produtoId;
    private String nomeProduto;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private Integer diferenca;
    private TipoEvento tipoEvento;
}

