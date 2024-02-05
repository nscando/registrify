package com.e.registrifyv1.Modelos.Rol;

public class Rol {
   public static final int ADMINISTRADOR = 1;
   public static final int SUPERVISOR = 2;
   public static final int USUARIO = 3;

   public static String getDescripcionRol(int idRol) {
      switch (idRol) {
         case ADMINISTRADOR:
            return "Administrador";
         case SUPERVISOR:
            return "Supervisor";
         case USUARIO:
            return "Usuario";
         default:
            return "Rol no definido";
      }
   }
}
