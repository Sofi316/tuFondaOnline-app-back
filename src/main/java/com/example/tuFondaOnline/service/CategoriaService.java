package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return categoriaRepository.findById(id).orElse(null);
    }
    //Eliminar categoria
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    } 
}
