package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculoDAO {

    private DBConnection dbConnection;

    public VehiculoDAO() { dbConnection = new DBConnection();   }

    public ObservableList<VehiculosModel> buscarVehiculo(String valor)
    {
        ObservableList<VehiculosModel> vehiculos = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();

            String query = "SELECT * FROM VEHICULO WHERE (LOWER(ID_VEHICULO) = LOWER(?) OR LOWER(MARCA_VEHICULO) LIKE LOWER(?) OR LOWER(MODELO) LIKE LOWER(?) OR LOWER(PATENTE) LIKE LOWER(?))";

            statement = connection.prepareStatement(query);

            statement.setString(1, valor);
            statement.setString(2, "%" + valor + "%");
            statement.setString(3, "%" + valor + "%");
            statement.setString(4, "%" + valor + "%");

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idVehiculo = resultSet.getInt("ID_VEHICULO");
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                int idGendarme = resultSet.getInt("ID_GENDARME");
                String tipoVehiculo = resultSet.getString("TIPO_VEHICULO");
                String marcaVehiculo = resultSet.getString("MARCA_VEHICULO");
                String modelo = resultSet.getString("MODELO");
                int kilometraje = resultSet.getInt("KILOMETRAJE");
                String patente = resultSet.getString("PATENTE");

                VehiculosModel vehiculo = new VehiculosModel(idVehiculo, idUnidad, idGendarme, tipoVehiculo, marcaVehiculo, modelo, kilometraje, patente);
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return vehiculos;
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

    // Agrega métodos para insertar, modificar y dar de baja vehículos según tus necesidades
}
