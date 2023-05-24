package com.RaspberryBar.service;

import java.io.IOException;

public class ImpresoraService {

    private static ImpresoraService instance = new ImpresoraService();

    private String impresoraBarra;
    private String impresoraCocina;

    private ImpresoraService() {
    }


    public static ImpresoraService getInstance(){
        return instance;
    }

    public String getImpresoraBarra() {
        return impresoraBarra;
    }

    public void setImpresoraBarra(String impresoraBarra) {
        this.impresoraBarra = impresoraBarra;
    }

    public String getImpresoraCocina() {
        return impresoraCocina;
    }

    public void setImpresoraCocina(String impresoraCocina) {
        this.impresoraCocina = impresoraCocina;
    }
}
