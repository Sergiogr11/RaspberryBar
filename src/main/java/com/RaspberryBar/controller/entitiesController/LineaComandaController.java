package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaDTO;
import com.RaspberryBar.service.LineaComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LineaComandaController {

    @Autowired
    private LineaComandaService lineaComandaService;

    @RequestMapping(value="createLineaComanda", method  = RequestMethod.POST)
    public String createLineaComanda(@RequestBody LineaComanda lineaComanda){
        return lineaComandaService.createLineaComanda(lineaComanda);
    }

    @RequestMapping(value="readLineaComanda", method = RequestMethod.GET)
    public List<LineaComanda> readLineaComanda(){
        return lineaComandaService.readLineaComandas();
    }

    @RequestMapping(value="updateLineaComanda", method = RequestMethod.PUT)
    public String updateLineaComanda(@RequestBody LineaComanda lineaComanda){
        return lineaComandaService.updateLineaComanda(lineaComanda );
    }

    @RequestMapping(value="deleteLineaComanda", method = RequestMethod.DELETE)
    public String deleteLineaComanda(@RequestBody LineaComanda lineaComanda){
        return lineaComandaService.deleteLineaComanda(lineaComanda);
    }

    @RequestMapping(value="findLastLineaComanda", method  = RequestMethod.GET)
    public int findLastLineaComandabyComandaId(@RequestBody Integer numeroComanda){
        return lineaComandaService.findMaxId(numeroComanda);
    }

    @RequestMapping(value="findAllByNumeroComanda", method = RequestMethod.GET)
    public List<LineaComanda> findAllByNumeroComanda(@RequestBody Integer numeroComanda){
        return lineaComandaService.findAllByNumeroComanda(numeroComanda);
    }

    @RequestMapping(value="findAllWithNombreArticulo", method = RequestMethod.GET)
    public List<LineaComandaDTO> findAllWithNombreArticulo(@RequestBody Integer numeroComanda){
        return lineaComandaService.findAllWithNombreArticulo(numeroComanda);
    }


}
