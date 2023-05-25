package com.RaspberryBar.entities;

public class LineaComandaDTO {
    private LineaComanda lineaComanda;
    private String nombreArticulo;

    public LineaComandaDTO(LineaComanda lineaComanda, String nombreArticulo) {
        this.lineaComanda = lineaComanda;
        this.nombreArticulo = nombreArticulo;
    }

    public LineaComanda getLineaComanda() {
        return lineaComanda;
    }

    public void setLineaComanda(LineaComanda lineaComanda) {
        this.lineaComanda = lineaComanda;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }
}
