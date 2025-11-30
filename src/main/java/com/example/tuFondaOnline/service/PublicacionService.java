package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tuFondaOnline.model.Publicacion;
import com.example.tuFondaOnline.repository.PublicacionRepository;

@Service
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;
    //Crear o actualizar publicación
    public Publicacion save(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }
    //Obtener todas las publicaciones
    public List<Publicacion> findAll(){
        return publicacionRepository.findAll();
    }
    //Buscar una publicación con id
    public Publicacion findById(Long id) {
        return publicacionRepository.findById(id).orElse(null);
    }
    //Eliminar publicación
    public void delete(Long id) {
        publicacionRepository.deleteById(id);
    }
}

