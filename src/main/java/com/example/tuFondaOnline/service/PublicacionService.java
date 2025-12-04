package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return publicacionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicación no encontrada"));
    }
    //Eliminar publicación
    public void delete(Long id) {
        if (!publicacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, ID no existe");
        }
        publicacionRepository.deleteById(id);
    }
}