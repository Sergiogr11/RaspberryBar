package com.RaspberryBar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.*;
import java.util.Properties;

public class ImpresoraService {

    private static ImpresoraService instance = new ImpresoraService();

    private String impresoraBarra;
    private String impresoraCocina;

    private ImpresoraService() {
        loadConfig();
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

    public void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + File.separator + "config.properties")) {
            properties.load(input);
            impresoraBarra = properties.getProperty("impresoraBarra");
            impresoraCocina = properties.getProperty("impresoraCocina");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveConfig() {
        try (OutputStream output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "config.properties")) {
            Properties properties = new Properties();
            properties.setProperty("impresoraBarra", impresoraBarra);
            properties.setProperty("impresoraCocina", impresoraCocina);
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
