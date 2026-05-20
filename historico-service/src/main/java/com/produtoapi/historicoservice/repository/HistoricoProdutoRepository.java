package com.produtoapi.historicoservice.repository;

import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistoricoProdutoRepository extends JpaRepository<HistoricoProduto,Long> {
    List<HistoricoProduto> findByDataRegistroBetween(LocalDateTime dataInicio,LocalDateTime dataFim);

}
