package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String deleteArticulo(@RequestBody Articulo articulo){
        return articuloService.deleteArticulo(articulo);
    }

    @RequestMapping(value="findByCategoria/{categoriaId}", method = RequestMethod.POST)
    public List<String> findByCategoria(@PathVariable("categoriaId") int categoriaId) {
        return articuloService.findbyCategoria(categoriaId);
    }

    @RequestMapping(value="readNameArticulos", method = RequestMethod.GET)
    public List<String> readNameArticulos(){ return articuloService.readNameArticulos(); }

    @RequestMapping(value="findIdByNombreArticulo", method = RequestMethod.POST)
    public Integer findIdByNombreArticulo(@RequestBody String nombreArticulo){ return articuloService.findIdByNombreArticulo(nombreArticulo); }

    @RequestMapping(value="findNombreById", method = RequestMethod.POST)
    public String findNombreById(@RequestBody int idArticulo){ return articuloService.findNombrebyId(idArticulo); }

    @RequestMapping(value="findArticulosbyCategoria/{categoriaId}", method = RequestMethod.POST)
    public List<Articulo> findArticulosbyCategoria(@PathVariable("categoriaId") int categoriaId) {
        return articuloService.findArticulosbyCategoria(categoriaId);
    }

    @RequestMapping(value="findArticulo/{articuloId}", method = RequestMethod.GET)
    public Articulo findArticulo(@PathVariable("articuloId") int articuloId) {
        return articuloService.findArticulo(articuloId);
    }
}
