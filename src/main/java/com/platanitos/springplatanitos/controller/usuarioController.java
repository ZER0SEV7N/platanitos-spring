package com.platanitos.springplatanitos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platanitos.springplatanitos.models.usuario;
import com.platanitos.springplatanitos.models.payload.response;
import com.platanitos.springplatanitos.services.usuarioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class usuarioController {

    @Autowired
    private usuarioServices usuarioServices;

    //Metodo para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<response<usuario>> registrarUsuario(@RequestBody usuario nuevoUsuario){
        usuario usuarioCreado = usuarioServices.Registrar(nuevoUsuario);

        if(usuarioCreado == null) 
            return ResponseEntity.status(400).body(new response<>(false, "Error al registrar el usuario", null));

        return ResponseEntity.status(201).body(new response<>(true, "Usuario registrado exitosamente", usuarioCreado));
    }

    //Metodo para iniciar sesion
    @PostMapping("/login")
    public ResponseEntity<response<usuario>> iniciarSesion(@RequestBody usuario usuarioLogin){
        usuario usuarioIniciado = usuarioServices.IniciarSesion(usuarioLogin.getEmail(), usuarioLogin.getContraseña());

        //No devolver la contraseña en la respuesta
        usuarioIniciado.setContraseña(null);
        
        if(usuarioIniciado == null) 
            return ResponseEntity.status(401).body(new response<>(false, "Credenciales invalidas", null));

        return ResponseEntity.status(200).body(new response<>(true, "Inicio de sesion exitoso", usuarioIniciado));
    }
}
