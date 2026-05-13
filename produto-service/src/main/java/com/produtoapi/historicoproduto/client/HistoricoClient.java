package com.produtoapi.historicoproduto.client;

import com.produtoapi.historicoproduto.FeignConfig;
import com.produtoapi.historicoproduto.dto.HistoricoProdutoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "historico",
        url = "http://historico-service:8081",
        configuration = FeignConfig.class
)
public interface HistoricoClient {

    @PostMapping("/eventos/produto")
    void registrarEvento(HistoricoProdutoRequestDTO dto);
}
