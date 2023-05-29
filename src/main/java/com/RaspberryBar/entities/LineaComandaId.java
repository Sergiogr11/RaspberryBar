package com.RaspberryBar.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaComandaId that = (LineaComandaId) o;
        return getNumeroLinea() == that.getNumeroLinea() &&
                getNumeroComanda() == that.getNumeroComanda();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroLinea(), getNumeroComanda());
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