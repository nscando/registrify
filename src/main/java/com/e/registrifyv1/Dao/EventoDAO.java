package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class EventoDAO {
    private DBConnection dbConnection;//

    public EventoDAO() { dbConnection = new DBConnection();   }

    public ObservableList<EventoDiarioModel> buscarEvento(String valor)//
    {
        ObservableList<EventoDiarioModel> eventos = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();

            String query = "SELECT * FROM EVENTODIARIO WHERE (LOWER(ID_EVENTO) = LOWER(?) OR LOWER(ID_UNIDAD) LIKE LOWER(?) OR LOWER(ID_GENDARME) LIKE LOWER(?) OR LOWER(FECHAEVENTO) LIKE LOWER(?) OR LOWER(ESTADO) LIKE LOWER(?))";

            statement = connection.prepareStatement(query);
            //habria que agregar todos los datos?

            statement.setString(1, valor);
            statement.setString(2, "%" + valor + "%");
            statement.setString(3, "%" + valor + "%");
            statement.setString(4, "%" + valor + "%");
            statement.setString(5, "%" + valor + "%");


            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idEvento = resultSet.getInt("ID_EVENTO");
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                int idGendarme = resultSet.getInt("ID_GENDARME");
                String descrEvento = resultSet.getString("DESCRIPCION_EVENTO");
                Date fechaEvento = resultSet.getString("FECHAEVENTO");
                int estado = resultSet.getInt("ESTADO");

                //todo, funciona??
                //String fechaEvento = String.valueOf(resultSet.getDate("FECHAEVENTO"));// funciona??/convertir a string

                EventoDiarioModel evento = new EventoDiarioModel(idEvento, idUnidad, idGendarme,  descrEvento, fechaEvento, estado);
                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return eventos;
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








