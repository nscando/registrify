package com.e.registrifyv1.Controladores;

import com.e.registrifyv1.Modelos.Rol.Rol;
import com.e.registrifyv1.Utiles.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuPrincipalController implements Initializable {
   @FXML private Button btnArmas;
   @FXML private Button btnEventosDiarios;
   @FXML private Button btnInventario;
   @FXML private Button btnUnidad;
   @FXML private Button btnUsuarios;
   @FXML private Button btnVehiculos;
   @FXML private Label userNameLabel;
   @FXML private Label userRolLabel;
   @FXML private MenuBar menuBar;


   private Map<Button, String[]> botonesVistas;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      // Mapa que relaciona cada botón con su vista y título correspondientes
      botonesVistas = new HashMap<>();
      botonesVistas.put(btnUsuarios, new String[]{"/View/Usuarios/UsuarioMenuView.fxml", "Menu Usuarios"});
      botonesVistas.put(btnVehiculos, new String[]{"/View/Vehiculos/VehiculosMenuView.fxml", "Menu Vehiculos"});
      botonesVistas.put(btnUnidad, new String[]{"/View/Unidad/UnidadMenuView.fxml", "Menu Unidad"});
      botonesVistas.put(btnArmas, new String[]{"/View/Arma/ArmaMenuView.fxml", "Menu Arma"});
      botonesVistas.put(btnEventosDiarios, new String[]{"/View/EventoDiario/EventoDiarioView.fxml", "Menu Evento"});
      botonesVistas.put(btnInventario, new String[]{"/View/Inventario/InventarioMenuView.fxml", "Menu Inventario"});

      if (Session.isSesionIniciada()) {
         userNameLabel.setText(Session.getNombreUsuario() + " " + Session.getApellidoUsuario());
         userRolLabel.setText(Rol.getDescripcionRol(Session.getIdRol()));
         configurarAccesosPorRol(Session.getIdRol());
      }
   }

   private void configurarAccesosPorRol(int idRol) {
      // Por defecto, se desactivan todos los accesos
      btnUsuarios.setDisable(true);
      btnArmas.setDisable(true);
      btnEventosDiarios.setDisable(true);
      btnVehiculos.setDisable(true);
      btnInventario.setDisable(true);
      btnUnidad.setDisable(true);

      // Activar accesos basados en el rol
      switch (idRol) {
         case Rol.ADMINISTRADOR:
            // El administrador tiene acceso a todo
            btnUsuarios.setDisable(false);
            btnArmas.setDisable(false);
            btnEventosDiarios.setDisable(false);
            btnVehiculos.setDisable(false);
            btnInventario.setDisable(false);
            btnUnidad.setDisable(false);
            break;
         case Rol.SUPERVISOR:
            // El supervisor tiene acceso a todo menos al menú de usuarios
            btnArmas.setDisable(false);
            btnEventosDiarios.setDisable(false);
            btnVehiculos.setDisable(false);
            btnInventario.setDisable(false);
            btnUnidad.setDisable(false);
            break;
         case Rol.USUARIO:
            // El usuario solo tiene acceso a Eventos Diarios y Vehículos
            btnEventosDiarios.setDisable(false);
            btnVehiculos.setDisable(false);
            break;
      }
   }


   @FXML
   private void handleButtonClick(ActionEvent actionEvent) {
      Button botonPresionado = (Button) actionEvent.getSource();
      if (botonPresionado.isDisabled()) {
         mostrarAlertaAccesoDenegado();
      } else {
         String[] vista = botonesVistas.get(botonPresionado);
         if (vista != null) {
            loadStage(vista[0], vista[1]);
         }
      }
   }

   private void mostrarAlertaAccesoDenegado() {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Acceso Denegado");
      alert.setHeaderText(null);
      alert.setContentText("No tienes los permisos necesarios para acceder a esta funcionalidad.");
      alert.showAndWait();
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
      }
   }

   @FXML
   private void cerrarSesion(ActionEvent event) {
      // Crear el alert de confirmación
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Cerrar sesión");
      alert.setHeaderText(null);
      alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");

      // Esperar la respuesta del usuario
      Optional<ButtonType> result = alert.showAndWait();
      if (((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
         // El usuario presionó OK, cerrar sesión
         Session.cerrarSesion();

         // Cierra la ventana del menú principal
         Stage stageActual = (Stage) menuBar.getScene().getWindow();
         stageActual.close();

         // Abre la ventana de login
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
         } catch (IOException e) {
            e.printStackTrace();
         }
      } else {
         // no se hace nada
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


