package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tuFondaOnline.model.Categoria;
import com.example.tuFondaOnline.model.Producto;
import com.example.tuFondaOnline.repository.CategoriaRepository;
import com.example.tuFondaOnline.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    // ----------------- LISTAR -----------------
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }


    // ----------------- BUSCAR POR ID -----------------
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Producto no encontrado con ID: " + id));
    }


    // ----------------- CREAR / ACTUALIZAR -----------------
    public Producto save(Producto producto) {

        if (producto.getCategoria() == null 
                || producto.getCategoria().getIdCategoria() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "La categoría es requerida");
        }

        Long categoriaId = producto.getCategoria().getIdCategoria();

        // Obtener categoría real desde BD
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, 
                        "La categoría con ID " + categoriaId + " no existe"));

        // Asignar categoría persistente
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }


    // ----------------- ELIMINAR -----------------
    public void deleteById(Long id) {

        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "No se puede eliminar, ID no existe");
        }

        productoRepository.deleteById(id);
    }

}
