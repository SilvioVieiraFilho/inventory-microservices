package com.produtoapi.historicoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {

            var auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null) return;

            Object credentials = auth.getCredentials();

            if (credentials instanceof String token) {
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}