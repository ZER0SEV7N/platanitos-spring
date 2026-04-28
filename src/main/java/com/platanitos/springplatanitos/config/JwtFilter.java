package com.platanitos.springplatanitos.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platanitos.springplatanitos.services.jwtservices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private jwtservices jwtServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Obtener el token del header Authorization
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token (remover "Bearer ")
        final String token = authHeader.substring(7);
        
        // Validar el token
        if (!jwtServices.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el email del token
        final String email = jwtServices.extraerEmail(token);

        // Crear autenticación con el email
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                email, null, new ArrayList<>());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        filterChain.doFilter(request, response);
    }
}
