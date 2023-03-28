package com.RaspberryBar.entities;

public class Mesa{
    int id;
    String posicion;
    int capacidad;
    enum EstadoMesa{
        Libre,
        Ocupada
    }
    EstadoMesa estadoMesa = EstadoMesa.Libre;

    /**
     * Constructor para la clase backend.entities.Mesa
     * @param id identificador unico para mesa
     * @param posicion posicion de la mesa
     * @param capacidad capacidad en personas
     * @param estadoMesa Ocupada o Libre
     */
    public Mesa(int id, String posicion, int capacidad, EstadoMesa estadoMesa) {
        this.id = id;
        this.posicion = posicion;
        this.capacidad = capacidad;
        this.estadoMesa = estadoMesa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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