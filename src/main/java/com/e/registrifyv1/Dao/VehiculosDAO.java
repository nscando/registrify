package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Unidad.UnidadModel;
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

public class VehiculosDAO {
//corregir
    private DBConnection dbConnection;

    public VehiculosDAO() {
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
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");
                String username1 = resultSet.getString("USERNAME");
                String rango = resultSet.getString("RANGO");
                String area = resultSet.getString("AREA");
                String observaciones = resultSet.getString("OBSERVACIONES");
                byte[] passwordBytes = resultSet.getBytes("PASSWORD");
                int estado = resultSet.getInt("ESTADO");
                int dni = resultSet.getInt("DNI");

                usuario = new UsuarioModel( idUnidad, idRol, nombre, apellido, dni, username1, rango, area, passwordBytes, estado, observaciones);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return usuario;
    }

    public ObservableList<UsuarioModel> buscarUsuarios(String valor) {
        ObservableList<UsuarioModel> usuarios = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();
            String query = "SELECT * FROM USUARIO WHERE LOWER(ID_GENDARME) = LOWER(?) OR LOWER(NOMBRE) LIKE LOWER(?) OR LOWER(APELLIDO) LIKE LOWER(?) OR LOWER(DNI) = LOWER(?)";
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
                int dni = resultSet.getInt("DNI");
                String username = resultSet.getString("USERNAME");
                String rango = resultSet.getString("RANGO");
                String area = resultSet.getString("AREA");
                byte[] password = resultSet.getBytes("PASSWORD");
                int estado = resultSet.getInt("ESTADO");
                String observaciones = resultSet.getString("OBSERVACIONES");

                UsuarioModel Vehiculo = new UsuarioModel( idUnidad, idRol, nombre, apellido, dni, username, rango, area, password, estado, observaciones);
                usuarios.add(Vehiculo);

                // cambiar  a los datos de el vehiculo, DB, ta hecho? / hacer
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return usuarios;
    }

    public List<UnidadModel> obtenerOpcionesUnidad() throws SQLException {
        List<UnidadModel> opcionesUnidad = new ArrayList<>();

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

                UnidadModel unidad = new UnidadModel(idUnidad, nombreUnidad, ubicacion);
                opcionesUnidad.add(unidad);
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
