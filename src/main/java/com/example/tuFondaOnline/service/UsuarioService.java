package com.example.tuFondaOnline.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.repository.UsuarioRepository;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }


    public Usuario save(Usuario usuario){
        String passwordEncriptada= passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }
    public void deleteById(Long id){
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, ID no existe");
        }
        usuarioRepository.deleteById(id);
    }
    public Usuario findByEmail(String email) {
        
        return usuarioRepository.findByEmail(email).orElse(null); 
    }
    public Usuario update(Long id, Usuario usuarioConDatosNuevos) {
        Usuario usuarioAntiguo = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuarioAntiguo.setNombre(usuarioConDatosNuevos.getNombre());
        usuarioAntiguo.setEmail(usuarioConDatosNuevos.getEmail());
        usuarioAntiguo.setDireccion(usuarioConDatosNuevos.getDireccion());
        usuarioAntiguo.setRut(usuarioConDatosNuevos.getRut());

        if (usuarioConDatosNuevos.getRol() != null) {
            usuarioAntiguo.setRol(usuarioConDatosNuevos.getRol());
        }
        if (usuarioConDatosNuevos.getActivo() != null) {
            usuarioAntiguo.setActivo(usuarioConDatosNuevos.getActivo());
        }

        if (usuarioConDatosNuevos.getPassword() != null 
                && !usuarioConDatosNuevos.getPassword().isBlank()) {
            usuarioAntiguo.setPassword(passwordEncoder.encode(usuarioConDatosNuevos.getPassword()));
        }

        return usuarioRepository.save(usuarioAntiguo);
    }

}