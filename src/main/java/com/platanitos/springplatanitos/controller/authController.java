package com.platanitos.springplatanitos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.platanitos.springplatanitos.models.usuario;
import com.platanitos.springplatanitos.models.payload.response;
import com.platanitos.springplatanitos.services.usuarioServices;
import com.platanitos.springplatanitos.services.jwtservices;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class authController {

    @Autowired
    private usuarioServices usuarioServices;

    @Autowired
    private jwtservices jwtServices;

    //Endpoint para registro de nuevo usuario
    @PostMapping("/registrar")
    public ResponseEntity<response<usuario>> registro(@RequestBody usuario nuevoUsuario) {
        usuario usuarioRegistrado = usuarioServices.Registrar(nuevoUsuario);

        if (usuarioRegistrado == null) 
            return ResponseEntity.status(400).body(new response<>(false, "El email ya está registrado", null));

        usuarioRegistrado.setContraseña(null);

        return ResponseEntity.status(201).body(new response<>(true, "Usuario registrado exitosamente", usuarioRegistrado));
    }

    //Endpoint para login de usuario
    @PostMapping("/login")
    public ResponseEntity<response<Map<String, Object>>> login(@RequestBody usuario credenciales) {
        usuario usuarioAutenticado = usuarioServices.IniciarSesion(credenciales.getEmail(), credenciales.getContraseña());

        if (usuarioAutenticado == null) 
            return ResponseEntity.status(401).body(new response<>(false, "Email o contraseña incorrectos", null));

        usuarioAutenticado.setContraseña(null); // Ocultar contraseña en la respuesta

        //Generar token JWT
        String token = jwtServices.generarToken(usuarioAutenticado.getEmail());
        
        //Preparar respuesta con token y datos del usuario
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("usuario", usuarioAutenticado);

        return ResponseEntity.status(200).body(new response<>(true, "Inicio de sesión exitoso", data));
    }
}
