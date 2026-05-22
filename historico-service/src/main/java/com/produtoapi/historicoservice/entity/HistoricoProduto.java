package com.produtoapi.historicoservice.entity;
import com.produtoapi.historicoservice.enums.TipoEvento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historicos_produtos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_id", nullable = false)
    private Long produtoId;


    @NotNull
    @Column(name = "quantidade_anterior", nullable = false)
    private Integer quantidadeAnterior;

    @NotNull
    @Column(name = "quantidade_nova", nullable = false)
    private Integer quantidadeNova;

    @NotNull
    @Column(name = "diferenca", nullable = false)
    private Integer diferenca;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "event_id", nullable = false, unique = true)
    private UUID eventId;

}