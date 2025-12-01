package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tuFondaOnline.model.Orden;
import com.example.tuFondaOnline.repository.OrdenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {
    
    @Autowired
    private OrdenRepository ordenRepository;
    
    public List<Orden> findAll(){
        return ordenRepository.findAll();
    }

    public Orden findById(Long id){
        return ordenRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));
    }

    public Orden save(Orden orden){
        return ordenRepository.save(orden);
    }

    public void delete(Long id){
        if (!ordenRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, ID no existe");
        }
        ordenRepository.deleteById(id);
    }

   
    public List<Orden> findByUsuarioEmail(String email) {
        
        return ordenRepository.findByUsuario_Email(email);
    }
}