package com.e.registrifyv1.Controladores.Vehiculos;

import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class  AgregarVehiculoController implements Initializable {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txbIdVehiculo;
    @FXML
    private TextField txbIdGendarme;
    @FXML
    private TextField txbIdUnidad;
    @FXML
    private TextField txbTipoVehiculo;
    @FXML
    private TextField txbMarcaVehiculo;
    @FXML
    private TextField txbKilometrajeVehiculo;
    @FXML
    private TextField txbPatenteVehiculo;
    @FXML
    private TextField txbModeloVehiculo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmarButton(ActionEvent event) {

        int idVehiculo = Integer.parseInt(txbIdVehiculo.getText());
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        String tipoVehiculo = txbTipoVehiculo.getText();
        String marcaVehiculo = txbMarcaVehiculo.getText();
        String modeloVehiculo = txbModeloVehiculo.getText();
        String patenteVehiculo = txbPatenteVehiculo.getText();
        String kmVehiculo = txbKilometrajeVehiculo.getText();

        VehiculosModel nuevoVehiculo = new VehiculosModel(idVehiculo, idUnidad, idGendarme, tipoVehiculo, marcaVehiculo, modeloVehiculo, patenteVehiculo, kmVehiculo );

        nuevoVehiculo.setIdVehiculo(idVehiculo);
        nuevoVehiculo.setIdUnidad(idUnidad);
        nuevoVehiculo.setIdGendarme(idGendarme);
        nuevoVehiculo.setTipoVehiculo(tipoVehiculo);
        nuevoVehiculo.setMarcaVehiculo(marcaVehiculo);
        nuevoVehiculo.setModelo(modeloVehiculo);
        nuevoVehiculo.setPatente(patenteVehiculo);
        nuevoVehiculo.setKilometraje(kmVehiculo);


        VehiculoDAO vehiculoDAO1 = new VehiculoDAO();
        boolean carga = vehiculoDAO1.insertarVehiculo(nuevoVehiculo);
        mostrarMensaje(carga);
    }
    private void mostrarMensaje(boolean carga) {
        Alert alert = new Alert(carga ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(carga ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (carga) {
            alert.setContentText("El Vehiculo se insertó correctamente.");
            txbIdVehiculo.clear();
            txbIdGendarme.clear();
            txbIdUnidad.clear();
            txbTipoVehiculo.clear();
            txbMarcaVehiculo.clear();
            txbModeloVehiculo.clear();
            txbPatenteVehiculo.clear();
            txbKilometrajeVehiculo.clear();
        } else {
            alert.setContentText("No se pudo insertar el Vehiculo.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
}
