package com.example.tuFondaOnline.config;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.tuFondaOnline.service.JwtService;

import jakarta.servlet.FilterChain; 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
            
            // 1. Para obtener el header de autorización
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            // 2. VALIDAR SI EL HEADER ES CORRECTO
        
            if(authHeader==null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }

            //3. Extraer el TOKEN (se quita la palabra "Bearer ")
            jwt= authHeader.substring(7);

            //4. Extraer el email del TOKEN
            userEmail=jwtService.extractUsername(jwt);

            //5. Validar y autenticar
            //Si hay un email y el usuario NO está autenticado todavía en el contexto
            if (userEmail !=null && SecurityContextHolder.getContext().getAuthentication()== null){
                
                //Busca usuario en la bbdd
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                
                //Verificamos si el token es válido para ese usuario
                if(jwtService.isTokenValid(jwt, userDetails)){

                    //Creamos una credencial oficial de spring
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //Le decimos a spring que el usuario es legítimo, que puede pasar
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

            //6. Continuar con el siguiente filtro
            filterChain.doFilter(request,response);




        }


}
