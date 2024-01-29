package com.e.registrifyv1.Modelos.Arma;


public class ArmaMenuModel {

    private int idArma;
    private int idGendarme;
    private int idUnidad;
    private String marcaArma;
    private String tipoArma;
    private String numeroSerieArma;

    public ArmaMenuModel(int idArma, int idGendarme, int idUnidad, String marcaArma, String tipoArma, String numeroSerieArma) {
        this.idArma = idArma;
        this.idGendarme = idGendarme;
        this.idUnidad = idUnidad;
        this.marcaArma = marcaArma;
        this.tipoArma = tipoArma;
        this.numeroSerieArma = numeroSerieArma;
    }

    public int getIdArma() {
        return idArma;
    }

    public void setIdArma(int idArma) {
        this.idArma = idArma;
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

    public String getMarcaArma() {
        return marcaArma;
    }

    public void setMarcaArma(String marcaArma) {
        this.marcaArma = marcaArma;
    }

    public String getTipoArma() {
        return tipoArma;
    }

    public void setTipoArma(String tipoArma) {
        this.tipoArma = tipoArma;
    }

    public String getNumeroSerieArma() {
        return numeroSerieArma;
    }

    public void setNumeroSerieArma(String numeroSerieArma) {
        this.numeroSerieArma = numeroSerieArma;
    }
}
