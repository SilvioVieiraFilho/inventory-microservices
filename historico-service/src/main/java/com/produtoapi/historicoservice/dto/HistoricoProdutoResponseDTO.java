package com.produtoapi.historicoservice.dto;

import com.produtoapi.historicoservice.enums.TipoEvento;
import com.sun.java.accessibility.util.EventID;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoProdutoResponseDTO {

    private Long id;

    private Long produtoId;

    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private Integer diferenca;
    private LocalDateTime dataRegistro;
    private TipoEvento tipoEvento;
}