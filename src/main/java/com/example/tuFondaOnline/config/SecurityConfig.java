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
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*")); 
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(false); 
                return configuration;
            }))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
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
                ).hasAnyAuthority("ADMINISTRADOR", "CLIENTE")

                 .requestMatchers(HttpMethod.POST, 
                    "/api/ordenes/**", 
                    "/api/detalle_orden/**",
                    "/api/detalle_orden/orden/**"
                ).hasAnyAuthority("ADMINISTRADOR", "CLIENTE")

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

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .httpBasic(withDefaults());

        return http.build();
    }
}