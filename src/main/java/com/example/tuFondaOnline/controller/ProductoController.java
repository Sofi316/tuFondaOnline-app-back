package com.example.tuFondaOnline.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.tuFondaOnline.model.Producto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tuFondaOnline.service.ProductoService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.findAll();
    }
    
    
}
