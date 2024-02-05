package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnidadDAO {

    private DBConnection dbConnection;

    public UnidadDAO() {
        dbConnection = new DBConnection();
    }
/*

    public UnidadMenuModel getUnidadByNameAndLocation(String name, String location){
        UnidadMenuModel unidad = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();
            String query = "SELECT * FROM UNIDAD WHERE (LOWER(ID_UNIDAD) = LOWER(?) OR LOWER(NOMBRE_UNIDAD) LIKE LOWER(?) OR LOWER(UBICACION) LIKE LOWER(?))";

            statement = connection.prepareStatement(query);

            //statement.setInt(1, unidad.getIdUnidad());
            statement.setString(2, name);
            statement.setString(3, location);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                String nombreUnidad = resultSet.getString("NOMBRE_UNIDAD");
                String ubicacionUnidad = resultSet.getString("UNBICACION");

                unidad = new UnidadMenuModel(idUnidad, nombreUnidad, ubicacionUnidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return unidad;
    }
*/



    public  ObservableList<UnidadMenuModel>buscarUnidad(String valor) {
        //se quito dentro de los parametros que recibe este metodo, el "incluir baja".
        ObservableList<UnidadMenuModel> unidades = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();

            String query = "SELECT * FROM UNIDAD WHERE (LOWER(ID_UNIDAD) = LOWER(?) OR LOWER(NOMBRE_UNIDAD) LIKE LOWER(?) OR LOWER(UBICACION) LIKE LOWER(?))";


            statement = connection.prepareStatement(query);

            statement.setString(1, valor);
            statement.setString(2, "%" + valor + "%");
            statement.setString(3, "%" + valor + "%");

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                String nombreUnidad = resultSet.getString("NOMBRE_UNIDAD");
                String ubicacionUnidad = resultSet.getString("UBICACION");


                UnidadMenuModel unidad = new UnidadMenuModel(idUnidad, nombreUnidad, ubicacionUnidad);
                unidades.add(unidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return unidades;
    }

    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try{
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

    public boolean insertarUnidad(UnidadMenuModel unidad) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "INSERT INTO UNIDAD ( ID_UNIDAD, NOMBRE_UNIDAD, UBICACION) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, unidad.getIdUnidad());
            statement.setString(2, unidad.getNombreUnidad());
            statement.setString(3, unidad.getUbicacionUnidad());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public boolean modificarUnidad(UnidadMenuModel unidad) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();

            String query = "UPDATE UNIDAD SET NOMBRE_UNIDAD = ?, UBICACION = ? WHERE ID_UNIDAD = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, unidad.getNombreUnidad());
            statement.setString(2, unidad.getUbicacionUnidad());
            statement.setInt(3, unidad.getIdUnidad());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public boolean bajaUnidad(int idUnidad) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "DELETE FROM UNIDAD WHERE ID_UNIDAD=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idUnidad);  // Establece el estado a 0 (dar de baja)

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
