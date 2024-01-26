package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AgregarArmaController implements Initializable {
    
    @FXML
    private Button btnCancelar;


    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txbIdGendarme;

    @FXML
    private TextField txbIdUnidad;

    @FXML
    private TextField txbMarcaArma;

    @FXML
    private TextField txbTipoArma;

    @FXML
    private TextField txbNumeroSerieArma;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmarButton(ActionEvent event) {

        int idArma = 0;
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        String marcaArma = txbMarcaArma.getText();
        String tipoArma = txbTipoArma.getText();
        String numeroSerieArma = txbNumeroSerieArma.getText();

        ArmaMenuModel nuevaArma = new ArmaMenuModel(idArma, idGendarme, idUnidad, marcaArma, tipoArma, numeroSerieArma);

        nuevaArma.setIdArma(idArma);
        nuevaArma.setIdGendarme(idGendarme);
        nuevaArma.setIdUnidad(idUnidad);
        nuevaArma.setMarcaArma(marcaArma);
        nuevaArma.setTipoArma(tipoArma);
        nuevaArma.setNumeroSerieArma(numeroSerieArma);

        ArmaDAO armaDAO1 = new ArmaDAO();
        boolean exito = armaDAO1.insertarArma(nuevaArma);

        mostrarMensaje(exito);

    }

    private void mostrarMensaje(boolean exito) {
        Alert alert = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(exito ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (exito) {
            alert.setContentText("El arma se insertó correctamente.");
            txbIdGendarme.clear();
            txbIdUnidad.clear();
            txbMarcaArma.clear();
            txbTipoArma.clear();
            txbNumeroSerieArma.clear();
        } else {
            alert.setContentText("No se pudo insertar el arma.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
}
