package com.e.registrifyv1.Dao;

import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import com.e.registrifyv1.Utiles.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class EventoDAO {
    private DBConnection dbConnection;//

    public EventoDAO() { dbConnection = new DBConnection();   }

    public ObservableList<EventoDiarioModel> buscarEvento(String valor) {
        ObservableList<EventoDiarioModel> eventos = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();

            String query = "SELECT e.ID_EVENTO, e.ID_UNIDAD, e.ID_GENDARME, "
                    + "u.NOMBRE_UNIDAD AS NOMBRE_UNIDAD, "
                    + "g.NOMBRE AS NOMBRE_GENDARME, "
                    + "g.APELLIDO AS APELLIDO_GENDARME, "
                    + "g.DNI AS DNI_GENDARME, "
                    + "e.DESCRIPCION_EVENTO AS DESCRIPCION_EVENTO, "
                    + "e.FECHAEVENTO AS FECHAEVENTO, "
                    + "e.ESTADO AS ESTADO "
                    + "FROM EVENTODIARIO e "
                    + "LEFT JOIN UNIDAD u ON e.ID_UNIDAD = u.ID_UNIDAD "
                    + "LEFT JOIN USUARIO g ON e.ID_GENDARME = g.ID_GENDARME "
                    + "WHERE (LOWER(e.ID_EVENTO) = LOWER(?) "
                    + "OR LOWER(u.NOMBRE_UNIDAD) LIKE LOWER(?) "
                    + "OR LOWER(g.NOMBRE) LIKE LOWER(?) "
                    + "OR LOWER(g.APELLIDO) LIKE LOWER(?) "
                    + "OR LOWER(g.DNI) LIKE LOWER(?) "
                    + "OR LOWER(e.DESCRIPCION_EVENTO) LIKE LOWER(?) "
                    + "OR LOWER(e.FECHAEVENTO) LIKE LOWER(?) "
                    + "OR LOWER(e.ESTADO) LIKE LOWER(?))";

            statement = connection.prepareStatement(query);

            // Configurar parámetros de búsqueda
            statement.setString(1, valor); // e.ID_EVENTO
            statement.setString(2, "%" + valor + "%"); // u.NOMBRE_UNIDAD
            statement.setString(3, "%" + valor + "%"); // g.NOMBRE
            statement.setString(4, "%" + valor + "%"); // g.APELLIDO
            statement.setString(5, "%" + valor + "%"); // g.DNI
            statement.setString(6, "%" + valor + "%"); // e.DESCRIPCION_EVENTO
            statement.setString(7, "%" + valor + "%"); // e.FECHAEVENTO
            statement.setString(8, "%" + valor + "%"); // e.ESTADO

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idEvento = resultSet.getInt("ID_EVENTO");
                int idUnidad = resultSet.getInt("ID_UNIDAD");
                int idGendarme = resultSet.getInt("ID_GENDARME");
                String nombreUnidad = resultSet.getString("NOMBRE_UNIDAD");
                String nombreGendarme = resultSet.getString("NOMBRE_GENDARME");
                String apellidoGendarme = resultSet.getString("APELLIDO_GENDARME");
                String dniGendarme = resultSet.getString("DNI_GENDARME");
                String descrEvento = resultSet.getString("DESCRIPCION_EVENTO");
                String fechaEvento = resultSet.getString("FECHAEVENTO");
                int estado = resultSet.getInt("ESTADO");

                EventoDiarioModel evento = new EventoDiarioModel(idEvento, idUnidad, idGendarme, descrEvento, fechaEvento, estado);
                evento.setNombreUnidad(nombreUnidad);
                evento.setNombreGendarme(nombreGendarme);
                evento.setApellidoGendarme(apellidoGendarme);
                evento.setDniGendarme(dniGendarme);
                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return eventos;
    }

    public boolean insertarEvento(EventoDiarioModel evento) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();
            String query = "INSERT INTO EVENTODIARIO (ID_EVENTO, ID_UNIDAD, ID_GENDARME, DESCRIPCION_EVENTO, FECHAEVENTO, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(query);
            statement.setInt(1, evento.getIdEvento());
            statement.setInt(2, evento.getIdUnidad());
            statement.setInt(3, evento.getIdGendarme());
            statement.setString(4, evento.getDescrEvento());
            statement.setString(5, evento.getFechaEvento());
            statement.setInt(6, evento.getEstado());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public boolean bajaEvento(int idEvento) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            // Verificar el valor de idAccesorio
            System.out.println("ID del Evento a eliminar: " + idEvento);
            connection = dbConnection.getConexion();
            String query = "DELETE FROM EVENTODIARIO WHERE ID_EVENTO=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idEvento);

            // Verificar la consulta generada
            System.out.println("Consulta generada: " + statement.toString());

            int rowsAffected = statement.executeUpdate();

            // Verificar cuántas filas fueron afectadas
            System.out.println("Filas afectadas: " + rowsAffected);
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

    public boolean actualizarEventoDiario(EventoDiarioModel evento) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();

            String query = "UPDATE EVENTODIARIO SET ID_UNIDAD=?, ID_GENDARME=?, DESCRIPCION_EVENTO=?, FECHAEVENTO=?, ESTADO=? WHERE ID_EVENTO=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, evento.getIdUnidad());
            statement.setInt(2, evento.getIdGendarme());
            statement.setString(3, evento.getDescrEvento());
            statement.setString(4, evento.getFechaEvento());
            statement.setInt(5, evento.getEstado());
            statement.setInt(6, evento.getIdEvento());

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








