package com.example.tuFondaOnline.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails { 
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long id;

    @Column(nullable = false, length=13, unique=true)
    private String rut;

    @Column(nullable = false, length=40)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String rol; 

    @Column(nullable = false, length=120)
    private String direccion;


    @Column(nullable = false, length=100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_comuna") 
    private Comuna comuna;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities(){
        // Convierte tu String "rol" en un objeto que Spring entienda
        return List.of(new SimpleGrantedAuthority(rol));    
    }

    @Override
    public String getUsername(){
        return email; 
    }

    @Override @JsonIgnore public boolean isAccountNonExpired() { return true; }
    @Override @JsonIgnore public boolean isAccountNonLocked() { return true; }
    @Override @JsonIgnore public boolean isCredentialsNonExpired() { return true; }
    @Override @JsonIgnore public boolean isEnabled() { return true; } 
}