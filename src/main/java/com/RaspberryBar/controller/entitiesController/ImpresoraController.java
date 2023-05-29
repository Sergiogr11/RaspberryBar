package com.RaspberryBar.controller.entitiesController;

import com.RaspberryBar.entities.*;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
     UsuarioService usuarioService;
     @Autowired
     RestauranteService restauranteService;
     @Autowired
     ComandaService comandaService;

    private EscPos configurePrinter(String printerName) throws IOException {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(printerName);
        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
        return new EscPos(printerOutputStream);
    }


    public String imprimirComandas(Comanda comanda) throws IOException {

        int comandaId = comanda.getNumeroComanda();

        //Inicializo impresora
        String impresoraBarra = impresoraService.getImpresoraBarra();
        EscPos escposBarra = configurePrinter(impresoraBarra);

        //Obtengo info del restaurante
        Restaurante restaurante = restauranteService.readRestaurante().get(0);
        String nombreRestaurante = restaurante.getNombre();
        String direccionRestaurante = restaurante.getDireccion();
        String cifRestaurante = restaurante.getCif();
        String telefonoRestaurante = restaurante.getTelefono();

        //Obtengo el nombre de la mesa
        String nombreMesa = mesaService.findMesa(comanda.getMesaId()).getNombreMesa();

        //Obtengo el camarero asociado a la mesa
        String nombreCamarero = usuarioService.findUsuarioById(comanda.getUsuarioId()).getUsername();

        //Obtengo la fechaApertura y fechaCierre de la comanda
        long fechaApertura = comanda.getFechaHoraApertura();
        long fechaCierre = comanda.getFechaHoraCierre();
        //Creo un objeto date con las horas
        Date fechaAp = new Date(fechaApertura);
        Date fechaCi = new Date(fechaApertura);
        // Formatear la horaCierre en un formato legible
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        // Formatear la fecha para mostrar solamente la hora en formato "hh:mm"
        String horaApertura = formatoHora.format(fechaAp);
        String horaCierre = formatoHora.format(fechaCi);

        //Obtengo el numero de comanda
        String numeroTicket = String.valueOf(comanda.getNumeroComanda());

        //Obtengo el total de la comanda
        double precioTotal = comanda.getPrecioTotal();

        List<LineaComandaDTO> lineaComandaTotal = lineaComandaService.findAllWithNombreArticulo(comandaId);


        String indicativo = "               Cant.    Importe";
        String precioTotalFormateado = String.format("%.2f", precioTotal);

        //Procedemos a imprimir el ticket
        //Procedemos a imprimir en la impresora de cocina
        Style title = new Style()
                .setFontName(Style.FontName.Font_B)
                .setFontSize(Style.FontSize._2, Style.FontSize._2)
                .setJustification(EscPosConst.Justification.Center);

        Style subtitle = new Style()
                .setFontSize(Style.FontSize._1, Style.FontSize._1)
                .setFontName(Style.FontName.Font_B)
                .setJustification(EscPosConst.Justification.Center);

        Style linea = new Style()
                .setJustification(EscPosConst.Justification.Center)
                .setUnderline(Style.Underline.TwoDotThick);

        Style left = new Style()
                .setJustification(EscPosConst.Justification.Left_Default);
        Style right = new Style()
                .setJustification(EscPosConst.Justification.Right);
        Style rightLine = new Style()
                .setUnderline(Style.Underline.TwoDotThick)
                .setJustification(EscPosConst.Justification.Right);
        Style center = new Style()
                .setJustification(EscPosConst.Justification.Center);
        Style fontB = new Style()
                .setJustification(EscPosConst.Justification.Center)
                .setFontName(Style.FontName.Font_B);

        escposBarra.feed(2)
                .writeLF(title,nombreRestaurante)
                .feed(1)
                .writeLF(direccionRestaurante)
                .write(left,"CIF: " + cifRestaurante + "   ")
                .writeLF(right,"Telf: " + telefonoRestaurante)
                .writeLF(linea, "            ")
                .feed(1)
                .writeLF(center, "Hora Apertura: " + horaApertura)
                .writeLF(center, "Hora Cierre: " + horaCierre)
                .writeLF(center, "Mesa: " + nombreMesa)
                .writeLF(center, "Camarero: " + nombreCamarero)
                .writeLF(center, "Ticket nº: " + numeroTicket)
                .writeLF(linea, "            ")
                .feed(1)
                .writeLF(center, indicativo);

        for (LineaComandaDTO lineaComanda : lineaComandaTotal) {
            //escposBarra.writeLF(center, lineaComanda.getLineaComanda().getCantidad() + " " + lineaComanda.getNombreArticulo() + " " + lineaComanda.getLineaComanda().getPrecio());
            String nombreArticulo = lineaComanda.getNombreArticulo();
            int cantidad = lineaComanda.getLineaComanda().getCantidad();
            double importe = lineaComanda.getLineaComanda().getPrecio();

            String lineaArticulo = String.format(Locale.getDefault(), "%-15s%5d%10.2f", nombreArticulo, cantidad, importe);
            escposBarra.writeLF(center, lineaArticulo);
        }

        escposBarra.writeLF(rightLine,  "         ")
        .writeLF("Total (IVA Incl.):        " + precioTotalFormateado)
        .feed(1)
        .writeLF(fontB, "GRACIAS POR SU VISITA")
        .feed(4)
        .cut(EscPos.CutMode.FULL)
        .close();

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
        //Numero comandas
        String totalComandas = String.valueOf(comandasList.size());

        //Caja total
        double precioTotal = 0.0;
        //Declaro fechas
        LocalDateTime fechaHoraAperturaMin = null;
        LocalDateTime fechaHoraCierreMax = null;

        for (Comanda comanda : comandasList) {
            precioTotal += comanda.getPrecioTotal();
            long fechaHoraAperturaMillis = comanda.getFechaHoraApertura();
            long fechaHoraCierreMillis = comanda.getFechaHoraCierre();

            LocalDateTime fechaHoraApertura = LocalDateTime.ofInstant(Instant.ofEpochMilli(fechaHoraAperturaMillis), ZoneId.systemDefault());
            LocalDateTime fechaHoraCierre = LocalDateTime.ofInstant(Instant.ofEpochMilli(fechaHoraCierreMillis), ZoneId.systemDefault());

            if (fechaHoraAperturaMin == null || fechaHoraApertura.isBefore(fechaHoraAperturaMin)) {
                fechaHoraAperturaMin = fechaHoraApertura;
            }

            if (fechaHoraCierreMax == null || fechaHoraCierre.isAfter(fechaHoraCierreMax)) {
                fechaHoraCierreMax = fechaHoraCierre;
            }
        }

        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaHoraApertura = fechaHoraAperturaMin.format(formatoFechaHora);
        String fechaHoraCierre = fechaHoraCierreMax.format(formatoFechaHora);

        String precioTotalFormated = String.format("%.2f", precioTotal);

        //Inicializo impresora
        String impresoraBarra = impresoraService.getImpresoraBarra();
        EscPos escposBarra = configurePrinter(impresoraBarra);

        Style title = new Style()
                .setFontName(Style.FontName.Font_C)
                .setFontSize(Style.FontSize._3, Style.FontSize._3)
                .setJustification(EscPosConst.Justification.Center);

        Style subtitle = new Style()
                .setFontName(Style.FontName.Font_C)
                .setFontSize(Style.FontSize._2, Style.FontSize._2)
                .setJustification(EscPosConst.Justification.Center);

        Style info = new Style()
                .setFontName(Style.FontName.Font_C)
                .setFontSize(Style.FontSize._1, Style.FontSize._1)
                .setJustification(EscPosConst.Justification.Center);


        escposBarra.feed(1)
                .writeLF(title, "Balance de Ventas")
                .feed(1)
                .writeLF(info, "Fecha Apertura:")
                .writeLF(subtitle, fechaHoraApertura)
                .writeLF(info, "Fecha Cierre:")
                .writeLF(subtitle, fechaHoraCierre)
                .writeLF(info, "Numero Comandas: ")
                .writeLF(subtitle, totalComandas)
                .writeLF(info, "Caja Total: ")
                .writeLF(subtitle, precioTotalFormated)
                .feed(4)
                .cut(EscPos.CutMode.FULL)
                .close();

        return "Balance ventas impreso correctamente";
    }


    public String imprimirFactura(Factura factura) throws IOException {

        int comandaId = factura.getComandaId();

        //Inicializo impresora
        String impresoraBarra = impresoraService.getImpresoraBarra();
        EscPos escposBarra = configurePrinter(impresoraBarra);

        //Obtengo info del restaurante
        Restaurante restaurante = restauranteService.readRestaurante().get(0);
        String nombreRestaurante = restaurante.getNombre();
        String direccionRestaurante = restaurante.getDireccion();
        String cifRestaurante = restaurante.getCif();
        String telefonoRestaurante = restaurante.getTelefono();

        //Obtengo la comanda
        Comanda comanda = comandaService.findComandaById(comandaId).get();

        //Obtengo el nombre de la mesa
        String nombreMesa = mesaService.findMesa(comanda.getMesaId()).getNombreMesa();

        //Obtengo el camarero asociado a la mesa
        String nombreCamarero = usuarioService.findUsuarioById(comanda.getUsuarioId()).getUsername();

        //Obtengo la fechaApertura y fechaCierre de la comanda
        long fechaApertura = comanda.getFechaHoraApertura();
        long fechaCierre = comanda.getFechaHoraCierre();
        //Creo un objeto date con las horas
        Date fechaAp = new Date(fechaApertura);
        Date fechaCi = new Date(fechaCierre);
        // Formatear la horaCierre en un formato legible
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        // Formatear la fecha para mostrar solamente la hora en formato "hh:mm"
        String horaApertura = formatoHora.format(fechaAp);
        String horaCierre = formatoHora.format(fechaCi);

        //Obtengo el numero de comanda
        String numeroTicket = String.valueOf(comanda.getNumeroComanda());

        //Obtengo el total de la comanda
        double precioTotal = comanda.getPrecioTotal();

        //Obtengo la base imponible
        double iva = Double.parseDouble(restaurante.getTipoImpositivo()) / 100.0;
        double baseImponible = precioTotal - precioTotal * iva;
        String baseImponibleFormateada = String.format("%.2f", baseImponible);

        //Obtengo el nombre y dni del receptor
        String dniReceptor = factura.getNombreReceptor();
        String nombreReceptor = factura.getDniReceptor();

        //Obtengo la fecha de Emision
        long fechaEmisionLong = factura.getFechaEmision();
        // Creo un objeto Date con la fecha de emisión
        Date fechaEm = new Date(fechaEmisionLong);
        // Formatear la fecha en un formato legible
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEmision = formatoFecha.format(fechaEm);


        List<LineaComandaDTO> lineaComandaTotal = lineaComandaService.findAllWithNombreArticulo(comandaId);


        String indicativo = "               Cant.    Importe";
        String precioTotalFormateado = String.format("%.2f", precioTotal);

        //Procedemos a imprimir el ticket
        //Procedemos a imprimir en la impresora de cocina
        Style title = new Style()
                .setFontName(Style.FontName.Font_B)
                .setFontSize(Style.FontSize._2, Style.FontSize._2)
                .setJustification(EscPosConst.Justification.Center);

        Style subtitle = new Style()
                .setFontSize(Style.FontSize._1, Style.FontSize._1)
                .setFontName(Style.FontName.Font_B)
                .setJustification(EscPosConst.Justification.Center);

        Style linea = new Style()
                .setJustification(EscPosConst.Justification.Center)
                .setUnderline(Style.Underline.TwoDotThick);

        Style left = new Style()
                .setJustification(EscPosConst.Justification.Left_Default);
        Style right = new Style()
                .setJustification(EscPosConst.Justification.Right);
        Style rightLine = new Style()
                .setUnderline(Style.Underline.TwoDotThick)
                .setJustification(EscPosConst.Justification.Right);
        Style center = new Style()
                .setJustification(EscPosConst.Justification.Center);
        Style fontB = new Style()
                .setJustification(EscPosConst.Justification.Center)
                .setFontName(Style.FontName.Font_B);

        escposBarra.feed(2)
                .writeLF(title,nombreRestaurante)
                .feed(1)
                .writeLF(direccionRestaurante)
                .write(left,"CIF: " + cifRestaurante + "   ")
                .writeLF(right,"Telf: " + telefonoRestaurante)
                .writeLF(linea, "            ")
                .feed(1)
                .writeLF(center, "Nombre Receptor: " + nombreReceptor)
                .writeLF(center, "DNI/CIF Receptor: " + dniReceptor)
                .writeLF(center, "Fecha Emisión: " + fechaEmision)
                .writeLF(linea, "            ")
                .feed(1)
                .writeLF(center, "Hora Apertura: " + horaApertura)
                .writeLF(center, "Hora Cierre: " + horaCierre)
                .writeLF(center, "Mesa: " + nombreMesa)
                .writeLF(center, "Camarero: " + nombreCamarero)
                .writeLF(center, "Ticket nº: " + numeroTicket)
                .writeLF(linea, "            ")
                .feed(1)
                .writeLF(center, indicativo);

        for (LineaComandaDTO lineaComanda : lineaComandaTotal) {
            //escposBarra.writeLF(center, lineaComanda.getLineaComanda().getCantidad() + " " + lineaComanda.getNombreArticulo() + " " + lineaComanda.getLineaComanda().getPrecio());
            String nombreArticulo = lineaComanda.getNombreArticulo();
            int cantidad = lineaComanda.getLineaComanda().getCantidad();
            double importe = lineaComanda.getLineaComanda().getPrecio();

            String lineaArticulo = String.format(Locale.getDefault(), "%-15s%5d%10.2f", nombreArticulo, cantidad, importe);
            escposBarra.writeLF(center, lineaArticulo);
        }

        escposBarra.writeLF(rightLine,  "         ")
                .writeLF("Base Imponible :          " + baseImponibleFormateada)
                .writeLF("Total (IVA Incl.):        " + precioTotalFormateado)
                .feed(1)
                .writeLF(fontB, "GRACIAS POR SU VISITA")
                .feed(4)
                .cut(EscPos.CutMode.FULL)
                .close();

        return "Factura impresa correctamente";
    }

}
