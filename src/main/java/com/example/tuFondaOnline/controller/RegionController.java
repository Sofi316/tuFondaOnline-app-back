package com.example.tuFondaOnline.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tuFondaOnline.model.Region;
import com.example.tuFondaOnline.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/regiones")
@Tag(name = "Ubicación - Regiones", description = "Endpoints para listar regiones de Chile")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    @Operation(summary = "Listar todas las regiones")
    public List<Region> listarRegiones() {
        return regionService.findAll();
    }
}