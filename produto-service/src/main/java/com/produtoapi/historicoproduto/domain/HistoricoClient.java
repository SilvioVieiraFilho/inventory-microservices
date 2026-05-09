package com.produtoapi.historicoproduto.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "historico-service", url = "http://localhost:8081")
public interface HistoricoClient {

    @PostMapping("/eventos/produto")
    void registrarEvento(@RequestBody HistoricoProdutoRequestDTO dto);
}

