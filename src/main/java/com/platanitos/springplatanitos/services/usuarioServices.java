package com.platanitos.springplatanitos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.repository.usuarioRepository;
import com.platanitos.springplatanitos.models.usuario;

@Service
public class usuarioServices {

    @Autowired
    private usuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    //Metodo para registrar un nuevo usuario
    public usuario Registrar(usuario nuevoUsuario){
        //Verificar si el email ya existe
        if(usuarioRepository.findByEmail(nuevoUsuario.getEmail()) != null)
            return null; 

        String hashContraseña = passwordEncoder.encode(nuevoUsuario.getContraseña());
        nuevoUsuario.setContraseña(hashContraseña);

        return usuarioRepository.save(nuevoUsuario);
    }

    //Metodo para iniciar sesion
    public usuario IniciarSesion(String email, String contraseña){

        //Verificar si el email existe y si la contraseña es correcta
        usuario usuarioExistente = usuarioRepository.findByEmail(email);
        
        if(usuarioExistente != null && passwordEncoder.matches(contraseña, usuarioExistente.getContraseña()))
            return usuarioExistente;
        
        return null; 
        
    }

}
