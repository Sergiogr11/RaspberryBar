package com.RaspberryBar.entities;

import javax.persistence.*;

@Entity
public class LineaComanda{

    @EmbeddedId
    private LineaComandaId lineaComandaId;

    private int cantidad;
    private float precio;
    private int articuloId;

    /**
     * Constructor para Linea backend.entities.Comanda
     * @param numeroLinea numero de Linea de la comanda
     * @param numeroComanda numero de comanda asociado
     * @param cantidad cantidad del artículo
     * @param precio precio del artículo
     * @param articuloId artículo asociado a la linea de la comanda
     */
    public LineaComanda(LineaComandaId lineaComandaId, int cantidad, float precio, int articuloId) {
        this.lineaComandaId = lineaComandaId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.articuloId = articuloId;
    }

    public LineaComanda(){}

    public LineaComandaId getLineaComandaId() {
        return lineaComandaId;
    }

    public void setLineaComandaId(LineaComandaId lineaComandaId) {
        this.lineaComandaId = lineaComandaId;
    }

    public int getNumeroLinea() {
        return lineaComandaId.getNumeroLinea();
    }

    public void setNumeroLinea(int numeroLinea) {
        this.lineaComandaId.setNumeroLinea(numeroLinea);
    }

    public int getNumeroComanda() {
        return lineaComandaId.getNumeroComanda();
    }

    public void setNumeroComanda(int numeroComanda) {
        this.lineaComandaId.setNumeroComanda(numeroComanda);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }
}