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
@Tag(name = "Publicaciones", description = "Operaciones relacionadas con las publicaciones")
public class PublicacionController {
    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las publicaciones", description = "Obtiene una lista de todas las publicaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class)))
    })
    public List<Publicacion> getAll(){
        return publicacionService.findAll();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una publicación por ID", description = "Obtiene una publicación específica utilizando su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class))),
        @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    public Publicacion getById(
        @Parameter(description = "ID de la publicación a buscar", required = true) @PathVariable Long id){
        return publicacionService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva publicación", description = "Crea una nueva publicación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicación creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class)))
    })
    public Publicacion create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Publicación a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class),
                examples = @ExampleObject(
                    name = "EjemploPublicacion",
                    value = "{\"id\":1,\"titulo\":\"Título de la publicación\",\"bajada\":\"Bajada de la publicación\",\"detalle\":\"Detalle de la publicación\",\"imagen\":\"imagen.jpg\",\"contenido\":\"Contenido de la publicación\",\"fecha\":\"2024-06-01T12:00:00\"}"
                )
            )
        )
        @RequestBody Publicacion publicacion){
        return publicacionService.save(publicacion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una publicación", description = "Actualiza una publicación existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicación actualizada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class))),
        @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    public Publicacion update(
        @Parameter(description = "ID de la publicación a actualizar", required = true) @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Publicación con los datos actualizados",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Publicacion.class),
                examples = @ExampleObject(
                    name = "EjemploPublicacionActualizada",
                    value = "{\"id\":1,\"titulo\":\"Título de la publicación\",\"bajada\":\"Bajada de la publicación\",\"detalle\":\"Detalle de la publicación\",\"imagen\":\"imagen.jpg\",\"contenido\":\"Contenido de la publicación\",\"fecha\":\"2024-06-01T12:00:00\"}"
                )
            )
        )
        @RequestBody Publicacion publicacion){
        publicacion.setIdPublicacion(id);
        return publicacionService.save(publicacion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una publicación", description = "Elimina una publicación por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicación eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    public void delete(
        @Parameter(description = "ID de la publicación a borrar", required = true) @PathVariable Long id){
        publicacionService.delete(id);
    }
}
