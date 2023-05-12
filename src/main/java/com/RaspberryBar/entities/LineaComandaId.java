package com.RaspberryBar.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;


@EqualsAndHashCode
@Embeddable
public class LineaComandaId implements Serializable {
    private int numeroLinea;
    private int numeroComanda;

    public LineaComandaId() {
    }

    public LineaComandaId(int numeroLinea, int numeroComanda) {
        this.numeroLinea = numeroLinea;
        this.numeroComanda = numeroComanda;
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
}