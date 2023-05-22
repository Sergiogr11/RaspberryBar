package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComandaController {
    @Autowired
    private ComandaService comandaService;

    @RequestMapping(value="createComanda", method  = RequestMethod.POST)
    public String createComandda(@RequestBody Comanda comanda){
        return comandaService.createComanda(comanda);
    }

    @RequestMapping(value="readComanda", method = RequestMethod.GET)
    public List<Comanda> readComanda(){
        return comandaService.readComandas();
    }

    @RequestMapping(value="updateComanda", method = RequestMethod.PUT)
    public String updateComanda(@RequestBody Comanda comanda){
        return comandaService.updateComanda(comanda);
    }

    @RequestMapping(value="deleteComanda", method = RequestMethod.DELETE)
    public String deleteComanda(@RequestBody Comanda comanda){
        return comandaService.deleteComanda(comanda);
    }

    @RequestMapping(value="findLastComandaByMesa", method = RequestMethod.DELETE)
    public Comanda findLastComandaByMesa(@RequestBody int mesaId){
        return comandaService.findLastComandaByMesa(mesaId);
    }

    @RequestMapping(value="findMaxIdComanda", method = RequestMethod.GET)
    public int findMaxIdComanda(){
        return comandaService.findMaxId();
    }
}
