package com.produtoapi.historicoservice.client;

import com.produtoapi.historicoservice.dto.ProdutoDTO;

import com.produtoapi.historicoservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "historicoClient", url = "http://localhost:8081", configuration = FeignConfig.class)

public interface ProdutoClient {


    @GetMapping("/produtos/{id}")
    ProdutoDTO buscarPorId(@PathVariable("id") Long id);
}