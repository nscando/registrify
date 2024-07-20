package com.e.registrifyv1.Modelos.EventoDiario;

import java.util.Date;

public class EventoDiarioModel {
    private int idEvento;
    private int idUnidad;
    private int idGendarme;
    private String descrEvento;
    private String fechaEvento;
    private int estado;

    public EventoDiarioModel(int idEvento, int idUnidad, int idGendarme, String descrEvento, String fechaEvento, int estado){
        this.idEvento=idEvento;
        this.idUnidad=idUnidad;
        this.idGendarme=idGendarme;
        this.descrEvento=descrEvento;
        this.fechaEvento=fechaEvento;
        this.estado=estado;
    }

    // Getters y setters

    public int getIdEvento() {

        return idEvento;    }

    public void setIdEvento(int idEvento) {

        this.idEvento = idEvento;    }

    public int getIdUnidad() {
        return idUnidad;    }


    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;    }


    public int getIdGendarme() {
        return idGendarme;    }


    public void setIdGendarme(int idGendarme) {
        this.idGendarme = idGendarme;    }


    public String getDescrEvento() {
        return descrEvento;    }


    public void setDescrEvento(String descrEvento) {
        this.descrEvento = descrEvento;    }


    public String getFechaEvento() {
        return fechaEvento;    }


    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;    }


    public int getEstado() {
        return estado;    }


    public void setEstado(int estado) {
        this.estado = estado;   }

}