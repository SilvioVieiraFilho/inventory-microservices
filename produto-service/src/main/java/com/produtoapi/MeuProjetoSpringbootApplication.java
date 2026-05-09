package com.produtoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MeuProjetoSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuProjetoSpringbootApplication.class, args);
	}

}
