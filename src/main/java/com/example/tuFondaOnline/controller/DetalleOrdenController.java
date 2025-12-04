package com.example.tuFondaOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "Obtener todos los detalles de orden", description = "Obtiene una lista de todos los detalles. Si es cliente, filtra solo los propios.")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Operación exitosa"),
        @ApiResponse(responseCode = "404",description = "Detalle de orden no encontrado",
        content=@Content(mediaType = "application/json",
            schema = @Schema(implementation = DetalleOrden.class))
        )
    })
    public List<DetalleOrden> listarDetalleOrden(Authentication authentication) {
        
        boolean esJefe = authentication.getAuthorities().stream()
            .anyMatch(rol -> rol.getAuthority().equals("ADMINISTRADOR") || 
                             rol.getAuthority().equals("VENDEDOR"));

        if (esJefe) {
            return detalleOrdenService.findAll();
        } else {
            String email = authentication.getName();
            return detalleOrdenService.findByUsuarioEmail(email);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de orden por id", description = "Obtiene un detalle de orden por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode="404", description = "Detalle de orden no encontrado")
    })
    public DetalleOrden obtenerDetalleOrdenPorId(@Parameter(description="ID del detalle a buscar", required=true) @PathVariable Long id) {
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
        Authentication authentication,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Detalle de orden a crear",
        required= true,
        content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DetalleOrden.class),
        examples = @ExampleObject(
            name = "EjemploDetalleOrden",
            value = "{\"orden\": {\"id\": 1}, \"producto\": {\"id\": 2}, \"cantidad\": 3, \"precio\": 1500}")))
        @RequestBody DetalleOrden detalleOrden) {
        
        // 1. Buscamos la Orden Completa (Para validar y para rellenar el JSON)
        Orden ordenObjetivo = ordenService.findById(detalleOrden.getOrden().getId());
        detalleOrden.setOrden(ordenObjetivo); // Asignamos el objeto completo

        // 2. Seguridad: Verificar dueño
        boolean esJefe = authentication.getAuthorities().stream()
            .anyMatch(rol -> rol.getAuthority().equals("ADMINISTRADOR") || 
                             rol.getAuthority().equals("VENDEDOR"));

        if (!esJefe) {
            String emailUsuario = authentication.getName();
            if (ordenObjetivo != null && !ordenObjetivo.getUsuario().getEmail().equals(emailUsuario)) {
                throw new RuntimeException("Acceso denegado: No puedes agregar productos a una orden ajena.");
            }
        }

        // 3. Buscamos el Producto Completo 
        Producto productoReal = productoService.findById(detalleOrden.getProducto().getId());
        detalleOrden.setProducto(productoReal); // Asignamos el objeto completo

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
    public DetalleOrden actualizarDetalleOrden(@Parameter(description="ID del detalle a actualizar",required=true)
     @PathVariable Long id,
     @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Detalle de orden a actualizar",
        required= true,
        content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DetalleOrden.class),
        examples = @ExampleObject(
            name = "EjemploDetalleOrden",
            value = "{\"orden\": {\"id\": 1}, \"producto\": {\"id\": 2}, \"cantidad\": 4, \"precio\": 2000}"))) 
     @RequestBody DetalleOrden detalleOrden) {
        
        detalleOrden.setId(id);
        
        return detalleOrdenService.save(detalleOrden);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle de orden", description = "Elimina un detalle de orden por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Detalle de orden eliminado exitosamente"),
        @ApiResponse(responseCode = "404",description = "Detalle de orden no encontrado")
    })
    public void eliminarDetalleOrden(@Parameter(description="ID del detalle a borrar",required=true)
     @PathVariable Long id) {
        detalleOrdenService.delete(id);
    }
}