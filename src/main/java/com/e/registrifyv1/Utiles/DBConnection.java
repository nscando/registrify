package com.e.registrifyv1.Utiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   public Connection DBLink;

   public Connection getConexion() {
      String dbNombre = "registrify";
      String dbUser = "root";
      String dbPassword = "***";
      String dbUrl = "jdbc:mysql://localhost/" + dbNombre + "?serverTimezone=UTC";

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         DBLink = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }
      return DBLink;
   }
}
