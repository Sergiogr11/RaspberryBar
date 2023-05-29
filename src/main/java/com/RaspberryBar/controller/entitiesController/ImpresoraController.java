package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaDTO;
import com.RaspberryBar.service.*;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.output.PrinterOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.print.PrintService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
     MesaService mesaService;
     @Autowired
     ArticuloService articuloService;
     @Autowired
     UsuarioService usuarioService;

    private EscPos configurePrinter(String printerName) throws IOException {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(printerName);
        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
        return new EscPos(printerOutputStream);
    }

    public String imprimirComandas(Comanda comanda) throws IOException {

        String impresoraBarra = impresoraService.getImpresoraBarra();
        EscPos escpos = configurePrinter(impresoraBarra);

        //Obtengo las lineas de comanda
        //lineaComandaList = lineaComandaService.findAllByNumeroComanda(comanda.getNumeroComanda());

        escpos.writeLF("Mesa :" + comanda.getMesaId());
        escpos.feed(1);
        /*
        for (LineaComanda lineaComanda : lineaComandaList){
            String articuloName = articuloService.findNombrebyId(lineaComanda.getArticuloId());
            escpos.writeLF(lineaComanda.getCantidad() + "---------" + articuloName);
        }
        escpos.feed(1).cut(EscPos.CutMode.FULL);
        escpos.close();


         */
        return "Comanda impresa correctamente";
    }

    @RequestMapping(value="imprimirBebidayComida", method  = RequestMethod.POST)
    public String imprimirBebidayComida(@RequestBody Comanda comanda) throws IOException {
        int comandaId = comanda.getNumeroComanda();

        //Inicializo impresoras
        String impresoraBarra = impresoraService.getImpresoraBarra();
        EscPos escposBarra = configurePrinter(impresoraBarra);
        String impresoraCocina = impresoraService.getImpresoraCocina();
        EscPos escposCocina = configurePrinter(impresoraCocina);


        List<LineaComandaDTO> lineaComandaTotal = lineaComandaService.findAllWithNombreArticulo(comandaId);

        List<LineaComandaDTO> lineaComandaBebidas = lineaComandaService.findByComandaAndCategoriaNombre(comandaId, "Bebidas");
        List<LineaComandaDTO> lineaComandaVinos = lineaComandaService.findByComandaAndCategoriaNombre(comandaId, "Vinos");
        List<LineaComandaDTO> lineaComandaCafes = lineaComandaService.findByComandaAndCategoriaNombre(comandaId, "Cafés");

        // Creo la lista de bebidas combinando las bebidas, los vinos y los cafés
        List<LineaComandaDTO> lineaComandaBebida = new ArrayList<>();
        lineaComandaBebida.addAll(lineaComandaBebidas);
        lineaComandaBebida.addAll(lineaComandaVinos);
        lineaComandaBebida.addAll(lineaComandaCafes);

        // Creo la lista de comidas eliminando las bebidas de la lista total
        List<LineaComandaDTO> lineaComandaComida = new ArrayList<>(lineaComandaTotal);
        lineaComandaComida.removeAll(lineaComandaBebida);

        //Obtengo el nombre de la mesa
        String nombreMesa = mesaService.findMesa(comanda.getMesaId()).getNombreMesa();

        //Obtengo hora actual
        long fechaHoraCierreMillis = comanda.getFechaHoraApertura();
        //Creo un objeto date con la hora actual
        Date fecha = new Date(fechaHoraCierreMillis);
        // Formatear la horaCierre en un formato legible
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        // Formatear la fecha para mostrar solamente la hora en formato "hh:mm"
        String hora = formatoHora.format(fecha);

        //Obtengo el camarero asociado a la mesa
        String nombreCamarero = usuarioService.findUsuarioById(comanda.getUsuarioId()).getUsername();

        //Procedemos a imprimir en la impresora de cocina
        Style title = new Style()
                .setFontSize(Style.FontSize._3, Style.FontSize._3)
                .setJustification(EscPosConst.Justification.Center);

        Style subtitle = new Style()
                .setFontSize(Style.FontSize._2, Style.FontSize._2)
                .setFontName(Style.FontName.Font_B)
                .setJustification(EscPosConst.Justification.Center);

        escposCocina.feed(2);
        escposCocina.writeLF(title,"Mesa: " + nombreMesa);
        escposCocina.writeLF(subtitle, "Hora: " + hora);
        escposCocina.writeLF(subtitle, "Camarero: " + nombreCamarero);
        escposCocina.feed(2);

        for (LineaComandaDTO lineaComanda : lineaComandaComida) {
            escposCocina.writeLF(subtitle,lineaComanda.getLineaComanda().getCantidad() + "  " + lineaComanda.getNombreArticulo());
        }

        escposCocina.feed(4).cut(EscPos.CutMode.FULL);
        escposCocina.close();

        //Procedemos a imprimir en la impresora de barra

        escposBarra.feed(2);
        escposBarra.writeLF(title,"Mesa: " + nombreMesa);
        escposBarra.writeLF(subtitle, "Hora: " + hora);
        escposBarra.writeLF(subtitle, "Camarero: " + nombreCamarero);
        escposBarra.feed(2);

        for (LineaComandaDTO lineaComanda : lineaComandaBebida) {
            escposBarra.writeLF(subtitle,lineaComanda.getLineaComanda().getCantidad() + "  " + lineaComanda.getNombreArticulo());
        }

        escposBarra.feed(4).cut(EscPos.CutMode.FULL);
        escposBarra.close();

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


    public String imprimirFactura(Factura factura) throws IOException {
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

        System.out.println(factura);
        return "Comanda impresa correctamente";
    }


}
