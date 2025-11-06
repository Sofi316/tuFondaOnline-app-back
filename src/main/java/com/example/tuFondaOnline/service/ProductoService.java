package com.example.tuFondaOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tuFondaOnline.model.Producto;
import com.example.tuFondaOnline.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Long id){
        productoRepository.deleteById(id);
    }

}
