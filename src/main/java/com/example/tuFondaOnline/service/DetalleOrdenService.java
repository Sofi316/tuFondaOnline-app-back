package com.example.tuFondaOnline.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public DetalleOrden findById(Long id){
        return detalleOrdenRepository.findById(id).get();
    }

    public DetalleOrden save(DetalleOrden detalleOrden){
        return detalleOrdenRepository.save(detalleOrden);
    }

    public void delete(Long id){
        detalleOrdenRepository.deleteById(id);
    }
}