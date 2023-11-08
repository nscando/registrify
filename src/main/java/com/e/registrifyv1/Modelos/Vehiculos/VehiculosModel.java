package com.e.registrifyv1.Modelos.Vehiculos;

public class VehiculosModel {
    private int idVehiculo;
    private int idUnidad;
    private int idGendarme;
    private String tipoVehiculo;
    private String marcaVehiculo;
    private String modelo;

    //private Float kilometraje;//
    private String patente;

    public VehiculosModel( int idVehiculo, int idUnidad, int idGendarme, String tipoVehiculo,  String marcaVehiculo, String modelo, String patente) {

        this.idVehiculo = idVehiculo;
        this.idGendarme = idGendarme;
        this.idUnidad = idUnidad;
        this.tipoVehiculo = tipoVehiculo;
        this.marcaVehiculo = marcaVehiculo;
        this.modelo = modelo;
        this.patente = patente;
        //falta km
    }

    // Getters y setters

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdGendarme() {
        return idGendarme;
    }

    public void setIdGendarme(int idGendarme) {
        this.idGendarme = idGendarme;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }
}
