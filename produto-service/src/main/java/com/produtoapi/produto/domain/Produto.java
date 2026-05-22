package com.produtoapi.produto.domain;

import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.produto.enums.ProdutoStatus;
import com.produtoapi.exception.BusinessException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "informe um nome.")
    private String nome;

    private int quantidade;

    @CreatedDate
    private LocalDateTime createdAt;
    private double preco;

    @Enumerated(EnumType.STRING)
    private ProdutoStatus status;


    @NotNull(message = "Categoria é obrigatória")

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;


}

