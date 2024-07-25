package com.e.registrifyv1.Dao;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
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
        ObservableList<InventarioModel> inventario = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnection.getConexion();

            String query = "SELECT ACCESORIO.ID_ACCESORIO, ACCESORIO.NUMEROSERIE_ACCESORIO, ACCESORIO.TIPO_ACCESORIO, "
                    + "USUARIO.NOMBRE AS NOMBRE_GENDARME, "
                    + "USUARIO.APELLIDO AS APELLIDO_GENDARME, "
                    + "USUARIO.DNI AS DNI_GENDARME, "
                    + "UNIDAD.NOMBRE_UNIDAD AS NOMBRE_UNIDAD "
                    + "FROM ACCESORIO "
                    + "LEFT JOIN USUARIO ON ACCESORIO.ID_GENDARME = USUARIO.ID_GENDARME "
                    + "LEFT JOIN UNIDAD ON ACCESORIO.ID_UNIDAD = UNIDAD.ID_UNIDAD "
                    + "WHERE (LOWER(ACCESORIO.ID_ACCESORIO) = LOWER(?) "
                    + "OR LOWER(USUARIO.NOMBRE) LIKE LOWER(?) "
                    + "OR LOWER(USUARIO.APELLIDO) LIKE LOWER(?) "
                    + "OR LOWER(USUARIO.DNI) LIKE LOWER(?) "
                    + "OR LOWER(UNIDAD.NOMBRE_UNIDAD) LIKE LOWER(?) "
                    + "OR LOWER(ACCESORIO.NUMEROSERIE_ACCESORIO) LIKE LOWER(?) "
                    + "OR LOWER(ACCESORIO.TIPO_ACCESORIO) LIKE LOWER(?))";

            statement = connection.prepareStatement(query);

            statement.setString(1, valor);
            statement.setString(2, "%" + valor + "%");
            statement.setString(3, "%" + valor + "%");
            statement.setString(4, "%" + valor + "%");
            statement.setString(5, "%" + valor + "%");
            statement.setString(6, "%" + valor + "%");
            statement.setString(7, "%" + valor + "%");

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAccesorio = resultSet.getInt("ID_ACCESORIO");
                String nombreAccesorio = resultSet.getString("NUMEROSERIE_ACCESORIO");
                String descrAccesorio = resultSet.getString("TIPO_ACCESORIO");
                String nombreGendarme = resultSet.getString("NOMBRE_GENDARME");
                String apellidoGendarme = resultSet.getString("APELLIDO_GENDARME");
                String dniGendarme = resultSet.getString("DNI_GENDARME");
                String nombreUnidad = resultSet.getString("NOMBRE_UNIDAD");

                InventarioModel item = new InventarioModel(idAccesorio, nombreUnidad, nombreGendarme, apellidoGendarme, dniGendarme, nombreAccesorio, descrAccesorio);
                inventario.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return inventario;
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

    public boolean actualizarInventario(InventarioModel inventario) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbConnection.getConexion();

            String query = "UPDATE ACCESORIO SET ID_UNIDAD=?, ID_GENDARME=?, NUMEROSERIE_ACCESORIO=?, TIPO_ACCESORIO=? WHERE ID_ACCESORIO=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, inventario.getIdUnidad());
            statement.setInt(2, inventario.getIdGendarme());
            statement.setString(3, inventario.getNombreAccesorio());
            statement.setString(4, inventario.getDescrAccesorio());
            statement.setInt(5, inventario.getIdAccesorio()); // Aquí se añade el quinto parámetro

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }
//todo corregir la baja. borra, pero no lo seleccionado
public boolean bajaInventario(int idAccesorio) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {

        // Verificar el valor de idAccesorio
        System.out.println("ID del Accesorio a eliminar: " + idAccesorio);
        connection = dbConnection.getConexion();
        String query = "DELETE FROM ACCESORIO WHERE ID_ACCESORIO=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, idAccesorio);  // Establece el estado a 0 (dar de baja)

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

}

