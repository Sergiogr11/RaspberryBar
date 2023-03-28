package com.RaspberryBar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario{

    @Id
    private int userId;
    private String username;
    private String correo;
    private String contraseña;

    public Usuario() {
    }

    private enum Roles {
        User,
        Admin
    }
    private Roles rol = Roles.User;
    private String telefono;

    /**
     * Constructor para la clase backend.entities.Usuario
     * @param userId Identificador unico para el usuario
     * @param username Nickname unico
     * @param correo Correo electronico
     * @param contraseña Contraseña
     * @param rol Rol, que puede ser admin o user
     * @param telefono Telefono movil
     */
    public Usuario(int userId, String username, String correo, String contraseña, Roles rol, String telefono) {
        this.userId = userId;
        this.username = username;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.telefono = telefono;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}