package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArmaDAO {
    private DBConnection dbConnection; //Conexion a la Base de datos

    public ArmaDAO(){
        dbConnection = new DBConnection();
    }

    public ObservableList<ArmaMenuModel> buscarArma(String valor) {

        //se quito dentro de los parametros que recibe este metodo, el "incluir baja".

        ObservableList<ArmaMenuModel> armas = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = dbConnection.getConexion();

            String query = "SELECT * FROM ARMA WHERE (LOWER(ID_ARMA) = LOWER(?) OR LOWER(ID_GENDARME) LIKE LOWER(?) OR LOWER(ID_UNIDAD) LIKE LOWER(?) OR LOWER(MARCA_ARMA) LIKE LOWER(?) OR LOWER(TIPO_ARMA) LIKE LOWER(?) OR LOWER(NUMSERIE_ARMA) LIKE LOWER(?))";


            statement = connection.prepareStatement(query);

            statement.setString(1, valor);
            statement.setString(2, "%" + valor + "%");
            statement.setString(3, "%" + valor + "%");
            statement.setString(4, "%" + valor + "%");
            statement.setString(5, "%" + valor + "%");
            statement.setString(6, "%" + valor + "%");


            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int idArma = resultSet.getInt("ID_ARMA");
                int idGendarme = resultSet.getInt("ID_GENDARME");
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                String marcaArma = resultSet.getString("MARCA_ARMA");
                String tipoArma = resultSet.getString("TIPO_ARMA");
                String numeroSerieArma = resultSet.getString("NUMSERIE_ARMA");

                ArmaMenuModel arma = new ArmaMenuModel(idArma, idGendarme, idUnidad, marcaArma, tipoArma, numeroSerieArma);

                armas.add(arma);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return armas;
    }

    public boolean insertarArma(ArmaMenuModel arma){

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "INSERT INTO ARMA (ID_ARMA, ID_GENDARME, ID_UNIDAD, MARCA_ARMA, TIPO_ARMA, NUMSERIE_ARMA) VALUES (?,?,?,?,?,?)";

            statement = connection.prepareStatement(query);

            statement.setInt(1, arma.getIdArma());
            statement.setInt(2, arma.getIdGendarme());
            statement.setInt(3, arma.getIdUnidad());
            statement.setString(4, arma.getMarcaArma());
            statement.setString(5, arma.getTipoArma());
            statement.setString(6, arma.getNumeroSerieArma());
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

    public boolean actualizarArma(ArmaMenuModel arma) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();

            String query = "UPDATE ARMA SET ID_GENDARME=?, ID_UNIDAD=?, MARCA_ARMA=?, TIPO_ARMA=?, NUMSERIE_ARMA=? WHERE ID_ARMA=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, arma.getIdGendarme());
            statement.setInt(2, arma.getIdUnidad());
            statement.setString(3, arma.getMarcaArma());
            statement.setString(4, arma.getTipoArma());
            statement.setString(5, arma.getNumeroSerieArma());
            statement.setInt(6, arma.getIdArma());


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
