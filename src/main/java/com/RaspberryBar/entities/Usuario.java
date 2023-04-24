package com.RaspberryBar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario{

    @Id
    private int userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String rol = "User";
    @Column(nullable = false)
    private String telefono;

    /**
     * Constructor vacío para Usuario
     */
    public Usuario() {
    }

    /**
     * Constructor para la clase backend.entities.Usuario
     * @param userId Identificador unico para el usuario
     * @param username Nickname unico
     * @param correo Correo electronico
     * @param password Contraseña
     * @param rol Rol, que puede ser admin o user
     * @param telefono Telefono movil
     */
    public Usuario(int userId, String username, String correo, String password, String rol, String telefono) {
        this.userId = userId;
        this.username = username;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.telefono = telefono;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}