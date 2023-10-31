package com.e.registrifyv1.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

      if (actionEvent.getSource() == btnVehiculos) {
         loadStage("/View/Vehiculos/VehiculosMenuView.fxml", "Menu Vehiculos");
      }

      if (actionEvent.getSource() == btnUnidad) {
         loadStage("/View/Unidad/UnidadMenuView.fxml", "Menu Unidad");
      }

      if(actionEvent.getSource() == btnArmas){
         loadStage("/View/Arma/ArmaMenuView.fxml", "Menu Arma");
      }

      if(actionEvent.getSource() == btnEventosDiarios){
         loadStage("/View/EventoDiario/EventoDiarioView.fxml", "Menu Evento");
      }
   }


   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      // Asignar eventos de teclado para las teclas F1-F6
      btnUsuarios.setOnKeyPressed(this::handleFKey);
      btnVehiculos.setOnKeyPressed(this::handleFKey);
      btnUnidad.setOnKeyPressed(this::handleFKey);
      btnArmas.setOnKeyPressed(this::handleFKey);
      btnEventosDiarios.setOnKeyPressed(this::handleFKey);
   }

   private void handleFKey(KeyEvent event) {
      if (event.getCode() == KeyCode.F1) {
         loadStage("/View/Usuarios/UsuarioMenuView.fxml", "Menu Usuarios");
      } else if (event.getCode() == KeyCode.F2) {
         loadStage("/View/Vehiculos/VehiculosMenuView.fxml", "Menu Vehiculos");
      } else if (event.getCode() == KeyCode.F3) {
         loadStage("/View/Unidad/UnidadMenuView.fxml", "Menu Unidad");
      } else if (event.getCode() == KeyCode.F4) {
         loadStage("/View/Arma/ArmaMenuView.fxml", "Menu Arma");
      } else if (event.getCode() == KeyCode.F5) {
         loadStage("/View/EventoDiario/EventoDiarioView.fxml", "Menu Evento");
      } else if (event.getCode() == KeyCode.F6) {
         // Agregar aquí la lógica para la tecla F6 si es necesario
      }
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


