package com.e.registrifyv1.Modelos.Inventario;

public class InventarioModel {
    private int idAccesorio;
    private int idUnidad;
    private int idGendarme;

    private String nombreGendarme;
    private String apellidoGendarme;
    private String dniGendarme;
    private String nombreUnidad;

    private String nombreAccesorio; // "NUMEROSERIE_ACCESORIO"
    private String descrAccesorio; // "TIPO_ACCESORIO"

    public InventarioModel(int idAccesorio, int idUnidad, int idGendarme, String nombreAccesorio, String descrAccesorio) {
        this.idAccesorio = idAccesorio;
        this.idUnidad = idUnidad;
        this.idGendarme = idGendarme;
        this.nombreAccesorio = nombreAccesorio;
        this.descrAccesorio = descrAccesorio;
    }

    public InventarioModel(int idAccesorio, String nombreUnidad, String nombreGendarme, String apellidoGendarme, String dniGendarme, String nombreAccesorio, String descrAccesorio) {
        this.idAccesorio = idAccesorio;
        this.nombreUnidad = nombreUnidad;
        this.nombreGendarme = nombreGendarme;
        this.apellidoGendarme = apellidoGendarme;
        this.dniGendarme = dniGendarme;
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

    public String getNombreGendarme() {

        return nombreGendarme;
    }

    public void setNombreGendarme(String nombreGendarme) {
        this.nombreGendarme = nombreGendarme;
    }

    public String getApellidoGendarme() {
        return apellidoGendarme;
    }

    public void setApellidoGendarme(String apellidoGendarme) {
        this.apellidoGendarme = apellidoGendarme;
    }

    public String getDniGendarme() {
        return dniGendarme;
    }

    public void setDniGendarme(String dniGendarme) {
        this.dniGendarme = dniGendarme;
    }


    public String getGendarmeInfo() {
        return nombreGendarme +      " " + apellidoGendarme + "\nDNI: " + dniGendarme + " ";
    }


    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
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
