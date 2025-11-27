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

import com.example.tuFondaOnline.model.Producto;
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
@RequestMapping("/api/productos")
@Tag(name="Productos",description="Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Operación exitosa"),
        @ApiResponse(responseCode = "404",description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)))
    })
    public List<Producto> listarProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por id", description = "Obtiene un producto por su id")
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode="404", description = "Producto no encontrado")
    })
    public Producto obtenerProductoPorId(@Parameter(description = "ID del producto a buscar",required = true) @PathVariable Long id) {
        return productoService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear un producto", description = "Crea un producto nuevo")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema=@Schema(implementation=Producto.class)
            )
        )
    })
    public Producto crearProducto(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Producto a crear", 
            required = true, 
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = Producto.class), 
                examples = @ExampleObject(
                    name = "EjemploProducto", 
                    value = "{\"nombre\": \"Charquican\", \"descripcion\": \"Delicioso charquicán Chileno\", \"precio\":3500, \"stock\": 5,\"img\": \"public/assets/foto.jpg\", \"enOferta\": false ,\"precioOferta\":0,\"categoria\": {\"id\": 1}}"
                )
            )
        )@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.delete(id);
    }
}
