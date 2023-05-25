package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Restaurante{

    @Id
    private int restauranteId;
    private String nombre;
    private String tipoImpositivo;
    private String cif;
    private String direccion;
    private String telefono;

    /**
     * Constructor para la clase backend.entities.Restaurante
     * @param restauranteId Identificador unico restaurante
     * @param nombre Nombre del restaurante
     * @param tipoImpositivo Porcentaje establecido para operaciones comerciales
     * @param cif Codigo de identificacion fiscal restaurante
     * @param direccion Direccion postal del restaurante
     * @param telefono Telefono movil del restaurante
     */
    public Restaurante(int restauranteId, String nombre, String tipoImpositivo, String cif, String direccion, String telefono) {
        this.restauranteId = restauranteId;
        this.nombre = nombre;
        this.tipoImpositivo = tipoImpositivo;
        this.cif = cif;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Restaurante(){}

    public int getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(int restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoImpositivo() {
        return tipoImpositivo;
    }

    public void setTipoImpositivo(String tipoImpositivo) {
        this.tipoImpositivo = tipoImpositivo;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}