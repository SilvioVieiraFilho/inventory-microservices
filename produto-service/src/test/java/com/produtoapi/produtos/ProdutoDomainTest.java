package com.produtoapi.produtos;

import com.produtoapi.exception.BusinessException;
import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.domain.ProdutoDomainService;
import com.produtoapi.produto.dto.ProdutoRequestDTO;
import com.produtoapi.produto.enums.ProdutoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDomainTest {

    private ProdutoDomainService domain;

    @BeforeEach
    void setup() {
        domain = new ProdutoDomainService();
    }

    @Test
    void deveInicializarProdutoComValoresPadrao() {

        Produto produto = new Produto();

        produto.setStatus(null);

        domain.inicializar(produto);

        assertEquals(0, produto.getQuantidade());
        assertEquals(ProdutoStatus.ATIVO, produto.getStatus());
        assertNotNull(produto.getCreatedAt());
    }

    @Test
    void deveLancarErroSeQuantidadeNegativaNoInicializar() {

        Produto produto = new Produto();
        produto.setQuantidade(-1);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> domain.inicializar(produto)
        );

        assertEquals("Quantidade inválida", ex.getMessage());
    }

    // =========================
    // VALIDAR CADASTRO
    // =========================
    @Test
    void deveValidarCadastroComSucesso() {

        ProdutoRequestDTO dto = criarDTOValido();

        assertDoesNotThrow(() -> domain.validarCadastro(dto));
    }

    @Test
    void deveFalharQuandoPrecoInvalido() {

        ProdutoRequestDTO dto = criarDTOValido();
        dto.setPreco(0.0);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> domain.validarCadastro(dto)
        );

        assertEquals("O preço deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveFalharQuandoQuantidadeNegativa() {

        ProdutoRequestDTO dto = criarDTOValido();
        dto.setQuantidade(-1);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> domain.validarCadastro(dto)
        );

        assertEquals("A quantidade não pode ser negativa.", ex.getMessage());
    }

    @Test
    void deveFalharQuandoProdutoDesativado() {

        ProdutoRequestDTO dto = criarDTOValido();
        dto.setStatus(ProdutoStatus.DESATIVADO);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> domain.validarCadastro(dto)
        );

        assertTrue(ex.getMessage().contains("Não é permitido"));
    }

    // =========================
    // VALIDAR ATUALIZACAO
    // =========================
    @Test
    void deveValidarAtualizacaoComSucesso() {

        ProdutoRequestDTO dto = criarDTOValido();

        assertDoesNotThrow(() -> domain.validarAtualizacao(dto));
    }

    @Test
    void deveFalharAtualizacaoSemNome() {

        ProdutoRequestDTO dto = criarDTOValido();
        dto.setNome("");

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> domain.validarAtualizacao(dto)
        );

        assertEquals("Nome não pode ser vazio", ex.getMessage());
    }

    // =========================
    // ADICIONAR QUANTIDADE
    // =========================
    @Test
    void deveAdicionarQuantidadeCorretamente() {

        Produto produto = new Produto();
        produto.setQuantidade(10);

        domain.adicionarQuantidade(produto, 5);

        assertEquals(15, produto.getQuantidade());
    }

    @Test
    void deveFalharQuantidadeInvalida() {

        Produto produto = new Produto();
        produto.setQuantidade(10);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> domain.adicionarQuantidade(produto, 0)
        );

        assertEquals("Quantidade inválida para incremento", ex.getMessage());
    }

    // =========================
    // ATUALIZAR PRODUTO
    // =========================
    @Test
    void deveAtualizarProdutoCorretamente() {

        Produto produto = new Produto();
        produto.setQuantidade(10);

        ProdutoRequestDTO dto = criarDTOValido();
        dto.setQuantidade(20);

        domain.atualizarProduto(produto, dto);

        assertEquals("Produto Teste", produto.getNome());
        assertEquals(100.0, produto.getPreco());
        assertEquals(20, produto.getQuantidade());
        assertEquals(ProdutoStatus.ATIVO, produto.getStatus());
    }

    // =========================
    // HELPERS
    // =========================
    private ProdutoRequestDTO criarDTOValido() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO();
        dto.setNome("Produto Teste");
        dto.setPreco(100.0);
        dto.setQuantidade(10);
        dto.setStatus(ProdutoStatus.ATIVO);
        dto.setCategoria_id(1L);
        return dto;
    }
}