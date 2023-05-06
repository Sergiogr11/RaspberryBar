package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MesaController {
    @Autowired
    private MesaService mesaService;

    @RequestMapping(value="createMesa", method  = RequestMethod.POST)
    public String createMesa(@RequestBody Mesa mesa){
        return mesaService.createMesa(mesa);
    }

    @RequestMapping(value="readMesas", method = RequestMethod.GET)
    public List<Mesa> readMesas(){
        return mesaService.readMesas();
    }

    @RequestMapping(value="updateMesa", method = RequestMethod.PUT)
    public String updateMesa(@RequestBody Mesa mesa){
        return mesaService.updateMesa(mesa);
    }

    @RequestMapping(value="deleteMesa", method = RequestMethod.DELETE)
    public String deleteMesa(@RequestBody Mesa mesa){
        return mesaService.deleteMesa(mesa);
    }

    @RequestMapping(value="findMesasByPosicion", method = RequestMethod.POST)
    public List<Mesa> findMesaByPosicion(@RequestBody String posicion){
        return mesaService.findMesaByPosicion(posicion);
    }

}
