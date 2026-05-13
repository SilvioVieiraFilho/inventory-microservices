package com.produtoapi.security;

import com.produtoapi.security.filter.JwtAuthenticationFilter;
import com.produtoapi.security.service.JwtService;
import com.produtoapi.usuario.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private UsuarioRepository usuarioRepository;
    private JwtService jwtService;
    private JwtAuthenticationFilter filter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setup() {

        jwtService = mock(JwtService.class);

        usuarioRepository = mock(UsuarioRepository.class);

        request = mock(HttpServletRequest.class);

        response = mock(HttpServletResponse.class);

        filterChain = mock(FilterChain.class);

        filter = new JwtAuthenticationFilter(
                jwtService,
                usuarioRepository
        );

        SecurityContextHolder.clearContext();
    }

    @Test
    void devePassarQuandoTokenValido() throws Exception {

        String token = "fake-token";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.isTokenValid(token))
                .thenReturn(true);

        when(jwtService.extractUsername(token))
                .thenReturn("silvio@email.com");

        when(jwtService.extractRole(token))
                .thenReturn("USER");

        filter.doFilter(request, response, filterChain);

        assertNotNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        assertEquals(
                "silvio@email.com",
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
        );

        verify(filterChain, times(1))
                .doFilter(request, response);
    }

    @Test
    void naoDeveAutenticarQuandoHeaderNulo() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        filter.doFilter(request, response, filterChain);

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        verify(filterChain, times(1))
                .doFilter(request, response);
    }

    @Test
    void naoDeveAutenticarQuandoHeaderInvalido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Invalid");

        filter.doFilter(request, response, filterChain);

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        verify(filterChain, times(1))
                .doFilter(request, response);
    }
}