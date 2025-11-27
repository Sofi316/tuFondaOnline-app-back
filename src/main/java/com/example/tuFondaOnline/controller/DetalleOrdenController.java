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

import com.example.tuFondaOnline.model.DetalleOrden;
import com.example.tuFondaOnline.service.DetalleOrdenService;

@RestController
@RequestMapping("/api/detalle_orden")
public class DetalleOrdenController {
    
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @GetMapping
    public List<DetalleOrden> listarDetalleOrden() {
        return detalleOrdenService.findAll();
    }

    @GetMapping("/{id}")
    public DetalleOrden obtenerDetalleOrdenPorId(@PathVariable Long id) {
        return detalleOrdenService.findById(id);
    }

    @PostMapping
    public DetalleOrden crearDetalleOrden(@RequestBody DetalleOrden detalleOrden) {
        return detalleOrdenService.save(detalleOrden);
    }

    @PutMapping("/{id}")
    public DetalleOrden actualizarDetalleOrden(@PathVariable Long id, @RequestBody DetalleOrden detalleOrden) {
        detalleOrden.setId(id);
        return detalleOrdenService.save(detalleOrden);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalleOrden(@PathVariable Long id) {
        detalleOrdenService.delete(id);
    }
}