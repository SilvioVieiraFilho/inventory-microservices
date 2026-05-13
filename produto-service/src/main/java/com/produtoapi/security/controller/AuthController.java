package com.produtoapi.security.controller;


import com.produtoapi.security.dto.LoginResponseDTO;
import com.produtoapi.usuario.dto.UsuarioRequestDTO;
import com.produtoapi.usuario.dto.UsuarioResponseDTO;
import com.produtoapi.usuario.domain.Usuario;

import com.produtoapi.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.produtoapi.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import response.ApiResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @RequestBody UsuarioRequestDTO request
    ) {

        UsuarioResponseDTO usuario = usuarioService.autenticar(
                request.getEmail(),
                request.getSenha()
        );

        String token = jwtService.generateToken(
                request.getEmail(),
                usuario.getRole()
        );

        LoginResponseDTO responseDTO = new LoginResponseDTO(
                token,
                usuario.getEmail(),
                usuario.getRole()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Login realizado com sucesso",
                        responseDTO
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {

        UsuarioResponseDTO usuario = usuarioService.salvar(usuarioRequestDTO);

        Usuario usuarioEntity = new Usuario();

        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setRole(usuario.getRole());

        String token = jwtService.generateToken(usuarioEntity);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Usuário cadastrado com sucesso",
                        "OK",
                        token
                )
        );
    }
}