package com.produtoapi.historicoservice.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.produtoapi.historicoservice.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long produtoId;

    @NotNull
    private Integer quantidadeAnterior;
    @NotNull
    private Integer quantidadeNova;
    @NotNull
    private Integer diferenca;
    @NotNull
    private TipoEvento tipoEvento;
}

