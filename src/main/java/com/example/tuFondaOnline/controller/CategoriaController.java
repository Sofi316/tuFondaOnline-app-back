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

import com.example.tuFondaOnline.model.Categoria;
import com.example.tuFondaOnline.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Operaciones relacionadas con las categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorias", description = "Obtiene una lista de todas las categorias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class)))
    })
    public List<Categoria> getAll(){
        return categoriaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoria por ID", description = "Obtiene una categoria específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    })
    public Categoria getById(@Parameter(description = "ID de la categoría a obtener", required = true)
        @PathVariable Long id){
        return categoriaService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva categoria", description = "Crea una nueva categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class)))
    })
    public Categoria create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Categoría a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class),
                examples = @ExampleObject(
                    name = "EjemploCategoria",
                    value = "{ \"idCategoria\": 1, \"nombre\": \"Alimentos\" }"
                )
            )
        )
        @RequestBody Categoria categoria){
        return categoriaService.save(categoria);    
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoria", description = "Actualiza una categoria existente por su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria actualizada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    })
    public Categoria update(@Parameter(description = "ID de la categoría a actualizar", required = true)
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Categoría con los nuevos datos",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class),
                examples = @ExampleObject(
                    name = "EjemploCategoriaActualizada",
                    value = "{ \"idCategoria\": 1, \"nombre\": \"Alimentos\" }"                )
            )
        )
        @RequestBody Categoria categoria){
        categoria.setIdCategoria(id);
        return categoriaService.save(categoria);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoria", description = "Elimina una categoria por su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    })
    public void delete(@Parameter(description = "ID de la categoría a borrar", required = true)
        @PathVariable Long id){
        categoriaService.delete(id);
    }
}
