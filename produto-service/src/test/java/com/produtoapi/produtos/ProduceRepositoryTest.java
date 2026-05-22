package com.produtoapi.produtos;

import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.enums.StatusCategoria;
import com.produtoapi.categoria.repository.CategoriaRepository;
import com.produtoapi.produto.enums.ProdutoStatus;
import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)class ProduceRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria criarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("Informática");
        categoria.setDecricaoCategoria("Categoria de teste");
        categoria.setDataDeCriacao(LocalDateTime.now());
        categoria.setStatusCategoria(StatusCategoria.ATIVO);

        return categoriaRepository.save(categoria);
    }

    @Test
    @DisplayName("Deve salvar e buscar produto por nome")
    void deveBuscarProdutoPorNome() {

        Categoria categoria = criarCategoria();


        Produto produto = new Produto();
        produto.setNome("Mouse");
        produto.setPreco(100.0);
        produto.setStatus(ProdutoStatus.ATIVO);
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        List<Produto> resultado =
                produtoRepository.findByNome("Mouse");

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0))
                .extracting(Produto::getNome)
                .isEqualTo("Mouse");

    }

    @Test
    @DisplayName("Deve verificar se produto existe por nome, preço e status")
    void deveVerificarExistenciaProduto() {

        Categoria categoria = criarCategoria();

        Produto produto = new Produto();
        produto.setNome("Teclado");
        produto.setPreco(200.0);
        produto.setStatus(ProdutoStatus.ATIVO);
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        boolean existe =
                produtoRepository.existsByNomeAndPrecoAndStatus(
                        "Teclado",
                        200.0,
                        ProdutoStatus.ATIVO
                );

        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("Deve buscar produto por nome, preço e status")
    void deveBuscarPorNomePrecoStatus() {

        Categoria categoria = criarCategoria();

        Produto produto = new Produto();
        produto.setNome("Monitor");
        produto.setPreco(900.0);
        produto.setStatus(ProdutoStatus.ATIVO);
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        Optional<Produto> resultado =
                produtoRepository.findByNomeAndPrecoAndStatus(
                        "Monitor",
                        900.0,
                        ProdutoStatus.ATIVO
                );

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome())
                .isEqualTo("Monitor");
    }
}