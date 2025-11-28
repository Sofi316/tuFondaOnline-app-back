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

import com.example.tuFondaOnline.model.Orden;
import com.example.tuFondaOnline.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ordenes")
@Tag(name="Ordenes",description="Operaciones relacionadas con ordenes")
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;

    @GetMapping
    @Operation(summary = "Obtener todas las ordenes", description = "Obtiene una lista de todas las ordenes")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Operación exitosa"),
        @ApiResponse(responseCode = "404",description = "Orden no encontrada",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Orden.class)))
    })
    public List<Orden> listarOrdenes() {
        return ordenService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por id", description = "Obtiene una orden por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode="404", description = "Orden no encontrada")
    })
    public Orden obtenerOrdenPorId(
        @Parameter(description="ID de la ordena  buscar", required=true) @PathVariable Long id) {
        return ordenService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva orden", description = "Crea una nueva orden")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Orden creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema=@Schema(implementation=Orden.class))),
    })
    public Orden crearOrden(@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Orden a crear", 
        required = true, 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = Orden.class), 
            examples = @ExampleObject(
                name = "EjemploOrden", 
                value = "{\"usuario\": {\"id\": 1}, \"fechaOrden\": \"2025-07-05\",\"estado\": \"pendiente\", \"total\": 6500}"
                )
            )
        ) @RequestBody Orden orden) {
        return ordenService.save(orden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una orden", description = "Actualiza una orden existente")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Orden actualizada exitosamente",
            content = @Content(mediaType = "application/json",
                schema=@Schema(implementation=Orden.class))),
        @ApiResponse(responseCode = "404",description = "Orden no encontrada")
    })
    public Orden actualizarOrden(@Parameter(description="ID de la orden a actualizar", required=true)
        @PathVariable Long id,@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la orden actualizados",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Orden.class),
                examples = @ExampleObject(
                name = "EjemploOrdenActualizada", 
                value = "{\"usuario\": {\"id\": 1}, \"fechaOrden\": \"2025-07-05\",\"estado\": \"pendiente\", \"total\": 6500}"))) 
        @RequestBody Orden orden) {
        orden.setId(id);
        return ordenService.save(orden);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una orden", description = "Elimina una orden por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Orden eliminada exitosamente"),
        @ApiResponse(responseCode = "404",description = "Orden no encontrada")
    })
    public void eliminarOrden(@Parameter(description="ID de la orden a borrar", required=true)
     @PathVariable Long id) {
        ordenService.delete(id);
    }
}