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

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public List<Orden> listarOrdenes() {
        return ordenService.findAll();
    }

    @GetMapping("/{id}")
    public Orden obtenerOrdenPorId(@PathVariable Long id) {
        return ordenService.findById(id);
    }

    @PostMapping
    public Orden crearOrden(@RequestBody Orden orden) {
        return ordenService.save(orden);
    }

    @PutMapping("/{id}")
    public Orden actualizarOrden(@PathVariable Long id, @RequestBody Orden orden) {
        orden.setId(id);
        return ordenService.save(orden);
    }

    @DeleteMapping("/{id}")
    public void eliminarOrden(@PathVariable Long id) {
        ordenService.delete(id);
    }
}