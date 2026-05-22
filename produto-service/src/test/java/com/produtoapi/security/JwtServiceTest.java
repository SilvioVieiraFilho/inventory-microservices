package com.produtoapi.security;
import com.produtoapi.security.service.JwtService;

import com.produtoapi.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                "minha-chave-super-secreta-com-mais-de-32-bytes"
        );
    }

    @Test
    void deveGerarTokenComSucesso() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void deveExtrairUsernameDoToken() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        String username = jwtService.extractUsername(token);

        assertEquals(
                "user@email.com",
                username
        );
    }

    @Test
    void deveExtrairRoleDoToken() {

        String token = jwtService.generateToken(
                "user@email.com",
                "ADMIN"
        );

        String role = jwtService.extractRole(token);

        assertEquals(
                "ADMIN",
                role
        );
    }

    @Test
    void deveValidarTokenCorretamente() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        assertTrue(
                jwtService.isTokenValid(token)
        );
    }

    @Test
    void deveRetornarFalseQuandoTokenForInvalido() {

        assertFalse(
                jwtService.isTokenValid("token-invalido")
        );
    }

    @Test
    void deveFalharComTokenInvalido() {

        assertThrows(
                Exception.class,
                () -> jwtService.extractUsername("token-invalido")
        );
    }

    @Test
    void deveGerarTokenComUsuarioEExtrairId() {

        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setRole("USER");

        String token = jwtService.generateToken(usuario);

        Long userId = jwtService.extractUserId(token);

        assertEquals(
                10L,
                userId
        );
    }

    @Test
    void deveExtrairRoleDoTokenGeradoComUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRole("ADMIN");

        String token = jwtService.generateToken(usuario);

        String role = jwtService.extractRole(token);

        assertEquals(
                "ADMIN",
                role
        );
    }

    @Test
    void deveExtrairClaimsDoToken() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        assertNotNull(
                jwtService.extractAllClaims(token)
        );
    }
}