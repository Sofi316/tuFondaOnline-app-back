package com.example.tuFondaOnline.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.tuFondaOnline.model.Usuario;
import com.example.tuFondaOnline.repository.UsuarioRepository;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }


    public Usuario save(Usuario usuario){
        String passwordEncriptada= passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }
    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }
    public Usuario findByEmail(String email) {
        
        return usuarioRepository.findByEmail(email).orElse(null); 
    }
}
