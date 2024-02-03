package com.e.registrifyv1.Utiles;


import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;

public class Session {
   private static UsuarioModel usuarioLogueado;

   public static void iniciarSesion(UsuarioModel usuario) {
      usuarioLogueado = usuario;
   }

   public static void cerrarSesion() {
      usuarioLogueado = null;
   }

   public static boolean isSesionIniciada() {
      return usuarioLogueado != null;
   }

   public static UsuarioModel getUsuarioLogueado() {
      return usuarioLogueado;
   }

   // Métodos adicionales para obtener datos específicos del usuario logueado
   public static int getIdRol() {
      if (isSesionIniciada()) {
         return usuarioLogueado.getIdRol();
      }
      return -1;
   }

   public static String getNombreUsuario() {
      if (isSesionIniciada()) {
         return usuarioLogueado.getNombre();
      }
      return null;
   }

   public static String getApellidoUsuario() {
      if (isSesionIniciada()) {
         return usuarioLogueado.getApellido();
      }
      return null;
   }
}
