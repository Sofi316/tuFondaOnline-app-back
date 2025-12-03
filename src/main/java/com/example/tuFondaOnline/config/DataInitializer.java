package com.example.tuFondaOnline.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.tuFondaOnline.model.Comuna;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.repository.ComunaRepository;
import com.example.tuFondaOnline.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ComunaRepository comunaRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
      
        Comuna santiago = comunaRepository.findByNombre("Santiago").orElse(null);

        if (santiago == null) {
            System.out.println(">>> ERROR CRÍTICO: data.sql NO CARGÓ. La base de datos está vacía.");
            return;
        }

        // Crear ADMIN
        if (usuarioRepository.findByEmail("admin@duoc.cl").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin Inicial");
            admin.setEmail("admin@duoc.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol("ADMINISTRADOR"); 
            admin.setRut("11.111.111-1");
            admin.setDireccion("Casa Central");
            admin.setComuna(santiago);
            usuarioRepository.save(admin);
        }

        // Crear CLIENTE
        if (usuarioRepository.findByEmail("cliente@duoc.cl").isEmpty()) {
            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Habitual");
            cliente.setEmail("cliente@duoc.cl");
            cliente.setPassword(passwordEncoder.encode("1234")); 
            cliente.setRol("CLIENTE"); 
            cliente.setRut("15.555.555-5");
            cliente.setDireccion("Av. Siempre Viva 742");
            cliente.setComuna(santiago);
            usuarioRepository.save(cliente);
        }
    }
}