package com.RaspberryBar.entities;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Comanda{
    @Id
    private int numeroComanda;
    private float precioTotal;
    private LocalDateTime fechaHoraApertura;
    private LocalDateTime fechaHoraCierre;
    private int numeroComensales;
    private int usuarioId;
    private int mesaId;

    /**
     * Constructor para la clase backend.entities.Comanda
     * @param numeroComanda Identificador unico para la comanda
     * @param precioTotal Precio total de la comanda
     * @param fechaHoraApertura Fecha y Hora apertura mesa
     * @param fechaHoraCierre Fecha y Hora cierre mesa
     * @param numeroComensales Numero comensales mesa
     * @param usuarioId Identificador unico del usuario
     * @param mesaId Identificador unico de la mesa
     */
    public Comanda(int numeroComanda, float precioTotal, LocalDateTime fechaHoraApertura, LocalDateTime fechaHoraCierre, int numeroComensales, int usuarioId, int mesaId) {
        this.numeroComanda = numeroComanda;
        this.precioTotal = precioTotal;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.numeroComensales = numeroComensales;
        this.usuarioId = usuarioId;
        this.mesaId = mesaId;
    }

    public Comanda() {
    }

    public int getNumeroComanda() {
        return numeroComanda;
    }

    public void setNumeroComanda(int numeroComanda) {
        this.numeroComanda = numeroComanda;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(LocalDateTime fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public LocalDateTime getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(LocalDateTime fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public int getNumeroComensales() {
        return numeroComensales;
    }

    public void setNumeroComensales(int numeroComensales) {
        this.numeroComensales = numeroComensales;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }
}