package com.RaspberryBar.entities;

public class Articulo{
    int articuloId;
    String nombreArticulo;
    String descripcionArticulo;
    float precio;
    int categoriaId;

    /**
     * Constructor para la clase articulo
     * @param articuloId Identificador unico del articulo
     * @param nombreArticulo Nombre del articulo
     * @param descripcionArticulo Descripcion del articulo
     * @param precio Precio del articulo
     * @param categoriaId backend.entities.Categoria del artículo
     */
    public Articulo(int articuloId, String nombreArticulo, String descripcionArticulo, float precio, int categoriaId) {
        this.articuloId = articuloId;
        this.nombreArticulo = nombreArticulo;
        this.descripcionArticulo = descripcionArticulo;
        this.precio = precio;
        this.categoriaId = categoriaId;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}