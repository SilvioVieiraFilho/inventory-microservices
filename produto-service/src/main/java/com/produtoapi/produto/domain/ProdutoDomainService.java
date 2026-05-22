package com.produtoapi.produto.domain;

import com.produtoapi.exception.BusinessException;
import com.produtoapi.produto.dto.ProdutoRequestDTO;
import com.produtoapi.produto.enums.ProdutoStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProdutoDomainService {

    public void inicializar(Produto produto) {
        produto.setCreatedAt(LocalDateTime.now());

        if ( produto.getQuantidade() < 0) {
            throw new BusinessException("Quantidade inválida");
        }

        if (produto.getStatus() == null) {
            produto.setStatus(ProdutoStatus.ATIVO);
        }
    }


    public void validarCadastro(ProdutoRequestDTO dto) {

        validarQuantidade(dto);
        validarStatus(dto);
        validarPreco(dto);
    }

    public void validarAtualizacao(ProdutoRequestDTO dto) {

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new BusinessException("Nome não pode ser vazio");
        }

        validarQuantidade(dto);
        validarPreco(dto);

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

        if (dto.getStatus() == ProdutoStatus.ATIVO
                && dto.getQuantidade() <= 0) {

            throw new BusinessException(
                    "Produto ativo deve possuir estoque."
            );
        }
    }

    private void validarPreco(ProdutoRequestDTO dto) {

        if (dto.getPreco() == null || dto.getPreco() <= 0) {
            throw new BusinessException("O preço deve ser maior que zero.");
        }
    }

    private void validarQuantidade(ProdutoRequestDTO dto) {

        if (dto.getQuantidade() == null || dto.getQuantidade() < 0) {
            throw new BusinessException("A quantidade não pode ser negativa.");
        }
    }


    public void validarFiltro(
            String nome,
            ProdutoStatus status,
            Double precoMin,
            Double precoMax
    ) {

        if (nome == null && status == null && precoMin == null && precoMax == null) {
            throw new BusinessException("Informe pelo menos um filtro");
        }

        if (nome != null && nome.length() > 100) {
            throw new BusinessException("Nome muito grande");
        }

        if (precoMin != null && precoMin < 0) {
            throw new BusinessException("Preço mínimo inválido");
        }

        if (precoMax != null && precoMax < 0) {
            throw new BusinessException("Preço máximo inválido");
        }

        if (precoMin != null && precoMax != null && precoMin > precoMax) {
            throw new BusinessException("Preço mínimo não pode ser maior que o máximo");
        }
    }


    public void adicionarQuantidade(Produto produto, int quantidade) {

        if (quantidade <= 0) {
            throw new BusinessException("Quantidade inválida para incremento");
        }

        produto.setQuantidade(produto.getQuantidade() + quantidade);
    }

    public void atualizarProduto(Produto produto, ProdutoRequestDTO dto) {

        validarAtualizacao(dto);

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setStatus(dto.getStatus());
    }
}