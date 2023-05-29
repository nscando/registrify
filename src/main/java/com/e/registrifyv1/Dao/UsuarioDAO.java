package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

   private DBConnection dbConnection;

   public UsuarioDAO() {
      dbConnection = new DBConnection();
   }

   public UsuarioModel getUsuarioByUsernameAndPassword(String username, String password) {
      UsuarioModel usuario = null;
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT * FROM USUARIO WHERE USERNAME = ? AND PASSWORD = ?";
         statement = connection.prepareStatement(query);
         statement.setString(1, username);
         statement.setString(2, password);
         resultSet = statement.executeQuery();

         if (resultSet.next()) {
            int idGendarme = resultSet.getInt("ID_GENDARME");
            int idUnidad = resultSet.getInt("ID_UNIDAD");
            int idRol = resultSet.getInt("ID_ROL");
            String nombreGendarme = resultSet.getString("NOMBRE_GENDARME");
            String nombreUsuario = resultSet.getString("USERNAME");
            String rango = resultSet.getString("RANGO");
            String area = resultSet.getString("AREA");
            byte[] passwordBytes = resultSet.getBytes("PASSWORD");
            int estado = resultSet.getInt("ESTADO");

            usuario = new UsuarioModel(idGendarme, idUnidad, idRol, nombreGendarme, nombreUsuario, rango, area, passwordBytes);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return usuario;
   }


   public List<UsuarioModel> obtenerTodosUsuarios() {
      List<UsuarioModel> usuarios = new ArrayList<>();
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT * FROM USUARIO";
         statement = connection.prepareStatement(query);
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idGendarme = resultSet.getInt("ID_GENDARME");
            int idUnidad = resultSet.getInt("ID_UNIDAD");
            int idRol = resultSet.getInt("ID_ROL");
            String nombreGendarme = resultSet.getString("NOMBRE_GENDARME");
            String nombreUsuario = resultSet.getString("USERNAME");
            String rango = resultSet.getString("RANGO");
            String area = resultSet.getString("AREA");
            byte[] password = resultSet.getBytes("PASSWORD");
            int estado = resultSet.getInt("ESTADO");

            UsuarioModel usuario = new UsuarioModel(idGendarme, idUnidad, idRol, nombreGendarme, nombreUsuario, rango, area, password);
            usuarios.add(usuario);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return usuarios;
   }


   public ObservableList<UsuarioModel> buscarUsuarios(String valor) {
      ObservableList<UsuarioModel> usuarios = FXCollections.observableArrayList();
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT * FROM USUARIO WHERE ID_GENDARME = ? OR NOMBRE_GENDARME LIKE ?";
         statement = connection.prepareStatement(query);
         statement.setString(1, valor);
         statement.setString(2, "%" + valor + "%");
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idGendarme = resultSet.getInt("ID_GENDARME");
            int idUnidad = resultSet.getInt("ID_UNIDAD");
            int idRol = resultSet.getInt("ID_ROL");
            String nombreGendarme = resultSet.getString("NOMBRE_GENDARME");
            String nombreUsuario = resultSet.getString("USERNAME");
            String rango = resultSet.getString("RANGO");
            String area = resultSet.getString("AREA");
            byte[] password = resultSet.getBytes("PASSWORD");
            int estado = resultSet.getInt("ESTADO");

            UsuarioModel usuario = new UsuarioModel(idGendarme, idUnidad, idRol, nombreUsuario,nombreGendarme, rango, area, password, estado);
            usuarios.add(usuario);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return usuarios;
   }


   public void insertarUsuario(UsuarioModel usuario) {
      Connection connection = null;
      PreparedStatement statement = null;

      try {
         connection = dbConnection.getConexion();
         String query = "INSERT INTO USUARIO (ID_GENDARME, ID_UNIDAD, ID_ROL, NOMBRE_GENDARME, RANGO, AREA, PASSWORD) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
         statement = connection.prepareStatement(query);
         statement.setInt(1, usuario.getIdGendarme());
         statement.setInt(2, usuario.getIdUnidad());
         statement.setInt(3, usuario.getIdRol());
         statement.setString(4, usuario.getNombreGendarme());
         statement.setString(4, usuario.getNombreUsuario());
         statement.setString(5, usuario.getRango());
         statement.setString(6, usuario.getArea());
         statement.setBytes(7, usuario.getPassword().getBytes());

         statement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, null);
      }
   }

   public void actualizarUsuario(UsuarioModel usuario) {
      Connection connection = null;
      PreparedStatement statement = null;

      try {
         connection = dbConnection.getConexion();
         String query = "UPDATE USUARIO SET ID_UNIDAD = ?, ID_ROL = ?, NOMBRE_GENDARME = ?, RANGO = ?, AREA = ?, " +
                 "PASSWORD = ? WHERE ID_GENDARME = ?";
         statement = connection.prepareStatement(query);
         statement.setInt(1, usuario.getIdUnidad());
         statement.setInt(2, usuario.getIdRol());
         statement.setString(3, usuario.getNombreGendarme());
         statement.setString(3, usuario.getNombreUsuario());
         statement.setString(4, usuario.getRango());
         statement.setString(5, usuario.getArea());
         statement.setBytes(6, usuario.getPassword().getBytes());
         statement.setInt(7, usuario.getIdGendarme());

         statement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, null);
      }
   }

   public void bajaUsuario(int idGendarme) {
      Connection connection = null;
      PreparedStatement statement = null;
      try {
         // Obtener la conexión a la base de datos
         connection = dbConnection.getConexion();

         // Preparar la consulta SQL
         String query = "UPDATE USUARIO SET ESTADO = ? WHERE ID_GENDARME = ?";
         statement = connection.prepareStatement(query);

         // Establecer los parámetros de la consulta
         statement.setInt(1, 0); // Estado 0 para dado de baja
         statement.setInt(2, idGendarme);

         // Ejecutar la consulta
         statement.executeUpdate();

         // Cerrar el statement
         statement.close();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         // Cerrar la conexión
         closeResources(connection, statement, null);
      }
   }

   public List<String> obtenerOpcionesUnidad() throws SQLException {
      List<String> opcionesUnidad = new ArrayList<>();

      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT NOMBRE_UNIDAD FROM UNIDAD";
         statement = connection.prepareStatement(query);
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            String nombreUnidad = resultSet.getString("NOMBRE_UNIDAD");
            opcionesUnidad.add(nombreUnidad);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return opcionesUnidad;
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
