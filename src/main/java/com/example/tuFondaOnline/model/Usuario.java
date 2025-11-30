package com.example.tuFondaOnline.model;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    private Boolean activo;

    @Column(nullable=false, length=120)
    private String direccion;

    @Column(name = "fecha_registro",updatable = false)
    private LocalDate fechaRegistro;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    @Column(nullable = false, length=100)
    private String password;


// Métodos UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(rol));    
    }
    @Override
    public String getUsername(){
        return email;
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return activo;
    }

    @PrePersist
    public void asignarFechaRegistro() {
    
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
    }
    @ManyToOne
    @JoinColumn(name = "id_comuna") 
    private Comuna comuna;
}
