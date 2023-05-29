package com.e.registrifyv1.Controladores;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuariosMenuController {

   @FXML
   private TableView<UsuarioModel> tablaMenuUsuario;
   @FXML
   private TableColumn<UsuarioModel, Integer> idCol;

   @FXML
   private TableColumn<UsuarioModel, String> nombreGendarmeCol;

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
   }

   @FXML
   private void btBuscarAction(ActionEvent event) {
      String valorBusqueda = txtFieldMenuUsuario.getText();
      ObservableList<UsuarioModel> usuarios = usuarioDAO.buscarUsuarios(valorBusqueda);
      cargarUsuariosEnTableView(usuarios);
   }

   private void cargarUsuariosEnTableView(ObservableList<UsuarioModel> usuarios) {
      tablaMenuUsuario.setItems(usuarios);

      idCol.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
      nombreGendarmeCol.setCellValueFactory(new PropertyValueFactory<>("nombreGendarme"));
      usernameCol.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
      rolCol.setCellValueFactory(new PropertyValueFactory<>("idRol"));
      estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
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



}
