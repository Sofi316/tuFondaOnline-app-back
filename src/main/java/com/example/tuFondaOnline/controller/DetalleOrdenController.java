package com.example.tuFondaOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.tuFondaOnline.model.DetalleOrden;
import com.example.tuFondaOnline.model.Orden;
import com.example.tuFondaOnline.model.Producto; 
import com.example.tuFondaOnline.service.DetalleOrdenService;
import com.example.tuFondaOnline.service.OrdenService;
import com.example.tuFondaOnline.service.ProductoService; 

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/detalle_orden")
@Tag(name="DetalleOrden",description="Operaciones relacionadas con el detalle de las ordenes")
public class DetalleOrdenController {
    
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private ProductoService productoService; 

    @GetMapping
    @Operation(
        summary = "Obtener todos los detalles de orden", 
        description = "Obtiene una lista de todos los detalles"
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Operación exitosa"),
        @ApiResponse(responseCode = "404",description = "Detalle de orden no encontrado",
        content=@Content(mediaType = "application/json",
            schema = @Schema(implementation = DetalleOrden.class))
        )
    })
    public List<DetalleOrden> listarDetalleOrden() {
        return detalleOrdenService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de orden por id", description = "Obtiene un detalle de orden por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode="404", description = "Detalle de orden no encontrado")
    })
    public DetalleOrden obtenerDetalleOrdenPorId(
        @Parameter(description="ID del detalle a buscar", required=true) 
        @PathVariable Long id
    ) {
        return detalleOrdenService.findById(id);
    }

    @GetMapping("/orden/{idOrden}")
    public List<DetalleOrden> obtenerDetallesPorOrden(@PathVariable Long idOrden) {
        return detalleOrdenService.findByOrdenId(idOrden);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de orden", description = "Crea un nuevo detalle de orden")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DetalleOrden.class)))
    })
    public DetalleOrden crearDetalleOrden(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Detalle de orden a crear",
            required= true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DetalleOrden.class),
                examples = @ExampleObject(
                    name = "EjemploDetalleOrden",
                    value = "{\"orden\": {\"id\": 1}, \"producto\": {\"id\": 2}, \"cantidad\": 3, \"precio\": 1500}"
                )
            )
        )
        @RequestBody DetalleOrden detalleOrden
    ) {
        
        if (detalleOrden.getOrden() == null || detalleOrden.getOrden().getId() == null) {
            throw new RuntimeException("Falta el ID de la orden");
        }
        if (detalleOrden.getProducto() == null || detalleOrden.getProducto().getId() == null) {
            throw new RuntimeException("Falta el ID del producto");
        }

        Orden ordenObjetivo = ordenService.findById(detalleOrden.getOrden().getId());
        detalleOrden.setOrden(ordenObjetivo);

        Producto productoReal = productoService.findById(detalleOrden.getProducto().getId());
        detalleOrden.setProducto(productoReal);

        return detalleOrdenService.save(detalleOrden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un detalle de orden", description = "Actualiza un detalle de orden existente")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode="404", description = "Detalle de orden no encontrado")
    })
    public DetalleOrden actualizarDetalleOrden(
        @Parameter(description="ID del detalle a actualizar",required=true)
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Detalle de orden a actualizar",
            required= true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DetalleOrden.class),
                examples = @ExampleObject(
                    name = "EjemploDetalleOrden",
                    value = "{\"orden\": {\"id\": 1}, \"producto\": {\"id\": 2}, \"cantidad\": 4, \"precio\": 2000}"
                )
            )
        ) 
        @RequestBody DetalleOrden detalleOrden
    ) {
        
        detalleOrden.setId(id);
        
        return detalleOrdenService.save(detalleOrden);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle de orden", description = "Elimina un detalle de orden por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Detalle de orden eliminado exitosamente"),
        @ApiResponse(responseCode = "404",description = "Detalle de orden no encontrado")
    })
    public void eliminarDetalleOrden(
        @Parameter(description="ID del detalle a borrar",required=true)
        @PathVariable Long id
    ) {
        detalleOrdenService.delete(id);
    }
}
