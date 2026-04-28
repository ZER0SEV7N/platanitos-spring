package com.platanitos.springplatanitos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class securityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    //Metodo para encriptar la contraseña del usuario
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Metodo para configurar la seguridad de la aplicación
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()  
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()  
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").authenticated()  
                        .requestMatchers(HttpMethod.POST, "/api/productos").authenticated()  
                        .requestMatchers(HttpMethod.PATCH, "/api/productos/**").authenticated() 
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);    
        return http.build();
    }
}
