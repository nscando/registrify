package com.e.registrifyv1.Modelos.Usuarios;

public class UsuarioModel {
   private int idGendarme;
   private int idUnidad;
   private int idRol;
   private String nombreGendarme;
   private String rango;
   private String area;
   private String password;
   private int estado;
   private String nombreUsuario;

   public UsuarioModel(int idGendarme, int idUnidad, int idRol, String nombreGendarme, String nombreUsuario, String rango, String area, byte[] password, int estado) {
      // Convertir el byte[] a String
      this.password = new String(password);

      // Asignar los valores a los campos de la clase
      this.idGendarme = idGendarme;
      this.idUnidad = idUnidad;
      this.idRol = idRol;
      this.nombreGendarme = nombreGendarme;
      this.rango = rango;
      this.area = area;
      this.estado = estado;
      this.nombreUsuario = nombreUsuario;
   }

   public UsuarioModel(int idGendarme, int idUnidad, int idRol, String nombreGendarme, String nombreUsuario, String rango, String area, byte[] passwordBytes) {
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

   public String getNombreGendarme() {
      return nombreGendarme;
   }

   public void setNombreGendarme(String nombreGendarme) {
      this.nombreGendarme = nombreGendarme;
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

   public String getNombreUsuario() {
      return nombreUsuario;
   }

   public void setNombreUsuario(String nombreUsuario) {
      this.nombreUsuario = nombreUsuario;
   }
}
