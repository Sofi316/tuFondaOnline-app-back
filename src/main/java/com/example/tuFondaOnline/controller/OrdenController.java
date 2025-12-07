package com.example.tuFondaOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.tuFondaOnline.model.Orden;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.OrdenService;
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
@RequestMapping("/api/ordenes")
@Tag(name="Ordenes", description="Gestión de las boletas/pedidos de compra")
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
        summary = "Listar ordenes", 
        description = "Devuelve todas las órdenes"
    )
    public List<Orden> listarOrdenes() {
        return ordenService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar orden", description = "Obtiene una orden específica por su ID")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Encontrado"),
        @ApiResponse(responseCode="404", description = "No existe")
    })
    public Orden obtenerOrdenPorId(@PathVariable Long id) {
        return ordenService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear orden (Cabecera)", description = "Genera una nueva orden de compra")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Orden creada exitosamente",
            content = @Content(mediaType = "application/json", schema=@Schema(implementation=Orden.class)))
    })
    public Orden crearOrden(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la nueva orden", 
            required = true, 
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = Orden.class), 
                examples = @ExampleObject(
                    name = "Nueva Orden Pendiente", 
                    value = "{\n" +
                            "  \"usuario\": {\n" +
                            "    \"id\": 3\n" +
                            "  },\n" +
                            "  \"estado\": \"PENDIENTE\",\n" +
                            "  \"total\": 6500\n" +
                            "}"
                )
            )
        )  
        @RequestBody Orden orden) {
        
        if (orden.getUsuario() == null || orden.getUsuario().getId() == null) {
            throw new RuntimeException("Error: Debes enviar el ID del usuario (ej: { \"usuario\": { \"id\": 1 } })");
        }

        Usuario usuarioReal = usuarioService.findById(orden.getUsuario().getId());
        orden.setUsuario(usuarioReal);

        return ordenService.save(orden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado de orden", description = "Modifica una orden existente (Ej: cambiar estado a PAGADO)")
    public Orden actualizarOrden(
        @Parameter(description="ID de la orden", required=true) 
        @PathVariable Long id,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Orden.class),
                examples = @ExampleObject(
                    name = "Actualizar a Pagado", 
                    value = "{\n" +
                            "  \"usuario\": {\n" +
                            "    \"id\": 3\n" +
                            "  },\n" +
                            "  \"estado\": \"PAGADO\",\n" +
                            "  \"total\": 6500\n" +
                            "}"
                )
            )
        ) 
        @RequestBody Orden orden) {
        
        Orden ordenExistente = ordenService.findById(id);
        
        if (orden.getEstado() != null) {
            ordenExistente.setEstado(orden.getEstado());
        }
        
        return ordenService.save(ordenExistente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar orden", description = "Elimina una orden del sistema")
    public void eliminarOrden(@PathVariable Long id) {
        ordenService.delete(id);
    }
}
