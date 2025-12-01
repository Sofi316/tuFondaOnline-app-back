package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tuFondaOnline.model.Categoria;
import com.example.tuFondaOnline.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    //Crear o actualizar categoria
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    //Obtener todas las categorias
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    //Buscar categoria por id
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }
    //Eliminar categoria
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, ID no existe");
        }
        categoriaRepository.deleteById(id);
    } 
}
