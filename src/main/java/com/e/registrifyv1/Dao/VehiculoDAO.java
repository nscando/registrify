package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
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
            //habria que agregar todos los datos?

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
                String patente = resultSet.getString("PATENTE");
                String kilometraje = resultSet.getString("KILOMETRAJE");

                VehiculosModel vehiculo = new VehiculosModel(idVehiculo, idUnidad, idGendarme, tipoVehiculo, marcaVehiculo, modelo, patente, kilometraje);
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return vehiculos;
    }

    public boolean insertarVehiculo(VehiculosModel vehiculo) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "INSERT INTO VEHICULO (ID_VEHICULO, ID_UNIDAD, ID_GENDARME, TIPO_VEHICULO, MARCA_VEHICULO, MODELO, PATENTE, KILOMETRAJE) VALUES (?,?,?,?,?,?,?,?)";

            statement = connection.prepareStatement(query);

            statement.setInt(1, vehiculo.getIdVehiculo());
            statement.setInt(2, vehiculo.getIdUnidad());
            statement.setInt(3, vehiculo.getIdGendarme());
            statement.setString(4, vehiculo.getTipoVehiculo());
            statement.setString(5, vehiculo.getMarcaVehiculo());
            statement.setString(6, vehiculo.getModelo());
            statement.setString(7, vehiculo.getPatente());
            statement.setString(8, vehiculo.getKilometraje());
            //cambie el tipo de dato a STRING, antes era INT
            int rowsAffected = statement.executeUpdate();
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


    //esto lo vamos a usar en el controlador de el m,odificador de vehiculo(el cual hay que crear).
    public boolean actualizarVehiculo(VehiculosModel vehiculo) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();

            String query = "UPDATE VEHICULO SET ID_UNIDAD=?, ID_GENDARME=?, TIPO_VEHICULO=?, MARCA_VEHICULO=?, MODELO=?, PATENTE=?,KILOMETRAJE=?, WHERE ID_VEHICULO=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, vehiculo.getIdUnidad());
            statement.setInt(2, vehiculo.getIdGendarme());
            statement.setString(3, vehiculo.getTipoVehiculo());
            statement.setString(4, vehiculo.getMarcaVehiculo());
            statement.setString(5, vehiculo.getModelo());
            statement.setString(6, vehiculo.getPatente());
            statement.setString(7, vehiculo.getKilometraje());


            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }

    }
    //esto va a ser utilizado en el controlador "VehiculosMenuController"
    public boolean bajaVehiculo(int idVehiculo) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "DELETE FROM VEHICULO WHERE ID_VEHICULO=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idVehiculo);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }
 // 22/05/24
}
