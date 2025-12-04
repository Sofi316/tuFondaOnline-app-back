package com.example.tuFondaOnline.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tuFondaOnline.dto.LoginRequest;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UsuarioService usuarioService;

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe")
    })
    public ResponseEntity<Usuario> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo usuario",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class),
                examples = @ExampleObject(
                    name = "Ejemplo Cliente",
                    value = "{\"nombre\": \"Cliente Nuevo\", \"email\": \"prueba@duoc.cl\", \"password\": \"1234\", \"rut\": \"16.666.333-4\",\"direccion\": \"Av. Siempre Viva 742\", \"comuna\": {\"id\": 1}}"
                )
            )
        )
        @RequestBody Usuario usuario) {
        
        usuario.setRol("CLIENTE");

        Usuario usuarioGuardado = usuarioService.save(usuario);
        
        return ResponseEntity.ok(usuarioGuardado);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Verifica credenciales y devuelve el usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "403", description = "Credenciales incorrectas")
    })
    public ResponseEntity<Usuario> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciales de acceso",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginRequest.class),
                examples = @ExampleObject(
                    name = "Login Admin",
                    value = "{\"email\": \"admin@duoc.cl\", \"password\": \"admin123\"}"
                )
            )
        )
        @RequestBody LoginRequest request) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuarioReal = usuarioService.findByEmail(request.getEmail());
        
        return ResponseEntity.ok(usuarioReal);
    }
}