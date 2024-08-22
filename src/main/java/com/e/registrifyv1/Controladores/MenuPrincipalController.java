package com.e.registrifyv1.Controladores;

import com.e.registrifyv1.Controladores.Usuario.EditarUsuarioFormController;
import com.e.registrifyv1.Modelos.Rol.Rol;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
   private Map<String, Stage> ventanasAbiertas = new HashMap<>();
   private Timeline sessionTimeoutTimeline;
   private boolean isSesionCerrada = false; // Bandera para controlar el estado de la sesión

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
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

      iniciarMonitoreoInactividad();
   }

   private void configurarAccesosPorRol(int idRol) {
      btnUsuarios.setDisable(true);
      btnArmas.setDisable(true);
      btnEventosDiarios.setDisable(true);
      btnVehiculos.setDisable(true);
      btnInventario.setDisable(true);
      btnUnidad.setDisable(true);

      switch (idRol) {
         case Rol.ADMINISTRADOR:
            btnUsuarios.setDisable(false);
            btnArmas.setDisable(false);
            btnEventosDiarios.setDisable(false);
            btnVehiculos.setDisable(false);
            btnInventario.setDisable(false);
            btnUnidad.setDisable(false);
            break;
         case Rol.SUPERVISOR:
            btnArmas.setDisable(false);
            btnEventosDiarios.setDisable(false);
            btnVehiculos.setDisable(false);
            btnInventario.setDisable(false);
            btnUnidad.setDisable(false);
            break;
         case Rol.USUARIO:
            btnEventosDiarios.setDisable(false);
            btnVehiculos.setDisable(false);
            break;
      }
   }

   @FXML
   private void handleButtonClick(ActionEvent actionEvent) {
      Button botonPresionado = (Button) actionEvent.getSource();
      String[] vista = botonesVistas.get(botonPresionado);

      if (vista == null) {
         return;
      }

      String fxmlPath = vista[0]; // Usar el path del FXML como clave única

      if (ventanasAbiertas.containsKey(fxmlPath)) {
         // Si la ventana ya está abierta, no permitir abrir otra
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Información");
         alert.setHeaderText(null);
         alert.setContentText("El menú ya está abierto.");
         alert.show();
         return;
      }

      if (botonPresionado.isDisabled()) {
         mostrarAlertaAccesoDenegado();
      } else {
         Stage stage = loadStage(fxmlPath, vista[1]);

         if (stage != null) {
            // Almacenar la ventana abierta en el Map antes de mostrarla
            ventanasAbiertas.put(fxmlPath, stage);

            // Agregar un listener para removerla cuando se cierre
            stage.setOnCloseRequest(e -> ventanasAbiertas.remove(fxmlPath));

            // Mostrar la ventana después de haberla almacenado y configurado
            stage.show();
         }
      }

      reiniciarTemporizadorInactividad();
   }

   private void mostrarAlertaAccesoDenegado() {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Acceso Denegado");
      alert.setHeaderText(null);
      alert.setContentText("No tienes los permisos necesarios para acceder a esta funcionalidad.");
      alert.showAndWait();
   }

   @FXML
   private void editarUsuario(ActionEvent event) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/EditarUsuario.fxml"));
         Parent root = loader.load();
         EditarUsuarioFormController controller = loader.getController();
         UsuarioModel usuarioLogueado = Session.getUsuarioLogueado();
         controller.setDatosUsuario(
                 usuarioLogueado.getNombre(),
                 usuarioLogueado.getApellido(),
                 usuarioLogueado.getDni(),
                 usuarioLogueado.getRango(),
                 usuarioLogueado.getIdUnidad()
         );
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle("Editar Usuario");
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @FXML
   private void cerrarSesion(ActionEvent event) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Cerrar sesión");
      alert.setHeaderText(null);
      alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
         realizarCierreSesion(false);
      }
   }

   private void iniciarMonitoreoInactividad() {
      sessionTimeoutTimeline = new Timeline(new KeyFrame(Duration.seconds(500), new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            realizarCierreSesion(true);
         }
      }));
      sessionTimeoutTimeline.setCycleCount(1); // Se establece en 1 para que se ejecute solo una vez después de 30 segundos
      sessionTimeoutTimeline.play();
   }

   private void reiniciarTemporizadorInactividad() {
      if (sessionTimeoutTimeline != null) {
         sessionTimeoutTimeline.stop();
         sessionTimeoutTimeline.playFromStart(); // Reinicia el temporizador desde el inicio
      }
   }

   private void realizarCierreSesion(boolean porInactividad) {
      if (!isSesionCerrada) {
         isSesionCerrada = true; // Marcar la sesión como cerrada

         if (porInactividad) {
            Platform.runLater(() -> {
               Alert inactividadAlert = new Alert(Alert.AlertType.INFORMATION);
               inactividadAlert.setTitle("Sesión cerrada");
               inactividadAlert.setHeaderText(null);
               inactividadAlert.setContentText("La sesión se ha cerrado automáticamente debido a la inactividad.");
               inactividadAlert.showAndWait();
               cerrarVentanas();
            });
         } else {
            cerrarVentanas();
         }
      }
   }

   private void cerrarVentanas() {
      Session.cerrarSesion();
      Stage primaryStage = (Stage) menuBar.getScene().getWindow();

      List<Stage> stagesToClose = new ArrayList<>();
      for (Window window : Stage.getWindows()) {
         if (window instanceof Stage && window != primaryStage) {
            stagesToClose.add((Stage) window);
         }
      }
      for (Stage stage : stagesToClose) {
         stage.close();
      }

      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
         Parent root = loader.load();
         Stage loginStage = new Stage();
         loginStage.setScene(new Scene(root));
         loginStage.setTitle("Login");
         loginStage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }

      primaryStage.close();
   }

   private Stage loadStage(String fxml, String title) {
      Stage stage = null;
      try {
         Parent root = FXMLLoader.load(getClass().getResource(fxml));
         stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle(title);
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return stage;
   }

}
