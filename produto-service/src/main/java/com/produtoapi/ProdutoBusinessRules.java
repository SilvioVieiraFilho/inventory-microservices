package com.produtoapi;

import com.produtoapi.exception.BusinessException;
import com.produtoapi.produto.dto.ProdutoRequestDTO;
import com.produtoapi.produto.enums.ProdutoStatus;
import org.springframework.stereotype.Component;

@Component
public class ProdutoBusinessRules {

    public void validarCadastro(ProdutoRequestDTO dto) {
        validarQuantidade(dto);
        validarStatus(dto);
    }

    private void validarQuantidade(ProdutoRequestDTO dto) {
        if (dto.getQuantidade() <= 0) {
            throw new BusinessException(
                    "A quantidade deve ser maior que zero."
            );
        }
    }

    private void validarStatus(ProdutoRequestDTO dto) {

        if (dto.getStatus() == ProdutoStatus.DESATIVADO) {
            throw new BusinessException(
                    "Não é permitido cadastrar produto desativado."
            );
        }

        if (dto.getStatus() == ProdutoStatus.ESGOTADO
                && dto.getQuantidade() > 0) {
            throw new BusinessException(
                    "Produto esgotado deve possuir quantidade zero."
            );
        }
    }
}