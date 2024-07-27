package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

   private static final String LOGS_DIRECTORY = "LogsUsuarios";
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
   private final DBConnection dbConnection;

   public UsuarioDAO() {
      dbConnection = new DBConnection();
      createLogsDirectory();
   }

   private void createLogsDirectory() {
      File directory = new File(LOGS_DIRECTORY);
      if (!directory.exists()) {
         directory.mkdirs();
      }
   }

   private void logTransaction(String action, int userId, UsuarioModel usuario) {
      String logFileName = LOGS_DIRECTORY + File.separator + "Log_" + DATE_FORMAT.format(new Date(System.currentTimeMillis())) + ".txt";
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
         writer.write(String.format("[%s] Acción: %s, Usuario Modificador ID: %d, Usuario Afectado: %s%n",
                 new Timestamp(System.currentTimeMillis()), action, userId, usuario.toString()));
      } catch (IOException e) {
         e.printStackTrace();
      }
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
            String nombre = resultSet.getString("NOMBRE");
            String apellido = resultSet.getString("APELLIDO");
            String username1 = resultSet.getString("USERNAME");
            String rango = resultSet.getString("RANGO");
            String area = resultSet.getString("AREA");
            String observaciones = resultSet.getString("OBSERVACIONES");
            byte[] passwordBytes = resultSet.getBytes("PASSWORD");
            int estado = resultSet.getInt("ESTADO");
            String dni = resultSet.getString("DNI");
            Timestamp dateAdd = resultSet.getTimestamp("DATE_ADD");

            usuario = new UsuarioModel(idGendarme, idUnidad, idRol, nombre, apellido, dni, username1, rango, area, passwordBytes, estado, observaciones, dateAdd);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return usuario;
   }

   public ObservableList<UsuarioModel> buscarUsuarios(String valor, boolean incluirBaja, Date fechaDesde, Date fechaHasta) {
      ObservableList<UsuarioModel> usuarios = FXCollections.observableArrayList();
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         StringBuilder query = new StringBuilder("SELECT * FROM USUARIO WHERE (LOWER(ID_GENDARME) = LOWER(?) OR LOWER(NOMBRE) LIKE LOWER(?) OR LOWER(APELLIDO) LIKE LOWER(?) OR LOWER(DNI) = LOWER(?))");

         if (!incluirBaja) {
            query.append(" AND ESTADO = 1");
         }
         if (fechaDesde != null) {
            query.append(" AND DATE_ADD >= ?");
         }
         if (fechaHasta != null) {
            query.append(" AND DATE_ADD < ?");
         }

         statement = connection.prepareStatement(query.toString());
         statement.setString(1, valor);
         statement.setString(2, "%" + valor + "%");
         statement.setString(3, "%" + valor + "%");
         statement.setString(4, valor);
         int index = 5;
         if (fechaDesde != null) {
            statement.setTimestamp(index++, Timestamp.valueOf(fechaDesde.toLocalDate().atStartOfDay()));
         }
         if (fechaHasta != null) {
            // Para incluir
            statement.setTimestamp(index, Timestamp.valueOf(fechaHasta.toLocalDate().plusDays(1).atStartOfDay()));
         }
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idGendarme = resultSet.getInt("ID_GENDARME");
            int idUnidad = resultSet.getInt("ID_UNIDAD");
            int idRol = resultSet.getInt("ID_ROL");
            String nombre = resultSet.getString("NOMBRE");
            String apellido = resultSet.getString("APELLIDO");
            String dni = resultSet.getString("DNI");
            String username = resultSet.getString("USERNAME");
            String rango = resultSet.getString("RANGO");
            String area = resultSet.getString("AREA");
            byte[] password = resultSet.getBytes("PASSWORD");
            int estado = resultSet.getInt("ESTADO");
            String observaciones = resultSet.getString("OBSERVACIONES");
            Timestamp dateAdd = resultSet.getTimestamp("DATE_ADD");

            UsuarioModel usuario = new UsuarioModel(idGendarme, idUnidad, idRol, nombre, apellido, dni, username, rango, area, password, estado, observaciones, dateAdd);
            usuarios.add(usuario);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return usuarios;
   }


   public ObservableList<UsuarioModel> buscarUsuariosActivos(String valor) {
      ObservableList<UsuarioModel> usuarios = FXCollections.observableArrayList();
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT * FROM USUARIO WHERE (LOWER(ID_GENDARME) = LOWER(?) OR LOWER(NOMBRE) LIKE LOWER(?) OR LOWER(APELLIDO) LIKE LOWER(?) OR LOWER(DNI) = LOWER(?)) AND ESTADO = 1";
         statement = connection.prepareStatement(query);
         statement.setString(1, valor);
         statement.setString(2, "%" + valor + "%");
         statement.setString(3, "%" + valor + "%");
         statement.setString(4, valor);
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idGendarme = resultSet.getInt("ID_GENDARME");
            int idUnidad = resultSet.getInt("ID_UNIDAD");
            int idRol = resultSet.getInt("ID_ROL");
            String nombre = resultSet.getString("NOMBRE");
            String apellido = resultSet.getString("APELLIDO");
            String dni = resultSet.getString("DNI");
            String username = resultSet.getString("USERNAME");
            String rango = resultSet.getString("RANGO");
            String area = resultSet.getString("AREA");
            byte[] password = resultSet.getBytes("PASSWORD");
            int estado = resultSet.getInt("ESTADO");
            String observaciones = resultSet.getString("OBSERVACIONES");
            Timestamp dateAdd = resultSet.getTimestamp("DATE_ADD");

            UsuarioModel usuario = new UsuarioModel(idGendarme, idUnidad, idRol, nombre, apellido, dni, username, rango, area, password, estado, observaciones, dateAdd);
            usuarios.add(usuario);
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return usuarios;
   }

   public List<UnidadMenuModel> obtenerOpcionesUnidad() throws SQLException {
      List<UnidadMenuModel> opcionesUnidad = new ArrayList<>();

      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT ID_UNIDAD, NOMBRE_UNIDAD, UBICACION FROM UNIDAD";
         statement = connection.prepareStatement(query);
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idUnidad = resultSet.getInt("ID_UNIDAD");
            String nombreUnidad = resultSet.getString("NOMBRE_UNIDAD");
            String ubicacion = resultSet.getString("UBICACION");

            UnidadMenuModel unidad = new UnidadMenuModel(idUnidad, nombreUnidad, ubicacion);
            opcionesUnidad.add(unidad);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return opcionesUnidad;
   }

   public boolean insertarUsuario(UsuarioModel usuario, int userId) {
      Connection connection = null;
      PreparedStatement statement = null;

      try {
         connection = dbConnection.getConexion();
         String query = "INSERT INTO USUARIO (ID_UNIDAD, ID_ROL, NOMBRE, APELLIDO, DNI, USERNAME, RANGO, AREA, PASSWORD, ESTADO, OBSERVACIONES, DATE_ADD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
         statement = connection.prepareStatement(query);
         statement.setInt(1, usuario.getIdUnidad());
         statement.setInt(2, usuario.getIdRol());
         statement.setString(3, usuario.getNombre());
         statement.setString(4, usuario.getApellido());
         statement.setString(5, usuario.getDni());
         statement.setString(6, usuario.getUsername());
         statement.setString(7, usuario.getRango());
         statement.setString(8, usuario.getArea());
         statement.setBytes(9, usuario.getPassword());
         statement.setInt(10, usuario.getEstado());
         statement.setString(11, usuario.getObservaciones());

         int rowsAffected = statement.executeUpdate();
         if (rowsAffected > 0) {
            logTransaction("Inserción", userId, usuario);
         }
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         closeResources(connection, statement, null);
      }
   }

   public boolean actualizarUsuario(UsuarioModel usuario, int userId) {
      Connection connection = null;
      PreparedStatement statement = null;

      try {
         connection = dbConnection.getConexion();
         String query = "UPDATE USUARIO SET ID_UNIDAD=?, ID_ROL=?, NOMBRE=?, APELLIDO=?, DNI=?, USERNAME=?, RANGO=?, AREA=?, PASSWORD=?, ESTADO=?, OBSERVACIONES=? WHERE ID_GENDARME=?";
         statement = connection.prepareStatement(query);
         statement.setInt(1, usuario.getIdUnidad());
         statement.setInt(2, usuario.getIdRol());
         statement.setString(3, usuario.getNombre());
         statement.setString(4, usuario.getApellido());
         statement.setString(5, usuario.getDni());
         statement.setString(6, usuario.getUsername());
         statement.setString(7, usuario.getRango());
         statement.setString(8, usuario.getArea());
         statement.setBytes(9, usuario.getPassword());
         statement.setInt(10, usuario.getEstado());
         statement.setString(11, usuario.getObservaciones());
         statement.setInt(12, usuario.getIdGendarme());

         int rowsAffected = statement.executeUpdate();
         if (rowsAffected > 0) {
            logTransaction("Actualización", userId, usuario);
         }
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         closeResources(connection, statement, null);
      }
   }

   public boolean bajaUsuario(int idUsuario, int userId) {
      Connection connection = null;
      PreparedStatement statement = null;

      try {
         connection = dbConnection.getConexion();
         String query = "UPDATE USUARIO SET ESTADO=? WHERE ID_GENDARME=?";
         statement = connection.prepareStatement(query);
         statement.setInt(1, 0);  // Establece el estado a 0 (dar de baja)
         statement.setInt(2, idUsuario);  // Utiliza el ID del usuario que deseas dar de baja

         int rowsAffected = statement.executeUpdate();
         if (rowsAffected > 0) {
            logTransaction("Baja", userId, new UsuarioModel(idUsuario, 0, 0, "", "", "", "", "", "", null, 0, "", null));
         }
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         closeResources(connection, statement, null);
      }
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

   public List<UsuarioModel> buscarUsuariosActivos() throws SQLException {
      List<UsuarioModel> opcionesUsuarios = new ArrayList<>();

      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT ID_GENDARME, NOMBRE, APELLIDO, DNI FROM USUARIO";
         statement = connection.prepareStatement(query);
         resultSet = statement.executeQuery();

         while (resultSet.next()) {
            int idGendarme = resultSet.getInt("ID_GENDARME");
            String nombreGendarme = resultSet.getString("NOMBRE");
            String apellidoGendarme = resultSet.getString("APELLIDO");
            String dniGendarme = resultSet.getString("DNI");

            UsuarioModel usuario = new UsuarioModel(idGendarme, nombreGendarme, apellidoGendarme, dniGendarme);
            opcionesUsuarios.add(usuario);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return opcionesUsuarios;
   }


   public boolean verifyPassword(String username, String password) {
      boolean passwordCorrect = false;
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
         connection = dbConnection.getConexion();
         String query = "SELECT PASSWORD FROM USUARIO WHERE USERNAME = ?";
         statement = connection.prepareStatement(query);
         statement.setString(1, username);
         resultSet = statement.executeQuery();

         if (resultSet.next()) {
            String storedPassword = resultSet.getString("PASSWORD");
            passwordCorrect = storedPassword.equals(password); // Comparar directamente si no se usa hashing
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResources(connection, statement, resultSet);
      }

      return passwordCorrect;
   }

   public boolean updatePassword(int userId, String newPassword) {
      Connection connection = null;
      PreparedStatement statement = null;

      try {
         connection = dbConnection.getConexion();
         String query = "UPDATE USUARIO SET PASSWORD = ? WHERE ID_GENDARME = ?";
         statement = connection.prepareStatement(query);
         statement.setString(1, newPassword);  // Se debería encriptar la contraseña si se usa hashing
         statement.setInt(2, userId);

         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         closeResources(connection, statement, null);
      }
   }
}
