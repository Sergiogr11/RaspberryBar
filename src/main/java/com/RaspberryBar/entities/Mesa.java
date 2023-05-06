package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mesa{
    @Id
    private int mesaId;
    private String posicion;
    private int capacidad;
    private String estadoMesa = "Libre";
    private String nombreMesa;
    private int x;
    private int y;

    /**
     * Constructor para la clase backend.entities.Mesa
     * @param mesaId identificador unico para mesa
     * @param posicion posicion de la mesa
     * @param capacidad capacidad en personas
     * @param estadoMesa Ocupada o Libre
     * @param nombreMesa Nombre de la Mesa
     * @param x Posicion x de mesa
     * @param y Posicion y de mesa
     */
    public Mesa(int mesaId, String posicion, int capacidad, String estadoMesa, String nombreMesa) {
        this.mesaId = mesaId;
        this.posicion = posicion;
        this.capacidad = capacidad;
        this.estadoMesa = estadoMesa;
        this.nombreMesa = nombreMesa;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor vacio de mesa
     */
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

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public String getNombreMesa() {
        return nombreMesa;
    }

    public void setNombreMesa(String nombreMesa) {
        this.nombreMesa = nombreMesa;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}