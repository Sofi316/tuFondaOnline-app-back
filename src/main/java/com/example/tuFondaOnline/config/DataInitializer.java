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

        System.out.println(">>> ¡ÉXITO! Base de datos cargada. Creando usuarios en comuna: " + santiago.getNombre());

        // Crear ADMIN
        if (usuarioRepository.findByEmail("admin@duoc.cl").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin Inicial");
            admin.setEmail("admin@duoc.cl");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol("ADMINISTRADOR");
            admin.setRut("11.111.111-1");
            admin.setActivo(true);
            admin.setDireccion("Casa Central");
            admin.setFechaRegistro(LocalDate.now());
            admin.setFechaNac(LocalDate.of(1990, 1, 1));
            admin.setComuna(santiago);
            usuarioRepository.save(admin);
        }

        // Crear VENDEDOR
        if (usuarioRepository.findByEmail("vendedor@duoc.cl").isEmpty()) {
            Usuario vendedor = new Usuario();
            vendedor.setNombre("Vendedor Inicial");
            vendedor.setEmail("vendedor@duoc.cl");
            vendedor.setPassword(passwordEncoder.encode("vendedor123"));
            vendedor.setRol("VENDEDOR");
            vendedor.setRut("22.222.222-2");
            vendedor.setActivo(true);
            vendedor.setDireccion("Sucursal Oeste");
            vendedor.setFechaRegistro(LocalDate.now());
            vendedor.setFechaNac(LocalDate.of(1995, 5, 20));
            vendedor.setComuna(santiago);
            usuarioRepository.save(vendedor);
        }

        if (usuarioRepository.findByEmail("cliente@duoc.cl").isEmpty()) {
            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Habitual");
            cliente.setEmail("cliente@duoc.cl");
            cliente.setPassword(passwordEncoder.encode("1234")); 
            cliente.setRol("CLIENTE");
            cliente.setRut("15.555.555-5");
            cliente.setActivo(true);
            cliente.setDireccion("Av. Siempre Viva 742");
            cliente.setFechaRegistro(LocalDate.now());
            cliente.setFechaNac(LocalDate.of(2000, 10, 10));
            cliente.setComuna(santiago);
            usuarioRepository.save(cliente);
            System.out.println(">>> CLIENTE CREADO: cliente@duoc.cl / 1234");
        }
    }
}