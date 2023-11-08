package com.e.registrifyv1.Modelos.Unidad;



public class UnidadMenuModel {


    private int idUnidad;
    private String nombreUnidad;
    private String ubicacionUnidad;

    public UnidadMenuModel(int idUnidad, String nombreUnidad, String ubicacionUnidad) {
        this.idUnidad = idUnidad;
        this.nombreUnidad = nombreUnidad;
        this.ubicacionUnidad = ubicacionUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getUbicacionUnidad() {
        return ubicacionUnidad;
    }

    public void setUbicacionUnidad(String ubicacionUnidad) {
        this.ubicacionUnidad = ubicacionUnidad;
    }

}
