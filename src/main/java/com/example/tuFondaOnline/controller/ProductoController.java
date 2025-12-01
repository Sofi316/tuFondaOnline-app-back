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
@Tag(name = "Productos", description = "Gestión del catálogo de productos de la fonda")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene el catálogo completo de productos")
    public List<Producto> listarProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Guarda un nuevo producto en la base de datos (Solo Admin)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)))
    })
    public Producto crearProducto(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo producto",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class),
                examples = @ExampleObject(
                    name = "Ejemplo Sopaipillas",
                    value = "{\n" +
                            "  \"nombre\": \"Sopaipillas Pasadas\",\n" +
                            "  \"descripcion\": \"Porción de 3 sopaipillas bañadas en deliciosa chancaca caliente con cáscara de naranja.\",\n" +
                            "  \"precio\": 2500,\n" +
                            "  \"stock\": 50,\n" +
                            "  \"img\": \"/imagenes/productos/sopaipillas.jpg\",\n" +
                            "  \"enOferta\": true,\n" +
                            "  \"precioOferta\": 2000,\n" +
                            "  \"categoria\": {\n" +
                            "    \"idCategoria\": 2\n" +
                            "  }\n" +
                            "}"
                )
            )
        )
        @RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente (Solo Admin)")
    public Producto actualizarProducto(
        @Parameter(description = "ID del producto a editar", required = true) @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class),
                examples = @ExampleObject(
                    name = "Ejemplo Editar Empanada",
                    value = "{\n" +
                            "  \"nombre\": \"Empanada de Pino (Horno)\",\n" +
                            "  \"descripcion\": \"Masa rellena de carne picada, cebolla, aceitunas, pasas y huevo duro. Receta de la abuela.\",\n" +
                            "  \"precio\": 2800,\n" +
                            "  \"stock\": 25,\n" +
                            "  \"img\": \"/imagenes/productos/empanada.jpg\",\n" +
                            "  \"enOferta\": false,\n" +
                            "  \"precioOferta\": null,\n" +
                            "  \"categoria\": {\n" +
                            "    \"idCategoria\": 1\n" +
                            "  }\n" +
                            "}"
                )
            )
        )
        @RequestBody Producto producto) {
        
  
        producto.setId(id);
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Borra un producto del sistema (Solo Admin)")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.deleteById(id);
    }
}