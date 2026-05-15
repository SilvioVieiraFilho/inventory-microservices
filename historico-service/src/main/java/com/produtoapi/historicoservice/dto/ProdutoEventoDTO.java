package com.produtoapi.historicoservice.dto;

import com.produtoapi.historicoservice.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEventoDTO implements Serializable {
    private Long produtoId;
    private String nome;
    private Double preco;
    private Integer quantidadeAnterior;
    private Integer quantidadeNova;
    private TipoEvento tipoEvento;

}