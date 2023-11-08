package com.e.registrifyv1.Controladores.Unidad;

import com.e.registrifyv1.Dao.UnidadDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AgregarUnidadController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtNombreUnidad;

    @FXML
    private TextField txtUbicacionUnidad;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleConfirmarButton(ActionEvent event) {
        int idUnidad = 0;
        String nombreUnidad = txtNombreUnidad.getText();
        String ubicacionUnidad = txtUbicacionUnidad.getText();

        UnidadMenuModel nuevaUnidad = new UnidadMenuModel(idUnidad, nombreUnidad, ubicacionUnidad);

        nuevaUnidad.setIdUnidad(idUnidad);
        nuevaUnidad.setNombreUnidad(nombreUnidad);
        nuevaUnidad.setUbicacionUnidad(ubicacionUnidad);

        // Llamar al método insertarUsuario en UsuarioDAO
        UnidadDAO unidadDAO1 = new UnidadDAO();
        boolean exito = unidadDAO1.insertarUnidad(nuevaUnidad);

        mostrarMensaje(exito);
    }

    private void mostrarMensaje(boolean exito) {
        Alert alert = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(exito ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (exito) {
            alert.setContentText("La unidad se insertó correctamente.");
            txtNombreUnidad.clear();
            txtUbicacionUnidad.clear();
        } else {
            alert.setContentText("No se pudo insertar la unidad.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }






}
