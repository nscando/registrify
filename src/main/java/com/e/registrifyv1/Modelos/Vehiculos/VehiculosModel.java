package com.e.registrifyv1.Modelos.Vehiculos;

public class VehiculosModel {

    private int idVehiculo;
    private int idUnidad;
    private int idGendarme;

    private String nombreGendarme;
    private String apellidoGendarme;
    private String dniGendarme;
    private String nombreUnidad;

    private String tipoVehiculo;
    private String marcaVehiculo;
    private String modelo;
    private String patente;
    private String kilometraje;

    public VehiculosModel(int idVehiculo, int idUnidad, int idGendarme, String tipoVehiculo, String marcaVehiculo, String modelo, String patente, String kilometraje) {

        this.idVehiculo = idVehiculo;
        this.idUnidad = idUnidad;
        this.idGendarme = idGendarme;
        this.tipoVehiculo = tipoVehiculo;
        this.marcaVehiculo = marcaVehiculo;
        this.modelo = modelo;
        this.patente = patente;
        this.kilometraje = kilometraje;

    }

    // Getters y setters

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

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
        return nombreGendarme + " " + apellidoGendarme + "\nDNI: " + dniGendarme + " ";
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
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

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }
}
