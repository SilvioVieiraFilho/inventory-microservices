package com.produtoapi.produto.domain;


import com.produtoapi.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class ProdutoDomainService {

    public void atualizar(Produto produto, int quantidade) {

        if (quantidade == 0) {
            throw new BusinessException("Quantidade não pode ser zero");
        }

        if (quantidade > 0) {
            produto.adicionarQuantidade(quantidade);
        } else {
            produto.removerQuantidade(Math.abs(quantidade));
        }
    }

    public void inicializar(Produto produto) {
        produto.definirQuantidade(produto.getQuantidade());
    }


}