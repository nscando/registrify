package com.e.registrifyv1.Modelos.Rol;

public class RolModel {
   private int idRol;
   private String nombreRol;

   public RolModel(int idRol, String nombreRol) {
      this.idRol = idRol;
      this.nombreRol = nombreRol;
   }

   // Getters y setters

   public int getIdRol() {
      return idRol;
   }

   public void setIdRol(int idRol) {
      this.idRol = idRol;
   }

   public String getNombreRol() {
      return nombreRol;
   }

   public void setNombreRol(String nombreRol) {
      this.nombreRol = nombreRol;
   }
}