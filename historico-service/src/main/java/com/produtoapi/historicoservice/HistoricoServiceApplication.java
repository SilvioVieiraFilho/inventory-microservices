package com.produtoapi.historicoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.produtoapi")
@SpringBootApplication
public class HistoricoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricoServiceApplication.class, args);
	}

}
