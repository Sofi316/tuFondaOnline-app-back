package com.example.tuFondaOnline.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.tuFondaOnline.model.Comuna;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.repository.ComunaRepository;
import com.example.tuFondaOnline.repository.UsuarioRepository;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. BUSCAR UNA COMUNA EXISTENTE (Cargada por data.sql)
        // Buscamos la comuna con ID 1. 
        // Usamos .orElse(null) para que no falle la app si por error no se cargó el SQL.
        Comuna comunaInicial = comunaRepository.findById(1L).orElse(null);

        if (comunaInicial == null) {
            System.out.println(">>> ADVERTENCIA: No se encontró la Comuna ID 1. Los usuarios iniciales no se crearán.");
            return; // Salimos del método para evitar errores
        }

        // 2. CREAR ADMINISTRADOR (Si no existe)
        if (usuarioRepository.findByEmail("admin@duoc.cl").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin Inicial");
            admin.setEmail("admin@duoc.cl");
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña encriptada
            admin.setRol("ADMINISTRADOR");
            admin.setRun("11.111.111-1");
            admin.setActivo(true);
            admin.setDireccion("Casa Central Duoc");
            admin.setFechaRegistro(LocalDate.now());
            admin.setFechaNac(LocalDate.of(1990, 1, 1));
            
            // Asignamos la comuna que trajimos de la BD
            admin.setComuna(comunaInicial); 
            
            usuarioRepository.save(admin);
            System.out.println(">>> USUARIO CREADO: admin@duoc.cl (Pass: admin123)");
        }

        // 3. CREAR VENDEDOR (Si no existe)
        if (usuarioRepository.findByEmail("vendedor@duoc.cl").isEmpty()) {
            Usuario vendedor = new Usuario();
            vendedor.setNombre("Vendedor Inicial");
            vendedor.setEmail("vendedor@duoc.cl");
            vendedor.setPassword(passwordEncoder.encode("vendedor123")); // Contraseña encriptada
            vendedor.setRol("VENDEDOR");
            vendedor.setRun("22.222.222-2");
            vendedor.setActivo(true);
            vendedor.setDireccion("Sucursal Plaza Oeste");
            vendedor.setFechaRegistro(LocalDate.now());
            vendedor.setFechaNac(LocalDate.of(1995, 5, 20));
            
            // Asignamos la misma comuna (o podrías buscar la ID 2 si prefieres)
            vendedor.setComuna(comunaInicial);
            
            usuarioRepository.save(vendedor);
            System.out.println(">>> USUARIO CREADO: vendedor@duoc.cl (Pass: vendedor123)");
        }
    }
}