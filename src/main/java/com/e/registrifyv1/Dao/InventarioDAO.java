package com.e.registrifyv1.Dao;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class InventarioDAO {
    private DBConnection dbConnection;//

    public InventarioDAO() { dbConnection = new DBConnection();   }

    public ObservableList<InventarioModel> buscarInventario(String valor)//
    {
        ObservableList<InventarioModel> inventarios = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();

            String query = "SELECT * FROM ACCESORIO WHERE (LOWER(ID_ACCESORIO) = LOWER(?) OR LOWER(ID_UNIDAD) LIKE LOWER(?) OR LOWER(ID_GENDARME) LIKE LOWER(?) OR LOWER(NUMEROSERIE_ACCESORIO) LIKE LOWER(?)  OR LOWER(TIPO_ACCESORIO) LIKE LOWER(?))";

            statement = connection.prepareStatement(query);
            //habria que agregar todos los datos?

            statement.setString(1, valor);
            statement.setString(2, "%" + valor + "%");
            statement.setString(3, "%" + valor + "%");
            statement.setString(4, "%" + valor + "%");
            statement.setString(5, "%" + valor + "%");


            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAccesorio = resultSet.getInt("ID_ACCESORIO");
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                int idGendarme = resultSet.getInt("ID_GENDARME");
                String nombreAccesorio = resultSet.getString("NUMEROSERIE_ACCESORIO");
                String descrAccesorio = resultSet.getString("TIPO_ACCESORIO");

                InventarioModel inventario = new InventarioModel(idAccesorio, idUnidad, idGendarme, nombreAccesorio, descrAccesorio);
                inventarios.add(inventario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return inventarios;
    }

    public boolean insertarInventario(InventarioModel inventario) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "INSERT INTO ACCESORIO (ID_ACCESORIO, ID_UNIDAD, ID_GENDARME, NUMEROSERIE_ACCESORIO, TIPO_ACCESORIO) VALUES (?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(query);
            statement.setInt(1, inventario.getIdAccesorio());
            statement.setInt(2, inventario.getIdUnidad());
            statement.setInt(3, inventario.getIdGendarme());
            statement.setString(4, inventario.getNombreAccesorio());
            statement.setString(5, inventario.getDescrAccesorio());

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

}

