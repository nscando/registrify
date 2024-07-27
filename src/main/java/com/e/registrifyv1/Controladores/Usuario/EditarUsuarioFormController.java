package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.Session;
import com.e.registrifyv1.Modelos.Rol.Rol;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditarUsuarioFormController {
/*
   @FXML
   private TextField txtNombre;
   @FXML
   private TextField txtApellido;
   @FXML
   private TextField txtDni;
   @FXML
   private ComboBox<String> comboUnidad;
   @FXML
   private ComboBox<String> comboArea;
   @FXML
   private ComboBox<String> comboRango;
   @FXML
   private RadioButton rbAdmin;
   @FXML
   private RadioButton rbSupervisor;
   @FXML
   private RadioButton rbUser;
   @FXML
   private RadioButton rbEstado;
   @FXML
   private TextArea txtAreaObservaciones;
   @FXML
   private PasswordField txtPasswordOld;
   @FXML
   private PasswordField txtPasswordNew;
   @FXML
   private PasswordField txtPasswordConfirm;

   private UsuarioModel usuario;

   @FXML
   public void initialize() {
      // Aquí se pueden agregar inicializaciones generales si se necesitan
   }

   public void setUsuario(UsuarioModel usuario) {
      this.usuario = usuario;
      inicializarDatos();
   }

   private void inicializarDatos() {
      txtNombre.setText(usuario.getNombre());
      txtApellido.setText(usuario.getApellido());
      txtDni.setText(usuario.getDni());
      comboUnidad.setValue(String.valueOf(usuario.getIdUnidad()));
      comboArea.setValue(usuario.getArea());
      comboRango.setValue(usuario.getRango());

      switch (usuario.getIdRol()) {
         case Rol.ADMINISTRADOR:
            rbAdmin.setSelected(true);
            break;
         case Rol.SUPERVISOR:
            rbSupervisor.setSelected(true);
            break;
         case Rol.USUARIO:
            rbUser.setSelected(true);
            break;
      }

      rbEstado.setSelected(usuario.getEstado() == 1);
      txtAreaObservaciones.setText(usuario.getObservaciones());

      // Configurar la editabilidad de los campos según el rol del usuario logueado
      configurarCampos();
   }

   private void configurarCampos() {
      int idRol = Session.getIdRol();
      boolean isEditable = (idRol == Rol.ADMINISTRADOR);
      boolean isSupervisor = (idRol == Rol.SUPERVISOR);

      txtNombre.setDisable(!isEditable);
      txtApellido.setDisable(!isEditable);
      txtDni.setDisable(!isEditable);
      comboUnidad.setDisable(!isEditable);
      comboArea.setDisable(!isEditable);
      comboRango.setDisable(!isEditable);
      rbAdmin.setDisable(!isEditable);
      rbSupervisor.setDisable(!isEditable);
      rbUser.setDisable(!isEditable);
      rbEstado.setDisable(!isEditable);
      txtAreaObservaciones.setDisable(!isEditable && !isSupervisor);

      // Los campos de contraseña siempre están habilitados
      txtPasswordOld.setDisable(false);
      txtPasswordNew.setDisable(false);
      txtPasswordConfirm.setDisable(false);
   }

   @FXML
   private void handleConfirmarButton() {
      // Verificar y actualizar la información del usuario
      if (verificarContraseña()) {
         actualizarUsuario();
         // Lógica para cerrar el formulario o mostrar confirmación
      } else {
         mostrarAlertaError("Error de Contraseña", "Las contraseñas no coinciden o la contraseña antigua es incorrecta.");
      }
   }

   @FXML
   private void handleCancelarButton() {
      // Lógica para cerrar el formulario de edición sin realizar cambios
   }

   private boolean verificarContraseña() {
      // Verifica si la contraseña antigua es correcta y si la nueva coincide con la confirmación
      String passwordOld = txtPasswordOld.getText();
      String passwordNew = txtPasswordNew.getText();
      String passwordConfirm = txtPasswordConfirm.getText();

      // Verificación básica (puede ser más robusta si se verifica contra la base de datos)
      return usuario.getPassword().equals(passwordOld) && passwordNew.equals(passwordConfirm);
   }

   private void actualizarUsuario() {
      // Actualiza la información del usuario con los datos del formulario
      if (Session.getIdRol() == Rol.ADMINISTRADOR) {
         usuario.setNombre(txtNombre.getText());
         usuario.setApellido(txtApellido.getText());
         usuario.setDni(txtDni.getText());
         usuario.setIdUnidad(Integer.parseInt(comboUnidad.getValue()));
         usuario.setArea(comboArea.getValue());
         usuario.setRango(comboRango.getValue());
         usuario.setIdRol(obtenerIdRolSeleccionado());
         usuario.setEstado(rbEstado.isSelected() ? 1 : 0);
         usuario.setObservaciones(txtAreaObservaciones.getText());
      }

      // Actualizar la contraseña si se proporciona una nueva
      if (!txtPasswordNew.getText().isEmpty()) {
         usuario.setPassword(txtPasswordNew.getText().getBytes());
      }

      // Lógica para guardar el usuario en la base de datos o en la sesión
   }

   private int obtenerIdRolSeleccionado() {
      if (rbAdmin.isSelected()) {
         return Rol.ADMINISTRADOR;
      } else if (rbSupervisor.isSelected()) {
         return Rol.SUPERVISOR;
      } else {
         return Rol.USUARIO;
      }
   }

   private void mostrarAlertaError(String titulo, String mensaje) {
      Alert alerta = new Alert(Alert.AlertType.ERROR);
      alerta.setTitle(titulo);
      alerta.setHeaderText(null);
      alerta.setContentText(mensaje);
      alerta.showAndWait();

   }
*/

}

