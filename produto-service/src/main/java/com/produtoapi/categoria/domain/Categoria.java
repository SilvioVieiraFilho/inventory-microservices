package com.produtoapi.categoria.domain;

import com.produtoapi.categoria.enums.StatusCategoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categoria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @Column(unique = true)
    private String nomeCategoria;

    @NotNull
    private String decricaoCategoria;

    @NotNull
    private StatusCategoria statusCategoria;

    @NotNull
    private LocalDateTime dataDeCriacao;







}
