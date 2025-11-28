package com.example.tuFondaOnline.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            //1. Desactivar CSRF
            .csrf(csrf -> csrf.disable())

            //2. Definir reglas de acceso(rutas)
            .authorizeHttpRequests(auth-> auth
                //Rutas de autenticación y swagger públicas
                .requestMatchers("/auth/**", "/v3/api-docs/**","/swagger-ui/**", "/swagger-ui.html").permitAll()

                //Rutas públicas solo para leer
                .requestMatchers(HttpMethod.GET,
                    "/api/productos/**", 
                    "/api/categorias/**", 
                    "/api/regiones/**",
                    "/api/comunas/**",
                    "/api/blog/**"
                ).permitAll()

                // =========================================================
                // 2. ZONA ADMINISTRATIVA (VENDEDOR Y ADMIN)
                // =========================================================
                // Regla: "Vendedor puede visualizar lista y detalle de órdenes"
                // Aquí damos permiso de LECTURA (GET) a ambos roles.
                .requestMatchers(HttpMethod.GET, 
                    "/api/ordenes/**", 
                    "/api/detalles-orden/**"
                ).hasAnyAuthority("VENDEDOR", "ADMINISTRADOR")


                // =========================================================
                // 3. ZONA CRÍTICA (SOLO ADMIN)
                // =========================================================
                // Si alguien intenta CREAR, EDITAR o BORRAR Órdenes/Detalles, debe ser ADMIN.
                // El Vendedor será rechazado aquí (Error 403).
                .requestMatchers(HttpMethod.POST, "/api/ordenes/**", "/api/detalles-orden/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/ordenes/**", "/api/detalles-orden/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/ordenes/**", "/api/detalles-orden/**").hasAuthority("ADMINISTRADOR")
                
                // Gestión de Usuarios y Productos (Modificar) solo Admin
                .requestMatchers("/api/usuarios/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.POST, "/api/productos/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAuthority("ADMINISTRADOR")


                // =========================================================
                // 4. CLIENTES Y OTROS
                // =========================================================
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
    
}
