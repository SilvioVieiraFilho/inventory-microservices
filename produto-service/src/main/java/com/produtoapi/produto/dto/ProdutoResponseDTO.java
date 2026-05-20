package com.produtoapi.produto.dto;

import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.enums.ProdutoStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoResponseDTO {

	private Long id;
	private String nome;
	private Double preco;
	private int quantidade;
	private ProdutoStatus status;

	private Long categoria_id;

	public ProdutoResponseDTO(Produto p) {
		this.id = p.getId();
		this.nome = p.getNome();
		this.preco = p.getPreco();
		this.quantidade = p.getQuantidade();
		this.status = p.getStatus();
		this.categoria_id =
				p.getCategoria() != null
						? p.getCategoria().getId()
						: null;
	}
	}


