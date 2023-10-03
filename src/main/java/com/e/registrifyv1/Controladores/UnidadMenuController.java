package com.e.registrifyv1.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UnidadMenuController {
    @FXML
    public void menuUnidad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/UnidadView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar unidad");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button btnSalir;
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

}
