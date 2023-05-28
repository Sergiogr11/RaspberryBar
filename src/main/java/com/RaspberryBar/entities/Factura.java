package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Factura{
    @Id
    private int numeroFactura;
    private long fechaEmision;
    private String nombreReceptor;
    private String dniReceptor;
    private float baseImponible;
    private float importeTotal;
    private int restauranteId;
    private int comandaId;

    /**
     * Constructor para la clase backend.entities.Factura
     * @param numeroFactura Identificador unico backend.entities.Factura
     * @param fechaEmision Fecha emision
     * @param nombreReceptor Nombre completo del receptor
     * @param dniReceptor DNI o CIF del receptor
     * @param baseImponible Precio total sin IVA
     * @param importeTotal Importe total con el IVA sumado
     * @param restauranteId Identificador del restaurante asociado
     * @param comandaId Identificador de la comanda asociada
     */
    public Factura(int numeroFactura, long fechaEmision, String nombreReceptor, String dniReceptor, float baseImponible, float importeTotal, int restauranteId, int comandaId) {
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.nombreReceptor = nombreReceptor;
        this.dniReceptor = dniReceptor;
        this.baseImponible = baseImponible;
        this.importeTotal = importeTotal;
        this.restauranteId = restauranteId;
        this.comandaId = comandaId;
    }



    public Factura(){}

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public long getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(long fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getDniReceptor() {
        return dniReceptor;
    }

    public void setDniReceptor(String dniReceptor) {
        this.dniReceptor = dniReceptor;
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