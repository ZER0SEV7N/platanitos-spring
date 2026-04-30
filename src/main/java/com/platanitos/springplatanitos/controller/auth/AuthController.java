package com.platanitos.springplatanitos.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.platanitos.springplatanitos.models.Usuario;
import com.platanitos.springplatanitos.models.payload.Response;
import com.platanitos.springplatanitos.services.auth.JwtServices;
import com.platanitos.springplatanitos.services.auth.UsuarioServices;
import java.util.HashMap;
import java.util.Map;

//Controlador para gestionar la autenticación y registro de usuarios
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private JwtServices jwtServices;

    //Endpoint para registro de nuevo usuario
    //Post: /api/auth/registrar
    @PostMapping("/registrar")
    public ResponseEntity<Response<Usuario>> registro(@RequestBody Usuario nuevoUsuario) {
        Usuario usuarioRegistrado = usuarioServices.Registrar(nuevoUsuario);

        if (usuarioRegistrado == null) 
            return ResponseEntity.status(400).body(new Response<>(false, "El email ya está registrado", null));

        usuarioRegistrado.setContraseña(null);

        return ResponseEntity.status(201).body(new Response<>(true, "Usuario registrado exitosamente", usuarioRegistrado));
    }

    //Endpoint para login de usuario
    //Post: /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<Response<Map<String, Object>>> login(@RequestBody Usuario credenciales) {
        Usuario usuarioAutenticado = usuarioServices.IniciarSesion(credenciales.getEmail(), credenciales.getContraseña());

        if (usuarioAutenticado == null) 
            return ResponseEntity.status(401).body(new Response<>(false, "Email o contraseña incorrectos", null));

        usuarioAutenticado.setContraseña(null); // Ocultar contraseña en la respuesta

        //Generar token JWT
        String token = jwtServices.generarToken(usuarioAutenticado.getEmail());
        
        //Preparar respuesta con token y datos del usuario
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("usuario", usuarioAutenticado);

        return ResponseEntity.status(200).body(new Response<>(true, "Inicio de sesión exitoso", data));
    }
}
