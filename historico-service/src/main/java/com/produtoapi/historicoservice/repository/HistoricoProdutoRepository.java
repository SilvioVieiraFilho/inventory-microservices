package com.produtoapi.historicoservice.repository;

import com.produtoapi.historicoservice.entity.HistoricoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoProdutoRepository extends JpaRepository<HistoricoProduto,Long> {
}
