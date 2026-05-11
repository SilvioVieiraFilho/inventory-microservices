package com.produtoapi.historicoservice.exception;

public class HistoricoNotFoundException extends RuntimeException {

	public HistoricoNotFoundException(Long id) {

		super("Produto não encontrado com ID: " + id);

	}

}
