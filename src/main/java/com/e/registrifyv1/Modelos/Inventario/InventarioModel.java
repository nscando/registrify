package com.e.registrifyv1.Modelos.Inventario;

public class InventarioModel {
    private int idAccesorio;
    private int idUnidad;
    private int idGendarme;
    private String nombreAccesorio; // "NUMEROSERIE_ACCESORIO"
    private String descrAccesorio; // "TIPO_ACCESORIO"

    public InventarioModel(int idAccesorio, int idUnidad, int idGendarme, String nombreAccesorio, String descrAccesorio) {
        this.idAccesorio = idAccesorio;
        this.idUnidad = idUnidad;
        this.idGendarme = idGendarme;
        this.nombreAccesorio = nombreAccesorio;
        this.descrAccesorio = descrAccesorio;
    }

    // Getters y setters
    public int getIdAccesorio() {
        return idAccesorio;
    }

    public void setIdAccesorio(int idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdGendarme() {
        return idGendarme;
    }

    public void setIdGendarme(int idGendarme) {
        this.idGendarme = idGendarme;
    }

    public String getNombreAccesorio() {
        return nombreAccesorio;
    }

    public void setNombreAccesorio(String nombreAccesorio) {
        this.nombreAccesorio = nombreAccesorio;
    }

    public String getDescrAccesorio() {
        return descrAccesorio;
    }

    public void setDescrAccesorio(String descrAccesorio) {
        this.descrAccesorio = descrAccesorio;
    }
}
