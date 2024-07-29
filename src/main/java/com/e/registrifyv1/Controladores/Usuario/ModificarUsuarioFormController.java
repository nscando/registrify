package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModificarUsuarioFormController {

   @FXML
   private TextField txtNombre;

   @FXML
   private TextField txtApellido;

   @FXML
   private TextField txtDni;

   @FXML
   private ComboBox<String> comboArea;

   @FXML
   private ComboBox<String> comboRango;

   @FXML
   private TextArea txtAreaObservaciones;

   @FXML
   private RadioButton rbAdmin;

   @FXML
   private RadioButton rbSupervisor;

   @FXML
   private RadioButton rbUser;

   @FXML
   private RadioButton rbEstado;

   private TableView<UsuarioModel> tablaMenuUsuario;

   private UsuariosMenuController usuariosMenuController;

   private UsuarioModel usuario;

   public void setTablaMenuUsuario(TableView<UsuarioModel> tablaMenuUsuario) {
      this.tablaMenuUsuario = tablaMenuUsuario;
   }

   public void setUsuariosMenuController(UsuariosMenuController usuariosMenuController) {
      this.usuariosMenuController = usuariosMenuController;
   }

   public void inicializarDatos(UsuarioModel usuario) {
      this.usuario = usuario;
      txtNombre.setText(usuario.getNombre());
      txtApellido.setText(usuario.getApellido());
      txtDni.setText(usuario.getDni());
      comboArea.setValue(usuario.getArea());
      comboRango.setValue(usuario.getRango());
      txtAreaObservaciones.setText(usuario.getObservaciones());
      rbEstado.setSelected(usuario.getEstado() == 1);

      switch (usuario.getIdRol()) {
         case 1:
            rbAdmin.setSelected(true);
            break;
         case 2:
            rbSupervisor.setSelected(true);
            break;
         case 3:
            rbUser.setSelected(true);
            break;
      }
   }

   @FXML
   private void handleConfirmarButtonAction(ActionEvent event) {
      UsuarioModel usuarioActualizado = obtenerDatosFormulario();

      if (usuarioActualizado != null) {
         UsuarioDAO usuarioDAO = new UsuarioDAO();
         boolean exito = usuarioDAO.actualizarUsuario(usuarioActualizado, obtenerUsuarioModificadorId());

         if (exito) {
            mostrarAlerta("Usuario Modificado", "El usuario se ha modificado correctamente.");
            if (usuariosMenuController != null) {
               usuariosMenuController.actualizarTableView();
            }
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.close();
         } else {
            mostrarAlerta("Error", "Error al modificar el usuario.");
         }
      }
   }

   private UsuarioModel obtenerDatosFormulario() {
      String nombre = txtNombre.getText();
      String apellido = txtApellido.getText();
      String username = nombre + apellido;
      String dni = txtDni.getText(); // Tratar dni como String

      // Validar que el DNI sea un número válido
      if (!dni.matches("\\d+")) {
         mostrarAlerta("Error de Validación", "El valor del DNI no es un número válido.");
         return null;
      }

      String area = comboArea.getValue();
      String rango = comboRango.getValue();
      String observaciones = txtAreaObservaciones.getText();
      int estado = rbEstado.isSelected() ? 1 : 0;
      int idRol = obtenerIdRol();

      return new UsuarioModel(
              usuario.getIdGendarme(), usuario.getIdUnidad(), idRol, nombre, apellido, dni, username, rango, area, usuario.getPassword(), estado, observaciones, usuario.getDateAdd());
   }

   private int obtenerIdRol() {
      if (rbAdmin.isSelected()) {
         return 1;
      } else if (rbSupervisor.isSelected()) {
         return 2;
      } else if (rbUser.isSelected()) {
         return 3;
      }
      return 0; // Puedes manejar esto según tus necesidades
   }

   private void mostrarAlerta(String titulo, String mensaje) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(titulo);
      alert.setHeaderText(mensaje);
      ButtonType okButton = new ButtonType("OK");
      alert.getButtonTypes().setAll(okButton);
      alert.showAndWait();
   }

   @FXML
   private void handleCancelarButtonAction(ActionEvent event) {
      Stage stage = (Stage) txtNombre.getScene().getWindow();
      stage.close();
   }

   private int obtenerUsuarioModificadorId() {
      // Aquí puedes implementar la lógica para obtener el ID del usuario que está realizando la modificación
      return 1; // Suponiendo que el ID del usuario modificador es 1 para este ejemplo
   }
}
