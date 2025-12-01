package com.example.tuFondaOnline.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tuFondaOnline.model.Comuna;
import com.example.tuFondaOnline.service.ComunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comunas")
@Tag(name = "Ubicación - Comunas", description = "Endpoints para listar comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    @Operation(summary = "Listar todas las comunas")
    public List<Comuna> listarTodas() {
        return comunaService.findAll();
    }

    @GetMapping("/{idRegion}")
    @Operation(summary = "Listar comunas por región", description = "Entrega solo las comunas que pertenecen a una región específica")
    public List<Comuna> listarPorRegion(@PathVariable Long idRegion) {
        return comunaService.findByRegion(idRegion);
    }
}