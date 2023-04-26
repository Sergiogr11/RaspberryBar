package com.RaspberryBar.service;

import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import static com.sun.javafx.font.FontResource.SALT;

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
                    usuarioToBeUpdate.setUserId(s.getUserId());
                    usuarioToBeUpdate.setUsername(s.getUsername());
                    usuarioToBeUpdate.setCorreo(s.getCorreo());
                    usuarioToBeUpdate.setPassword(s.getPassword());
                    usuarioToBeUpdate.setRol(s.getRol());
                    usuarioToBeUpdate.setTelefono(s.getTelefono());
                    usuarioRepository.save(usuarioToBeUpdate);
                });
                return "Usuario actualizado correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "El usuario no existe.";
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

    public boolean authenticate(String username, String password){
        Usuario usuario = this.usuarioRepository.findByUsername(username).get(0);
        if(usuario == null){
            return false;
        }else{
            String encrypted_password = encrypt(password);
            if(encrypted_password.equals(usuario.getPassword())) return true;
            else return false;
        }
    }


    public static String encrypt(String password) {
        String SALT = System.getenv("PASSWORD_SALT"); // Lee el salt desde una variable de entorno

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Selecciona el algoritmo de hash que deseas utilizar (en este caso, SHA-256)
            String saltedPassword = password + SALT; // Agrega el salt a la contraseña
            byte[] hashedPassword = md.digest(saltedPassword.getBytes()); // Calcula el hash de la contraseña con el salt

            // Convierte el hash en una cadena de texto codificada en base64
            String encodedHash = Base64.getEncoder().encodeToString(hashedPassword);
            return encodedHash; // Retorna el hash en formato de cadena de texto
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
