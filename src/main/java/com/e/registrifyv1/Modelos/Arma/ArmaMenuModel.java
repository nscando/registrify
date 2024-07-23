package com.e.registrifyv1.Modelos.Arma;


public class ArmaMenuModel {

    private int idArma;
    private int idGendarme;
    private int idUnidad;
    private String nombreGendarme;
    private String apellidoGendarme;
    private String dniGendarme;
    private String nombreUnidad;
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



    public ArmaMenuModel(int idArma, String nombreGendarme, String apellidoGendarme, String dniGendarme, String nombreUnidad, String marcaArma, String tipoArma, String numeroSerieArma) {
        this.idArma = idArma;
        this.nombreGendarme = nombreGendarme;
        this.apellidoGendarme = apellidoGendarme;
        this.dniGendarme = dniGendarme;
        this.nombreUnidad = nombreUnidad;
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

    public int getIdUnidad() {
        return idUnidad;
    }

    public String getGendarmeInfo() {
        return nombreGendarme + " " + apellidoGendarme + "\nDNI: " + dniGendarme + " ";
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
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
