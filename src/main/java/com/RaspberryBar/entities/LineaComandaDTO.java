package com.RaspberryBar.entities;

import java.util.Objects;

public class LineaComandaDTO {
    private LineaComanda lineaComanda;
    private String nombreArticulo;

    public LineaComandaDTO(LineaComanda lineaComanda, String nombreArticulo) {
        this.lineaComanda = lineaComanda;
        this.nombreArticulo = nombreArticulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaComandaDTO that = (LineaComandaDTO) o;
        return Objects.equals(getLineaComanda().getLineaComandaId(), that.getLineaComanda().getLineaComandaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLineaComanda().getLineaComandaId());
    }

    public LineaComanda getLineaComanda() {
        return lineaComanda;
    }

    public void setLineaComanda(LineaComanda lineaComanda) {
        this.lineaComanda = lineaComanda;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }
}
