package com.e.registrifyv1.Utiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   public Connection DBLink;

   public Connection getConexion() {

      String dbNombre = Configuracion.get("DB_NAME");
      String dbUser = Configuracion.get("DB_USERNAME");
      String dbPassword = Configuracion.get("DB_PASSWORD");
      String dbUrl = Configuracion.get("DB_URL");

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         DBLink = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }

      return DBLink;
   }
}
