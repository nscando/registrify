package com.e.registrifyv1.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EventoDiarioController {
    @FXML
    private Button btnSalir;
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
}
