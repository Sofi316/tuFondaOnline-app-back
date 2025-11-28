package com.example.tuFondaOnline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tuFondaOnline.dto.AuthResponse;
import com.example.tuFondaOnline.dto.LoginRequest;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.JwtService;
import com.example.tuFondaOnline.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private final JwtService jwtService;

    // 1. REGISTRO (Crea usuario y devuelve token)
    // URL: POST http://localhost:8080/auth/registro
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> register(@RequestBody Usuario usuario) {
        // Guardamos el usuario (el servicio ya encripta la contraseña)
        Usuario usuarioGuardado = usuarioService.save(usuario);
        
        // Generamos el token inmediatamente para que quede logueado
        String token = jwtService.generateToken(usuarioGuardado);
        
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // 2. LOGIN (Valida credenciales y devuelve token)
    // URL: POST http://localhost:8080/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        // A. Autenticar con Spring Security
        // Esto verifica usuario y contraseña automáticamente. Si falla, lanza una excepción.
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // B. Si pasamos la línea de arriba, es que las credenciales son correctas.
        // Buscamos al usuario para generar el token.
        UserDetails user = usuarioService.findByEmail(request.getEmail());
        
        // C. Generamos el token
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}