package com.produtoapi.historicoservice.client;

import com.produtoapi.historicoservice.dto.ProdutoDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produto-client", url = "http://localhost:8080", configuration = FeignClientsConfiguration.class)
public interface ProdutoClient {


    @GetMapping("/produtos/{id}")
    ProdutoDTO buscarPorId(@PathVariable("id") Long id);
}