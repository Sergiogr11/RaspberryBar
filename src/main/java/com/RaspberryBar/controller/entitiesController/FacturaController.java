package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.FacturaService;
import com.RaspberryBar.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @RequestMapping(value="createFactura", method  = RequestMethod.POST)
    public String createFactura(@RequestBody Factura factura){
        return facturaService.createFactura(factura);
    }

    @RequestMapping(value="readFactura", method = RequestMethod.GET)
    public List<Factura> readFacturas(){
        return facturaService.readFacturas();
    }

    @RequestMapping(value="deleteFactura", method = RequestMethod.DELETE)
    public String deleteFactura(@RequestBody Factura factura){
        return facturaService.deleteFactura(factura);
    }
}
