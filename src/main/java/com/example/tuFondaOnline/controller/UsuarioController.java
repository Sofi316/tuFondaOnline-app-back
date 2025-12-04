package com.example.tuFondaOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios (Solo Administrador)")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Devuelve la lista de todos los usuarios registrados")
    public List<Usuario> getAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario", description = "Obtiene un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Usuario getById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    // CREAR (Solo Admin)
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario (Admin, Vendedor, etc.)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    })
    public Usuario create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del usuario", required = true,
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                name = "Crear Vendedor",
                value = "{\"nombre\": \"Juan Vendedor\", \"email\": \"vendedor2@duoc.cl\", \"password\": \"1234\", \"rol\": \"VENDEDOR\", \"rut\": \"12.345.678-9\", \"direccion\": \"Sucursal Norte\", \"activo\": true, \"fechaNac\": \"1990-01-01\", \"comuna\": {\"id\": 1}}"
            ))) 
        @RequestBody Usuario usuario) {
        
        
        return usuarioService.save(usuario);
    }

    // ACTUALIZAR 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza datos del usuario sin tocar la contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Usuario update(
        @Parameter(description = "ID del usuario", required = true) @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos a modificar (No incluir password)", required = true,
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                name = "Editar Perfil",
                value = "{\"nombre\": \"Juan Vendedor (Editado)\", \"email\": \"vendedor2@duoc.cl\", \"rol\": \"VENDEDOR\", \"rut\": \"12.345.678-9\", \"direccion\": \"Nueva Dirección 123\", \"activo\": true, \"fechaNac\": \"1990-01-01\", \"comuna\": {\"id\": 1}}"
            ))) 
        @RequestBody Usuario usuario) {
        
        return usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Borra un usuario del sistema")
    public void delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }
}