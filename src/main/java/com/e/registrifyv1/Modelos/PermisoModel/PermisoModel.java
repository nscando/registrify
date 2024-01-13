package com.e.registrifyv1.Modelos.PermisoModel;

public class PermisoModel {

      private int idPermiso;
      private String nombrePermiso;

      public PermisoModel(int idPermiso, String nombrePermiso) {
         this.idPermiso = idPermiso;
         this.nombrePermiso = nombrePermiso;
      }

      // Getters y setters

      public int getIdPermiso() {
         return idPermiso;
      }

      public void setIdPermiso(int idPermiso) {
         this.idPermiso = idPermiso;
      }

      public String getNombrePermiso() {
         return nombrePermiso;
      }

      public void setNombrePermiso(String nombrePermiso) {
         this.nombrePermiso = nombrePermiso;
      }
   }


