package com.RaspberryBar.controller;

import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.service.UsuarioService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value="info", method = RequestMethod.GET)
    public String info(){
        return "The app is up...";
    }

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
}
