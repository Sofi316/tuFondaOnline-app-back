package com.example.tuFondaOnline.controller;

import com.example.tuFondaOnline.dto.LoginRequest;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Registro y login sin seguridad")
public class AuthController {

    private final UsuarioService usuarioService;

    // REGISTRO
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        usuario.setRol("CLIENTE");
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    // LOGIN MANUAL (SIN SEGURIDAD)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.findByEmail(request.getEmail())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Email incorrecto");
        }

        if (!usuario.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(usuario);
    }
}
