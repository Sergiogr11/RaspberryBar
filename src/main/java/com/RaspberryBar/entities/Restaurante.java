package com.RaspberryBar.entities;

public class Restaurante{
    int restauranteId;
    String tipoImpositivo;
    String cif;
    String direccion;
    String telefono;

    /**
     * Constructor para la clase backend.entities.Restaurante
     * @param restauranteId Identificador unico restaurante
     * @param tipoImpositivo Porcentaje establecido para operaciones comerciales
     * @param cif Codigo de identificacion fiscal restaurante
     * @param direccion Direccion postal del restaurante
     * @param telefono Telefono movil del restaurante
     */
    public Restaurante(int restauranteId, String tipoImpositivo, String cif, String direccion, String telefono) {
        this.restauranteId = restauranteId;
        this.tipoImpositivo = tipoImpositivo;
        this.cif = cif;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(int restauranteId) {
        this.restauranteId = restauranteId;
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