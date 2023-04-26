package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.entities.Restaurante;
import com.RaspberryBar.service.MesaService;
import com.RaspberryBar.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestauranteController {
    @Autowired
    private RestauranteService restauranteService;

    @RequestMapping(value="createRestaurante", method  = RequestMethod.POST)
    public String createRestaurante(@RequestBody Restaurante restaurante){
        return restauranteService.createRestaurante(restaurante);
    }

    @RequestMapping(value="readRestaurantes", method = RequestMethod.GET)
    public List<Restaurante> readRestaurantes(){
        return restauranteService.readRestaurante();
    }

    @RequestMapping(value="updateRestaurante", method = RequestMethod.PUT)
    public String updateRestaurante(@RequestBody Restaurante restaurante){
        return restauranteService.updateRestaurante(restaurante);
    }

    @RequestMapping(value="deleteRestaurante", method = RequestMethod.DELETE)
    public String deleteRestaurante(@RequestBody Restaurante restaurante){
        return restauranteService.deleteRestaurante(restaurante);
    }
}
