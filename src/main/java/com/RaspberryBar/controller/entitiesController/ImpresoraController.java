package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Comanda;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImpresoraController {

    //Realizo la conexion con las impresoras en otro archivo
    //Conecto con LineaComandaController
    //Imprimo bebida en barra ()
    //Imprimo comida en cocina ()


    @RequestMapping(value="imprimirComandas", method  = RequestMethod.POST)
    public String imprimirComandas(@RequestBody Comanda comanda){
        //Obtener lista lineas de comanda
        //Bucle for, if categoria de lineas de comanda es bebidas, cafes, vinos
            //Imprimir en impresora usb las lineas de comanda de dichas categorias
        //else Imprimir lo demás en cocina
        return "Comanda impresa correctamente";
    }


}
