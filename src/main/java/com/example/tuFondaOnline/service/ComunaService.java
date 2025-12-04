package com.example.tuFondaOnline.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tuFondaOnline.model.Comuna;
import com.example.tuFondaOnline.repository.ComunaRepository;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }


    public List<Comuna> findByRegion(Long idRegion) {
        return comunaRepository.findByRegion_Id(idRegion);
    }
}