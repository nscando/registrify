package com.e.registrifyv1.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPrincipalController implements Initializable {

   @FXML
   private Button btnArmas;

   @FXML
   private Button btnEventosDiarios;

   @FXML
   private Button btnInventario;

   @FXML
   private Button btnUnidad;

   @FXML
   private Button btnUsuarios;

   @FXML
   private Button btnVehiculos;


   @FXML
   private void handleButtonClick(ActionEvent actionEvent) {
      if (actionEvent.getSource() == btnUsuarios) {
         loadStage("/View/Usuarios/UsuarioMenuView.fxml", "Menu Usuarios");
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

   }

   private void loadStage(String fxml, String title) {
      try {
         Parent root = FXMLLoader.load(getClass().getResource(fxml));
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle(title);
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}


