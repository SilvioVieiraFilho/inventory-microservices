package com.produtoapi.historicoservice.consumer;

import com.produtoapi.historicoservice.ProcessedEvent;
import com.produtoapi.historicoservice.dto.ProdutoEventoDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import com.produtoapi.historicoservice.repository.ProcessedEventRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service
public class HistoricoConsumer {

    private final HistoricoProdutoRepository repository;
    private final ProcessedEventRepository processedEventRepository;


    @KafkaListener(topics = "historico-produto-topic", groupId = "historico-group")
    public void consumir(ProdutoEventoDTO dto) {

        System.out.println("📥 CONSUMINDO: " + dto);

        if (processedEventRepository.existsById(dto.getEventId())) {
            return;
        }

        processedEventRepository.save(
                new ProcessedEvent(dto.getEventId(), LocalDateTime.now())
        );

        HistoricoProduto historico = HistoricoProduto.builder()
                .produtoId(dto.getProdutoId())
                .tipoEvento(dto.getTipoEvento())
                .quantidadeAnterior(dto.getQuantidadeAnterior())
                .quantidadeNova(dto.getQuantidadeNova())
                .diferenca(dto.getQuantidadeNova() - dto.getQuantidadeAnterior())
                .dataRegistro(LocalDateTime.now())
                .eventId(dto.getEventId())
                .build();

        repository.save(historico);
    }


    private void validar(ProdutoEventoDTO dto) {

        if (dto.getEventId() == null) {
            throw new RuntimeException("eventId não pode ser nulo");
        }

        if (dto.getTipoEvento() == null) {
            throw new RuntimeException("TipoEvento não pode ser nulo");
        }

        if (dto.getQuantidadeAnterior() == null ||
                dto.getQuantidadeNova() == null) {
            throw new RuntimeException("Quantidades não podem ser nulas");
        }
    }
}