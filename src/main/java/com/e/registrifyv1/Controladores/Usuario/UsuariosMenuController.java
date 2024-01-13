package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UsuariosMenuController {

   @FXML
   private Button btnSalir;

   @FXML
   private Button btnBaja;
   @FXML
   private TableView<UsuarioModel> tablaMenuUsuario;
   @FXML
   private TableColumn<UsuarioModel, Integer> idCol;
   @FXML
   private CheckBox cBoxIncluirUsuariosBaja;
   @FXML
   private TableColumn<UsuarioModel, String> nombreCol;
   @FXML
   private TableColumn<UsuarioModel, String> apellidoCol;
   @FXML
   private TableColumn<UsuarioModel, String> rangoCol;
   @FXML
   private TableColumn<UsuarioModel, String> observacionesCol;
   @FXML
   private TableColumn<UsuarioModel, Integer> dniCol;
   @FXML
   private TableColumn<UsuarioModel, String> usernameCol;
   @FXML
   private TableColumn<UsuarioModel, String> rolCol;
   @FXML
   private TableColumn<UsuarioModel, Integer> estadoCol;
   @FXML
   private TextField txtFieldMenuUsuario;

   private UsuarioDAO usuarioDAO;

   @FXML
   private void initialize() {
      usuarioDAO = new UsuarioDAO();
      configurarColumnas();
   }

   private void configurarColumnas() {
      nombreCol.setCellFactory(tc -> {
         TableCell<UsuarioModel, String> cell = new TableCell<UsuarioModel, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
               super.updateItem(item, empty);
               setText(empty ? null : item);
            }
         };
         cell.setOnMouseClicked(event -> {
            if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
               UsuarioModel rowData = cell.getTableView().getItems().get(cell.getIndex());
               abrirFormularioModificarUsuario(rowData);
            }
         });
         return cell;
      });

      tablaMenuUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         if (newValue != null) {
            abrirFormularioModificarUsuario(newValue);
         }
      });
   }

   @FXML
   private void handleSalirButtonAction(ActionEvent event) {
      Stage stage = (Stage) btnSalir.getScene().getWindow();
      stage.close();
   }

   @FXML
   private void btBuscarAction(ActionEvent event) {
      String valorBusqueda = txtFieldMenuUsuario.getText();
      boolean incluirBaja = cBoxIncluirUsuariosBaja.isSelected();

      ObservableList<UsuarioModel> usuarios = usuarioDAO.buscarUsuarios(valorBusqueda, incluirBaja);
      cargarUsuariosEnTableView(usuarios);
   }

   private void cargarUsuariosEnTableView(ObservableList<UsuarioModel> usuarios) {
      if (usuarios != null && !usuarios.isEmpty()) {
         tablaMenuUsuario.setItems(usuarios);
         idCol.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
         nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
         apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
         dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
         observacionesCol.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
         rangoCol.setCellValueFactory(new PropertyValueFactory<>("rango"));
         usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
         rolCol.setCellValueFactory(new PropertyValueFactory<>("idRol"));
         estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
      } else {
         tablaMenuUsuario.getItems().clear();
      }
   }

   @FXML
   private void abrirFormularioAgregarUsuario(ActionEvent event) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/AgregarUsuarioForm.fxml"));
         Parent root = loader.load();
         Stage stage = new Stage();
         stage.setTitle("Agregar Usuario");
         stage.setScene(new Scene(root));
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void abrirFormularioModificarUsuario(UsuarioModel usuario) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/ModificarUsuarioForm.fxml"));
         Parent root = loader.load();
         ModificarUsuarioFormController controller = loader.getController();
         controller.setTablaMenuUsuario(tablaMenuUsuario);
         controller.setUsuariosMenuController(this);
         controller.inicializarDatos(usuario);
         Stage stage = new Stage();
         stage.setTitle("Modificar Usuario");
         stage.setScene(new Scene(root));
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @FXML
   private void handleModificarUsuarioButtonAction(ActionEvent event) {
      UsuarioModel usuarioSeleccionado = tablaMenuUsuario.getSelectionModel().getSelectedItem();
      if (usuarioSeleccionado != null) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/ModificarUsuarioForm.fxml"));
            Parent root = (Parent) loader.load();
            ModificarUsuarioFormController controller = loader.getController();
            controller.inicializarDatos(usuarioSeleccionado);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
         } catch (IOException e) {
            e.printStackTrace();
         }
      } else {
         System.out.println("Por favor, seleccione un usuario para modificar.");
      }
   }


   @FXML
   private void handleBtnBajaClick(ActionEvent event) {
      // Obtén el usuario que se dará de baja (puedes obtenerlo de la tabla o de donde sea necesario)
      UsuarioModel usuarioSeleccionado = obtenerUsuarioSeleccionado();

      // Verifica si el usuario seleccionado no es nulo
      if (usuarioSeleccionado != null) {
         // Muestra el primer alert de advertencia
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Advertencia");
         alert.setHeaderText("¡Atención!");
         alert.setContentText("Estás a punto de dar de baja a un usuario. ¿Estás seguro?");
         alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

         Alert alert1 = new Alert(Alert.AlertType.WARNING);
         alert1.setTitle("Advertencia");
         alert1.setHeaderText("¡Atención!");
         alert1.setContentText("Entendes los riesgos de dar la BAJA de un USUARIO?. ¿Estás seguro?");
         alert1.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

         // Espera a la respuesta del primer alert
         Optional<ButtonType> result1 = alert.showAndWait();
         Optional<ButtonType> result2 = alert1.showAndWait();

         // Si se hace clic en "Sí" en el primer alert
         if (result1.isPresent() && result1.get() == ButtonType.YES) {
            // Utiliza el método de bajaUsuario de UsuarioDAO
            boolean bajaExitosa = usuarioDAO.bajaUsuario(usuarioSeleccionado.getIdGendarme());

            // Muestra el segundo alert de confirmación
            if (bajaExitosa) {
               Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
               alert2.setTitle("Confirmación");
               alert2.setHeaderText("¡Usuario dado de baja correctamente!");
               alert2.show();
               actualizarTableView();
               // Realiza acciones adicionales después de dar de baja si es necesario
               // ...
            } else {
               // Maneja el caso en que la baja no fue exitosa
               Alert alertError = new Alert(Alert.AlertType.ERROR);
               alertError.setTitle("Error");
               alertError.setHeaderText("Error al dar de baja");
               alertError.setContentText("Hubo un problema al dar de baja al usuario.");
               alertError.show();
            }
         }
      } else {
         // Maneja el caso en que no se ha seleccionado ningún usuario para dar de baja
         Alert alertError = new Alert(Alert.AlertType.ERROR);
         alertError.setTitle("Error");
         alertError.setHeaderText("Error al dar de baja");
         alertError.setContentText("Selecciona un usuario antes de dar de baja.");
         alertError.show();
      }
   }

   // Método para obtener el usuario seleccionado (ajústalo según tu lógica)
   private UsuarioModel obtenerUsuarioSeleccionado() {
      // Lógica para obtener el usuario seleccionado
      return tablaMenuUsuario.getSelectionModel().getSelectedItem();
   }


   public void actualizarTableView() {
      String valorBusqueda = txtFieldMenuUsuario.getText();
      boolean incluirBaja = cBoxIncluirUsuariosBaja.isSelected();
      ObservableList<UsuarioModel> usuarios = usuarioDAO.buscarUsuarios(valorBusqueda, incluirBaja);
      cargarUsuariosEnTableView(usuarios);
   }
}
