package com.produtoapi.historicoproduto;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            var auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || auth.getCredentials() == null) return;

            String token = auth.getCredentials().toString();

            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
