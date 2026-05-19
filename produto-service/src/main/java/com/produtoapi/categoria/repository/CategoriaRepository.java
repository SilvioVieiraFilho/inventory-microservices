package com.produtoapi.categoria.repository;

import com.produtoapi.categoria.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {


    Categoria findByNomeCategoria(String nomeCategoria);}
