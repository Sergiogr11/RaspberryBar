package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value="createCategoria", method  = RequestMethod.POST)
    public String createCategoria(@RequestBody Categoria categoria){
        return categoriaService.createCategoria(categoria);
    }

    @RequestMapping(value="readCategoria", method = RequestMethod.GET)
    public List<Categoria> readCategoria(){
        return categoriaService.readCategoria();
    }

    @RequestMapping(value="updateCategoria", method = RequestMethod.PUT)
    public String updateCategoria(@RequestBody Categoria categoria){
        return categoriaService.updateCategoria(categoria);
    }

    @RequestMapping(value="deleteCategoria", method = RequestMethod.DELETE)
    public String deleteCategoria(@RequestBody Categoria categoria){
        return categoriaService.deleteCategoria(categoria);
    }
}
