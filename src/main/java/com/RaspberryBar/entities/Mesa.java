package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mesa{
    @Id
    private int mesaId;
    private String posicion;
    private int capacidad;
    private enum EstadoMesa{
        Libre,
        Ocupada
    }
    EstadoMesa estadoMesa = EstadoMesa.Libre;

    /**
     * Constructor para la clase backend.entities.Mesa
     * @param mesaId identificador unico para mesa
     * @param posicion posicion de la mesa
     * @param capacidad capacidad en personas
     * @param estadoMesa Ocupada o Libre
     */
    public Mesa(int mesaId, String posicion, int capacidad, EstadoMesa estadoMesa) {
        this.mesaId = mesaId;
        this.posicion = posicion;
        this.capacidad = capacidad;
        this.estadoMesa = estadoMesa;
    }

    public Mesa() {
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(EstadoMesa estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
}