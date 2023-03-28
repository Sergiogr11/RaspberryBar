package com.RaspberryBar.service;

import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public String createUsuario(Usuario usuario){
        try {
            if (!usuarioRepository.existsByUsername(usuario.getUsername())){
                usuario.setUserId(null == usuarioRepository.findMaxId()? 0 : usuarioRepository.findMaxId() + 1);
                usuarioRepository.save(usuario);
                return "Usuario guardado correctamente.";
            }else {
                return "El usuario ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Usuario> readUsuarios(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public String updateUsuario(Usuario usuario){
        if (usuarioRepository.existsByUsername(usuario.getUsername())){
            try {
                List<Usuario> usuarios = usuarioRepository.findByUsername(usuario.getUsername());
                usuarios.stream().forEach(s -> {
                    Usuario usuarioToBeUpdate = usuarioRepository.findById(s.getUserId()).get();
                    usuarioToBeUpdate.setUsername(s.getUsername());
                    usuarioToBeUpdate.setCorreo(usuario.getCorreo());
                    usuarioToBeUpdate.setContraseña(usuario.getContraseña());
                    usuarioRepository.save(usuarioToBeUpdate);
                });
                return "Student record updated.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "Student does not exists in the database.";
        }
    }

    @Transactional
    public String deleteUsuario(Usuario usuario){
        if (usuarioRepository.existsByUsername(usuario.getUsername())){
            try {
                List<Usuario> usuarios = usuarioRepository.findByUsername(usuario.getUsername());
                usuarios.stream().forEach(s -> {
                    usuarioRepository.delete(s);
                });
                return "Usuario eliminado correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "El usuario no existe";
        }
    }
}
