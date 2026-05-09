package com.produtoapi.historicoservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @JsonProperty("produto_id")
    private Long produtoId;

    @JsonProperty("nome_produto")
    private String nomeProduto;

    @JsonProperty("quantidade")
    private Integer quantidade;
}