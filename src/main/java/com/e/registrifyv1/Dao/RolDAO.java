package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.RolModel.RolModel;
import com.e.registrifyv1.Utiles.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {
   private DBConnection dbConnection;

   public RolDAO() {
      dbConnection = new DBConnection();
   }

   public List<RolModel> obtenerRoles() {
      List<RolModel> roles = new ArrayList<>();
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT * FROM ROL";
         statement = connection.prepareStatement(query);
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idRol = resultSet.getInt("ID_ROL");
            String nombreRol = resultSet.getString("NOMBRE_ROL");

            RolModel rol = new RolModel(idRol, nombreRol);
            roles.add(rol);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return roles;
   }

   private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
      try {
         if (resultSet != null) {
            resultSet.close();
         }

         if (statement != null) {
            statement.close();
         }

         if (connection != null) {
            connection.close();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}

