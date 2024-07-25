package com.e.registrifyv1.Controladores.Inventario;

import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class  AgregarInventarioController implements Initializable {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txbIdAccesorio;
    @FXML
    private TextField txbIdUnidad;
    @FXML
    private TextField txbIdGendarme;
    @FXML
    private TextField txbNombre;
    @FXML
    private TextField txbDescripcion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmarButton(ActionEvent event) {

        int idAccesorio = 0;
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        String nombre = txbNombre.getText();
        String descripcion = txbDescripcion.getText();

        InventarioModel nuevoInventario = new InventarioModel(idAccesorio, idUnidad, idGendarme, nombre, descripcion);

        nuevoInventario.setIdAccesorio(idAccesorio);
        nuevoInventario.setIdUnidad(idUnidad);
        nuevoInventario.setIdGendarme(idGendarme);
        nuevoInventario.setNombreAccesorio(nombre);
        nuevoInventario.setDescrAccesorio(descripcion);


        InventarioDAO inventarioDAO1 = new InventarioDAO();
        boolean carga = inventarioDAO1.insertarInventario(nuevoInventario);
        mostrarMensaje(carga);
    }
    private void mostrarMensaje(boolean carga) {
        Alert alert = new Alert(carga ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(carga ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (carga) {
            alert.setContentText("El Objeto se insertó correctamente.");
            txbIdUnidad.clear();
            txbIdGendarme.clear();
            txbNombre.clear();
            txbDescripcion.clear();

        } else {
            alert.setContentText("No se pudo insertar el Objeto.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
}
