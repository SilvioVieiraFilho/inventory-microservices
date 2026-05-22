package com.produtoapi.produto.repository;
import com.produtoapi.produto.enums.ProdutoStatus;

import java.util.List;
import java.util.Optional;

import com.produtoapi.produto.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto,Long>,
	JpaSpecificationExecutor<Produto> {
	boolean existsByNomeAndPrecoAndStatus(String nome, Double preco, ProdutoStatus status);
List<Produto> findByNome(String nome);
Optional<Produto> findByNomeAndPrecoAndStatus(String nome, Double preco, ProdutoStatus status);

	@Query("SELECT p FROM Produto p JOIN FETCH p.categoria")
	Page<Produto> findAll(Pageable pageable);

	Optional<Produto> findByNomeAndCategoria_Id(String nome, Long categoriaId);

}
