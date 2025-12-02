package com.example.tuFondaOnline.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tuFondaOnline.model.DetalleOrden;
import com.example.tuFondaOnline.repository.DetalleOrdenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleOrdenService {
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    
    public List<DetalleOrden> findAll(){
        return detalleOrdenRepository.findAll();
    }
    public List<DetalleOrden> findByOrdenId(Long idOrden) {
    return detalleOrdenRepository.findByOrdenId(idOrden);
}

    public DetalleOrden findById(Long id){
    return detalleOrdenRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado"));
    }
    public DetalleOrden save(DetalleOrden detalleOrden){
        return detalleOrdenRepository.save(detalleOrden);
    }

    public void delete(Long id){
        if (!detalleOrdenRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, ID no existe");
        }
        detalleOrdenRepository.deleteById(id);
    }
    public List<DetalleOrden> findByUsuarioEmail(String email) {
     return detalleOrdenRepository.findByOrden_Usuario_Email(email);
    }
}