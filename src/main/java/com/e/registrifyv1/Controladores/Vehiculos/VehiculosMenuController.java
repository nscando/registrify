package com.e.registrifyv1.Controladores.Vehiculos;

import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
// acabo de agregar estos 2 import
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
//wtf?


public class VehiculosMenuController {
    @FXML
    private Button btnSalir;
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void menuAgregarVehiculo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/AgregarVehiculoForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Vehiculo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
