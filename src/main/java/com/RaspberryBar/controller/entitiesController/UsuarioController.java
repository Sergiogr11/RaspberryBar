package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value="createUsuario", method  = RequestMethod.POST)
    public String createUsuario(@RequestBody Usuario usuario){
        return usuarioService.createUsuario(usuario);
    }

    @RequestMapping(value="readUsuarios", method = RequestMethod.GET)
    public List<Usuario> readUsuarios(){
        return usuarioService.readUsuarios();
    }

    @RequestMapping(value="updateUsuario", method = RequestMethod.PUT)
    public String updateUsuario(@RequestBody Usuario usuario){
        return usuarioService.updateUsuario(usuario);
    }

    @RequestMapping(value="deleteUsuario", method = RequestMethod.DELETE)
    public String deleteUsuario(@RequestBody Usuario usuario){
        return usuarioService.deleteUsuario(usuario);
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public boolean login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return usuarioService.authenticate(username, password);
    }

    @RequestMapping(value="findUsuario",method = RequestMethod.POST)
    public Usuario findUsuario(@RequestParam("username") String username){
        return usuarioService.findUsuario(username);
    }
}
