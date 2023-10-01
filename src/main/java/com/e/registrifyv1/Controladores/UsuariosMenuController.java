package com.e.registrifyv1.Controladores;

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

public class UsuariosMenuController {

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
      // Agregar evento de doble clic a la columna que deseas
      nombreCol.setCellFactory(tc -> {
         TableCell<UsuarioModel, String> cell = new TableCell<UsuarioModel, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
               super.updateItem(item, empty);
               if (empty) {
                  setText(null);
               } else {
                  setText(item);
               }
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
   public void abrirFormularioAgregarUsuario(ActionEvent event) {
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

   @FXML
   private void abrirFormularioModificarUsuario(UsuarioModel usuario) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/ModificarUsuarioForm.fxml"));
         Parent root = loader.load();

         // Obtener el controlador del formulario de modificación
         ModificarUsuarioFormController controller = loader.getController();

         // Pasar los datos del usuario seleccionado al controlador del formulario de modificación
         controller.inicializarDatos(usuario);

         // Mostrar el formulario de modificación
         Stage stage = new Stage();
         stage.setTitle("Modificar Usuario");
         stage.setScene(new Scene(root));
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }


}
