package com.produtoapi.security;
import com.produtoapi.security.service.JwtService;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup() {

        jwtService = new JwtService();

        String secret = "4A6D686F344B564D527952376A576E5A72347537782125412A4428472B4B6250";

        ReflectionTestUtils.setField(jwtService, "secret", secret);

        Key key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        ReflectionTestUtils.setField(jwtService, "key", key);
    }

    @Test
    void deveGerarTokenComSucesso() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        assertNotNull(token);

        assertFalse(token.isEmpty());
    }

    @Test
    void deveExtrairUsernameDoToken() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        String username = jwtService.extractUsername(token);

        assertEquals("user@email.com", username);
    }

    @Test
    void deveExtrairRoleDoToken() {

        String token = jwtService.generateToken(
                "user@email.com",
                "ADMIN"
        );

        String role = jwtService.extractRole(token);

        assertEquals("ADMIN", role);
    }

    @Test
    void deveValidarTokenCorretamente() {

        String token = jwtService.generateToken(
                "user@email.com",
                "USER"
        );

        boolean valid = jwtService.isTokenValid(token);

        assertTrue(valid);
    }

    @Test
    void deveFalharComTokenInvalido() {

        assertThrows(Exception.class, () -> {

            jwtService.extractUsername("token-invalido");

        });
    }
}