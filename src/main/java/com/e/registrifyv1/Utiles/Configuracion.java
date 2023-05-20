package com.e.registrifyv1.Utiles;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuracion {

   private static final String ENV_FILE =".env";
   private static Dotenv dotenv;


   static {
      dotenv =  Dotenv.configure().ignoreIfMissing().load(); //modulo de variables de entorno//
      };


   public static String get(String key){
      return dotenv.get(key);
   }

}
