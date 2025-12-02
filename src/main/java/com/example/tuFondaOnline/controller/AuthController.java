package com.example.tuFondaOnline.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tuFondaOnline.dto.AuthResponse;
import com.example.tuFondaOnline.dto.LoginRequest;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.JwtService;
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

    @Autowired
    private final JwtService jwtService;

    //Registrar usuario público
    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario y devuelve el token de acceso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe")
    })
    public ResponseEntity<AuthResponse> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo usuario",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class),
                examples = @ExampleObject(
                    name = "Ejemplo Cliente",
                    value = "{\"nombre\": \"Cliente Nuevo\", \"email\": \"prueba@duoc.cl\", \"password\": \"1234\", \"rut\": \"16.666.333-4\", \"direccion\": \"Av. Siempre Viva 742\", \"activo\": true, \"fechaNac\": \"1998-05-20\", \"comuna\": {\"id\": 1}}"
                )
            )
        )
        @RequestBody Usuario usuario) {
        
        // Forzamos rol y fecha para seguridad
        usuario.setRol("CLIENTE");
        usuario.setFechaRegistro(LocalDate.now());

        
        // Guardamos
        Usuario usuarioGuardado = usuarioService.save(usuario);
        
        // Generamos token
        String token = jwtService.generateToken(usuarioGuardado);
        
        // Devolvemos Token y Usuario completo (para el Frontend)
        return ResponseEntity.ok(new AuthResponse(token, usuarioGuardado));
    }
     //Login usuario 
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y devuelve un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "403", description = "Credenciales incorrectas")
    })
    public ResponseEntity<AuthResponse> login(
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
        
        // 1. Autenticar (Si falla, lanza error aquí mismo)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Buscar al usuario 
    
        Usuario usuarioReal = usuarioService.findByEmail(request.getEmail());
        
        // 3. Generar Token
        String token = jwtService.generateToken(usuarioReal);
        
        // 4. Devolver respuesta compuesta
        return ResponseEntity.ok(new AuthResponse(token, usuarioReal));
    }
}