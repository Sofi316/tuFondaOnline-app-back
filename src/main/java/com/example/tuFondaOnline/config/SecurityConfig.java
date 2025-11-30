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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
            .authorizeHttpRequests(auth -> auth
                // 1. ZONA PÚBLICA (USANDO ANT-MATCHER BLINDADO)
                // Usamos "new AntPathRequestMatcher" para evitar confusiones de Spring
                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()

                // 2. RUTAS GET PÚBLICAS
                .requestMatchers(HttpMethod.GET, "/api/productos/**", "/api/categorias/**", "/api/regiones/**", "/api/comunas/**", "/api/blog/**").permitAll()

                // 3. VENDEDOR Y ADMIN
                .requestMatchers(HttpMethod.GET, "/api/ordenes/**", "/api/detalles-orden/**").hasAnyAuthority("VENDEDOR", "ADMINISTRADOR")

                // 4. SOLO ADMIN (Modificaciones críticas)
                .requestMatchers("/api/usuarios/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.POST, "/api/productos/**", "/api/ordenes/**", "/api/detalles-orden/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/productos/**", "/api/ordenes/**", "/api/detalles-orden/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**", "/api/ordenes/**", "/api/detalles-orden/**").hasAuthority("ADMINISTRADOR")

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