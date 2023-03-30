package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Factura{
    @Id
    private int numeroFactura;
    private String fechaEmision;
    private String datosReceptor;
    private float baseImponible;
    private float importeTotal;
    private String registroMercantil;
    private int restauranteId;
    private int comandaId;

    /**
     * Constructor para la clase backend.entities.Factura
     * @param numeroFactura Identificador unico backend.entities.Factura
     * @param fechaEmision Fecha emision
     * @param datosReceptor Datos del receptor
     * @param baseImponible Precio total sin IVA
     * @param importeTotal Importe total con el IVA sumado
     * @param registroMercantil Identificador del registro mercantil
     * @param restauranteId Identificador del restaurante asociado
     * @param comandaId Identificador de la comanda asociada
     */
    public Factura(int numeroFactura, String fechaEmision, String datosReceptor, float baseImponible, float importeTotal, String registroMercantil, int restauranteId, int comandaId) {
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.datosReceptor = datosReceptor;
        this.baseImponible = baseImponible;
        this.importeTotal = importeTotal;
        this.registroMercantil = registroMercantil;
        this.restauranteId = restauranteId;
        this.comandaId = comandaId;
    }

    public Factura(){}

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDatosReceptor() {
        return datosReceptor;
    }

    public void setDatosReceptor(String datosReceptor) {
        this.datosReceptor = datosReceptor;
    }

    public float getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(float baseImponible) {
        this.baseImponible = baseImponible;
    }

    public float getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(float importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(String registroMercantil) {
        this.registroMercantil = registroMercantil;
    }

    public int getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(int restauranteId) {
        this.restauranteId = restauranteId;
    }

    public int getComandaId() {
        return comandaId;
    }

    public void setComandaId(int comandaId) {
        this.comandaId = comandaId;
    }
}