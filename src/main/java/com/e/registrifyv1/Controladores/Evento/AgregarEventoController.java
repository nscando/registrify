package com.e.registrifyv1.Controladores.Evento;

import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class  AgregarEventoController implements Initializable {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txbIdGendarme;
    @FXML
    private TextField txbIdUnidad;
    @FXML
    private TextField txbDescripcionEvento;
    @FXML
    private TextField txbFechaEvento;
    @FXML
    private TextField txbEstadoEvento;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmarButton(ActionEvent event) {

        int idEvento = 0;
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        String descripcion = txbDescripcionEvento.getText();
        String fecha = txbFechaEvento.getText();
        int estado = Integer.parseInt(txbEstadoEvento.getText());

        EventoDiarioModel nuevoEvento = new EventoDiarioModel(idEvento, idUnidad, idGendarme, descripcion, fecha, estado);

        nuevoEvento.setIdEvento(idEvento);
        nuevoEvento.setIdUnidad(idUnidad);
        nuevoEvento.setIdGendarme(idGendarme);
        nuevoEvento.setDescrEvento(descripcion);
        nuevoEvento.setFechaEvento(fecha);
        nuevoEvento.setEstado(estado);



        EventoDAO eventoDAO1 = new EventoDAO();
        boolean carga = eventoDAO1.insertarEvento(nuevoEvento);
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
            txbDescripcionEvento.clear();
            txbFechaEvento.clear();
            txbEstadoEvento.clear();


        } else {
            alert.setContentText("No se pudo insertar el Objeto.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
}
