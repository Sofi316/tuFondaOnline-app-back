package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ordenRepository.findById(id).get();
    }

    public Orden save(Orden orden){
        return ordenRepository.save(orden);
    }

    public void delete(Long id){
        ordenRepository.deleteById(id);
    }
}
