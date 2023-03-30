package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Categoria{

    @Id
    private int categoriaId;
    private String nombreCategoria;
    private String descripcionCategoria;

    /**
     * Constructor de la clase categoria
     * @param categoriaId Identificador unico de la categoria
     * @param nombreCategoria Nombre de la categoria
     * @param descripcionCategoria Descripcion de la categoria
     */
    public Categoria(int categoriaId, String nombreCategoria, String descripcionCategoria) {
        this.categoriaId = categoriaId;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public Categoria() {
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }
}