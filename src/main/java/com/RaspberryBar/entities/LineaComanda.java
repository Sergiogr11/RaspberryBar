package com.RaspberryBar.entities;

public class LineaComanda{

    int numeroLinea;
    int numeroComanda;
    int cantidad;
    float precio;
    int articuloId;

    /**
     * Constructor para Linea backend.entities.Comanda
     * @param numeroLinea numero de Linea de la comanda
     * @param numeroComanda numero de comanda asociado
     * @param cantidad cantidad del artículo
     * @param precio precio del artículo
     * @param articuloId artículo asociado a la linea de la comanda
     */
    public LineaComanda(int numeroLinea, int numeroComanda, int cantidad, float precio, int articuloId) {
        this.numeroLinea = numeroLinea;
        this.numeroComanda = numeroComanda;
        this.cantidad = cantidad;
        this.precio = precio;
        this.articuloId = articuloId;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public int getNumeroComanda() {
        return numeroComanda;
    }

    public void setNumeroComanda(int numeroComanda) {
        this.numeroComanda = numeroComanda;
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