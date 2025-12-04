package com.example.tuFondaOnline.config;

import java.util.List; 

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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            
            
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:3000")); 
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                return configuration;
            }))
            // ---------------------------------------------

            .authorizeHttpRequests(auth -> auth
                // 1. ZONA PÚBLICA
             
                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()

                // 2. RUTAS GET PÚBLICAS (TIENDA)
                .requestMatchers(HttpMethod.GET,
                    "/api/productos",
                    "/api/productos/**",
                    "/api/categorias/**",
                    "/api/publicaciones/**",
                    "/api/regiones/**",
                    "/api/comunas/**"
                ).permitAll()

           
                .requestMatchers(HttpMethod.GET, 
                    "/api/ordenes/**", 
                    "/api/detalle_orden/**",
                    "/api/detalle_orden/orden/**"
                ).hasAnyAuthority("VENDEDOR", "ADMINISTRADOR", "CLIENTE")


               

                // 4. SOLO ADMIN (CRUD completo)
                .requestMatchers("/api/usuarios/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.POST,
                    "/api/productos/**",
                    "/api/categorias/**",
                    "/api/publicaciones/**"
                ).hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT,
                    "/api/productos/**",
                    "/api/ordenes/**",
                    "/api/detalle_orden/**",
                    "/api/categorias/**",
                    "/api/publicaciones/**"
                ).hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE,
                    "/api/productos/**",
                    "/api/ordenes/**",
                    "/api/detalle_orden/**",
                    "/api/categorias/**",
                    "/api/publicaciones/**"
                ).hasAuthority("ADMINISTRADOR")

                // 5. RESTO CERRADO
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