package com.example.tuFondaOnline.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para gestionar usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    @ApiResponses(value= {
        @ApiResponse(responseCode= "200", description= "Operación exitosa",
            content= @Content(mediaType= "application/json",
                schema= @Schema(implementation= Usuario.class)))
    })
    public List<Usuario> getAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener cliente por ID", description = "Devuelve un cliente específico según su ID" )
    @ApiResponses(value= {
            @ApiResponse(responseCode= "200", description= "Operación exitosa",
                content= @Content(mediaType= "application/json",
                    schema= @Schema(implementation= Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")})
    public Usuario getById(@Parameter(description = "ID del usuario a obtener", required = true) 
                        @PathVariable Long id){
        return usuarioService.findById(id);
    }
    
    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value= {
        @ApiResponse(responseCode= "200", description= "Usuario creado exitosamente",
            content= @Content(mediaType= "application/json",
                schema= @Schema(implementation= Usuario.class)))
    })
    public Usuario create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description="Usuario a crear",
        required= true,
        content= @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples= @ExampleObject(
                name= "Ejemplo de usuario",
                value= "{\"nombre\": \"Camila Ramelli\", \"email\": \"camila.ramelli@duocuc.cl\", \"rut\": \"12.345.678-9\", \"direccion\": \"Calle Falsa 123\", \"rol\": \"CLIENTE\", \"password\": \"securePass123\", \"fechaNac\": \"1987-08-13\", \"comuna\": {\"id\": 3}, \"activo\": true}"    
                ))) @RequestBody Usuario usuario){
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value= {
        @ApiResponse(responseCode= "200", description= "Usuario actualizado exitosamente",
            content= @Content(mediaType= "application/json",
                schema= @Schema(implementation= Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Usuario update(@Parameter(description = "ID del usuario a actualizar", required = true) 
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description="Datos actualizados del usuario",
            required= true,
            content= @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class),
                examples= @ExampleObject(
                    name= "Ejemplo de usuario actualizado",
                    value= "{\"nombre\": \"Camila Ramelli\", \"email\": \"camila.ramelli@duocuc.cl\", \"rut\": \"12.345.678-9\", \"direccion\": \"Calle Falsa 123\", \"rol\": \"CLIENTE\", \"password\": \"securePass123\", \"fechaNac\": \"1987-08-13\", \"comuna\": {\"id\": 3}, \"activo\": true}"
                ))) @RequestBody Usuario usuario){
                    usuario.setId(id);
                    return usuarioService.save(usuario);
                }

    @DeleteMapping ("/{id}")    
     @Operation(summary = "Elimina un usuario", description = "Elimina un usuario por su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public void delete(@Parameter(description = "ID del usuario a borrar", required = true)
        @PathVariable Long id) {
        usuarioService.deleteById(id);
    }
}
