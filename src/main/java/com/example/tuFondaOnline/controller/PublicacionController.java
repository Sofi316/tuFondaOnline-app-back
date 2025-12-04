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

import com.example.tuFondaOnline.model.Publicacion;
import com.example.tuFondaOnline.service.PublicacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/publicaciones")
@Tag(name = "Publicaciones (Blog)", description = "Gestión de noticias y artículos del blog")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    @Operation(summary = "Listar publicaciones", description = "Obtiene todos los artículos del blog")
    public List<Publicacion> listarPublicaciones() {
        return publicacionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar publicación", description = "Obtiene un artículo específico por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe")
    })
    public Publicacion obtenerPublicacion(@PathVariable Long id) {
        return publicacionService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear publicación", description = "Publica un nuevo artículo (Solo Admin)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Publicacion.class)))
    })
    public Publicacion crearPublicacion(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del artículo",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class),
                examples = @ExampleObject(
                    name = "Ejemplo Juegos Típicos",
                    value = "{\n" +
                            "  \"titulo\": \"Los mejores juegos típicos\",\n" +
                            "  \"bajada\": \"Descubre cómo divertirte con el emboque, el trompo y el palo ensebado.\",\n" +
                            "  \"detalle\": \"Tradiciones Patrias\",\n" +
                            "  \"imagen\": \"/imagenes/blogs/juegos.jpg\",\n" +
                            "  \"contenido\": \"Chile tiene una rica tradición de juegos... (texto largo)...\"\n" +
                            "}"
                )
            )
        )
        @RequestBody Publicacion publicacion) {
        return publicacionService.save(publicacion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar publicación", description = "Modifica un artículo existente (Solo Admin)")
    public Publicacion actualizarPublicacion(
        @Parameter(description = "ID de la publicación", required = true) @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class),
                examples = @ExampleObject(
                    name = "Editar Juegos",
                    value = "{\n" +
                            "  \"titulo\": \"Los mejores juegos típicos chilenos (Editado)\",\n" +
                            "  \"bajada\": \"Nueva bajada actualizada para el artículo.\",\n" +
                            "  \"detalle\": \"Tradiciones\",\n" +
                            "  \"imagen\": \"/imagenes/blogs/juegos_v2.jpg\",\n" +
                            "  \"contenido\": \"Contenido corregido y aumentado...\"\n" +
                            "}"
                )
            )
        )
        @RequestBody Publicacion publicacion) {
        publicacion.setId(id);
        return publicacionService.save(publicacion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar publicación", description = "Elimina un artículo del blog (Solo Admin)")
    public void eliminarPublicacion(@PathVariable Long id) {
        publicacionService.delete(id);
    }
}