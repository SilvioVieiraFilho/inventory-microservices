package com.produtoapi.historicoservice.consumer;

import com.produtoapi.historicoservice.dto.ProdutoEventoDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoricoConsumer{
        private final HistoricoProdutoRepository repository;

        public HistoricoConsumer(HistoricoProdutoRepository repository) {
            this.repository = repository;
        }

        @KafkaListener(topics = "historico-produto-topic", groupId = "historico-group")
        public void consumir(ProdutoEventoDTO dto) {

            if (dto.getTipoEvento() == null) {
                throw new RuntimeException("TipoEvento não pode ser nulo");
            }

            if (dto.getQuantidadeAnterior() == null ||
                    dto.getQuantidadeNova() == null) {
                throw new RuntimeException("Quantidades não podem ser nulas");
            }


            HistoricoProduto historico = HistoricoProduto.builder()
                    .produtoId(dto.getProdutoId())
                    .nomeProduto(dto.getNome())
                    .tipoEvento(dto.getTipoEvento())
                    .quantidadeAnterior(dto.getQuantidadeAnterior())
                    .quantidadeNova(dto.getQuantidadeNova())
                    .diferenca(dto.getQuantidadeNova() - dto.getQuantidadeAnterior())
                    .dataRegistro(LocalDateTime.now())
                    .build();


            repository.save(historico);
        }
    }

