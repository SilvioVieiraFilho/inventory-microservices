package com.produtoapi.security.filter;

import com.produtoapi.security.service.JwtService;
import com.produtoapi.usuario.domain.Usuario;
import com.produtoapi.usuario.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);


        try {

            String email = jwtService.extractUsername(token);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                Usuario usuario = usuarioRepository
                        .findByEmail(email)
                        .orElse(null);

                if (usuario == null) {

                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Usuário não encontrado"
                    );

                    return;
                }

                if (!jwtService.isTokenValid(token)) {

                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Token inválido"
                    );

                    return;
                }

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + usuario.getRole()
                                        )
                                )
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
            }

        } catch (Exception e) {


            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token inválido"
            );

            return;
        }

        filterChain.doFilter(request, response);
    }
}