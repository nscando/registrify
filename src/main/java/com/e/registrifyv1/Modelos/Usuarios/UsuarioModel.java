package com.e.registrifyv1.Modelos.Usuarios;

public class UsuarioModel {
   private int idGendarme;
   private int idUnidad;
   private int idRol;
   private String nombre;
   private String rango;
   private String area;
   private String password;
   private int estado;
   private String username;
   private String apellido;
   private int dni;
   private String observaciones;

   public UsuarioModel(int idGendarme, int idUnidad, int idRol, String nombre, String username, String rango, String area, byte[] password, int estado, String apellido, int dni, String observaciones) {
      this.idGendarme = idGendarme;
      this.idUnidad = idUnidad;
      this.idRol = idRol;
      this.nombre = nombre;
      this.username = username;
      this.rango = rango;
      this.area = area;
      this.password = new String(password);
      this.estado = estado;
      this.apellido = apellido;
      this.dni = dni;
      this.observaciones = observaciones;
   }



   // Métodos getter y setter para los campos de la clase

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

   public int getIdRol() {
      return idRol;
   }

   public void setIdRol(int idRol) {
      this.idRol = idRol;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getRango() {
      return rango;
   }

   public void setRango(String rango) {
      this.rango = rango;
   }

   public String getArea() {
      return area;
   }

   public void setArea(String area) {
      this.area = area;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public int getEstado() {
      return estado;
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getApellido() {
      return apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   //public int getDni() {
   //   return dni;
   //}

   public int getDni(){
      return dni;
   }

   public void setDni(int dni) {
      this.dni = dni;
   }

   public String getObservaciones() {
      return observaciones;
   }

   public void setObservaciones(String observaciones) {
      this.observaciones = observaciones;
   }
}
