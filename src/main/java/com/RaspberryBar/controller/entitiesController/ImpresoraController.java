package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.ImpresoraService;
import com.RaspberryBar.service.LineaComandaService;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.print.PrintService;
import java.io.IOException;
import java.util.List;

@RestController
public class ImpresoraController {


     //Obtengo las impresoras
     ImpresoraService impresoraService = ImpresoraService.getInstance();
     String impresoraBarra = impresoraService.getImpresoraBarra();
     String impresoraCocina = impresoraService.getImpresoraCocina();

     @Autowired
     LineaComandaService lineaComandaService;
     @Autowired
     ArticuloService articuloService;

     List<LineaComanda> lineaComandaList;

    //Conecto con LineaComandaController
    // Imprimo bebida en barra ()
    //Imprimo comida en cocina ()

    //LO QUE QUERIA HACER
    //Obtener lista lineas de comanda
    //Bucle for, if categoria de lineas de comanda es bebidas, cafes, vinos
    //Imprimir en impresora usb las lineas de comanda de dichas categorias
    //else Imprimir lo demás en cocina


    public String imprimirComandas(Comanda comanda) throws IOException {

        String impresoraBarra = impresoraService.getImpresoraBarra();
        String impresoraCocina = impresoraService.getImpresoraCocina();
        PrintService printServiceBar = PrinterOutputStream.getPrintServiceByName(impresoraBarra);
        PrinterOutputStream printerOutputStreamBar = new PrinterOutputStream(printServiceBar);
        EscPos escpos = new EscPos(printerOutputStreamBar);

        //Obtengo las lineas de comanda
        lineaComandaList = lineaComandaService.findAllByNumeroComanda(comanda.getNumeroComanda());

        escpos.writeLF("Mesa :" + comanda.getMesaId());
        escpos.feed(1);
        for (LineaComanda lineaComanda : lineaComandaList){
            String articuloName = articuloService.findNombrebyId(lineaComanda.getArticuloId());
            escpos.writeLF(lineaComanda.getCantidad() + "---------" + articuloName);
        }
        escpos.feed(1).cut(EscPos.CutMode.FULL);
        escpos.close();

        return "Comanda impresa correctamente";
    }

    @RequestMapping(value="imprimirBebidayComida", method  = RequestMethod.POST)
    public String imprimirBebidayComida(@RequestBody Comanda comanda) throws IOException {

        //TODO

        String impresoraBarra = impresoraService.getImpresoraBarra();
        String impresoraCocina = impresoraService.getImpresoraCocina();
        PrintService printServiceBar = PrinterOutputStream.getPrintServiceByName(impresoraBarra);
        PrinterOutputStream printerOutputStreamBar = new PrinterOutputStream(printServiceBar);
        EscPos escpos = new EscPos(printerOutputStreamBar);

        //Obtengo las lineas de comanda
        lineaComandaList = lineaComandaService.findAllByNumeroComanda(comanda.getNumeroComanda());

        escpos.writeLF("Mesa :" + comanda.getMesaId());
        escpos.feed(1);
        for (LineaComanda lineaComanda : lineaComandaList){
            String articuloName = articuloService.findNombrebyId(lineaComanda.getArticuloId());
            escpos.writeLF(lineaComanda.getCantidad() + "---------" + articuloName);
        }
        escpos.feed(1).cut(EscPos.CutMode.FULL);
        escpos.close();

        return "Comanda impresa correctamente";
    }


    public String imprimirBalanceVentas(List<Comanda> comandasList) throws IOException {
/*
        String impresoraBarra = impresoraService.getImpresoraBarra();
        String impresoraCocina = impresoraService.getImpresoraCocina();
        PrintService printServiceBar = PrinterOutputStream.getPrintServiceByName(impresoraBarra);
        PrinterOutputStream printerOutputStreamBar = new PrinterOutputStream(printServiceBar);
        EscPos escpos = new EscPos(printerOutputStreamBar);


        escpos.writeLF("Mesa :" + comanda.getMesaId());
        escpos.feed(1);
        for (LineaComanda lineaComanda : lineaComandaList){
            String articuloName = articuloService.findNombrebyId(lineaComanda.getArticuloId());
            escpos.writeLF(lineaComanda.getCantidad() + "---------" + articuloName);
        }
        escpos.feed(1).cut(EscPos.CutMode.FULL);
        escpos.close();


 */
        return "Comanda impresa correctamente";
    }


}
