package com.produtoapi.historicoservice.dto;

import com.produtoapi.historicoservice.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEventoDTO implements Serializable {

    private UUID eventId;
    private Long produtoId;
    private String nome;
    private Double preco;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private TipoEvento tipoEvento;

}