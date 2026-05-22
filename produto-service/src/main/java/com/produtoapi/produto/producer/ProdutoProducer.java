package com.produtoapi.produto.producer;

import com.produtoapi.produto.dto.ProdutoEventoDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProdutoProducer {

    private final KafkaTemplate<String, ProdutoEventoDTO> kafkaTemplate;

    public ProdutoProducer(
            KafkaTemplate<String, ProdutoEventoDTO> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEvento(ProdutoEventoDTO evento) {


        if (evento.getEventId() == null) {
            evento.setEventId(UUID.randomUUID());
        }

        kafkaTemplate.send(
                "historico-produto-topic",
                evento.getProdutoId().toString(),
                evento


        );
    }
}
