package com.RaspberryBar.controller;

import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticuloController {
    @Autowired
    private ArticuloService articuloService;

    @RequestMapping(value="createArticulo", method  = RequestMethod.POST)
    public String createArticulo(@RequestBody Articulo articulo){
        return articuloService.createArticulo(articulo);
    }

    @RequestMapping(value="readArticulo", method = RequestMethod.GET)
    public List<Articulo> readArticulos(){
        return articuloService.readArticulos();
    }

    @RequestMapping(value="updateArticulo", method = RequestMethod.PUT)
    public String updateArticulo(@RequestBody Articulo articulo){
        return articuloService.updateArticulo(articulo);
    }

    @RequestMapping(value="deleteArticulo", method = RequestMethod.DELETE)
    public String deleteMesa(@RequestBody Articulo articulo){
        return articuloService.deleteArticulo(articulo);
    }
}
