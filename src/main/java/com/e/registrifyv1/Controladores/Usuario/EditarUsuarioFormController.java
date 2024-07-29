package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;

public class EditarUsuarioFormController {

   @FXML
   private TextField txtNombre;
   @FXML
   private TextField txtApellido;
   @FXML
   private TextField txtDni;
   @FXML
   private TextField txtRango;
   @FXML
   private TextField txtUnidad;

   @FXML
   private PasswordField txtOldPassword;
   @FXML
   private PasswordField txtNewPassword;
   @FXML
   private PasswordField txtConfirmPassword;

   private UsuarioModel usuarioActual;
   private UsuarioDAO usuarioDAO;

   @FXML
   public void initialize() {
      // Cargar los datos del usuario logueado
      usuarioActual = Session.getUsuarioLogueado();
      usuarioDAO = new UsuarioDAO();
   }

   public void setDatosUsuario(String nombre, String apellido, String dni, String rango, int unidad) {
      txtNombre.setText(nombre);
      txtApellido.setText(apellido);
      txtDni.setText(dni);
      txtRango.setText(rango);
      txtUnidad.setText(String.valueOf(unidad));
   }

   @FXML
   private void handleConfirmarAction(ActionEvent event) {
      String oldPassword = txtOldPassword.getText();
      String newPassword = txtNewPassword.getText();
      String confirmPassword = txtConfirmPassword.getText();

      if (validarEntradas(oldPassword, newPassword, confirmPassword)) {
         // Verifica la contraseña antigua con la almacenada
         if (usuarioDAO.verifyPassword(usuarioActual.getUsername(), oldPassword)) {
            // Encripta la nueva contraseña antes de enviarla al DAO
            String newPasswordHash = obtenerPasswordHash(newPassword);
            boolean updateSuccess = usuarioDAO.updatePassword(usuarioActual.getIdGendarme(), newPasswordHash);

            if (updateSuccess) {
               mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Contraseña actualizada correctamente");
               handleCancelarAction(event);  // Cierra la ventana
            } else {
               mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la contraseña");
            }
         } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La contraseña actual es incorrecta");
         }
      }
   }

   private boolean validarEntradas(String oldPassword, String newPassword, String confirmPassword) {
      if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
         mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ningún campo puede estar vacío");
         return false;
      }
      if (!newPassword.equals(confirmPassword)) {
         mostrarAlerta(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden");
         return false;
      }
      if (newPassword.length() < 6) {
         mostrarAlerta(Alert.AlertType.ERROR, "Error", "La nueva contraseña debe tener al menos 6 caracteres");
         return false;
      }
      return true;
   }

   private String obtenerPasswordHash(String password) {
      // Aquí deberías usar la misma lógica de hashing que en el DAO y la base de datos
      // Si usas hashing, reemplaza esta implementación por la adecuada
      return password; // O utiliza un método de hashing adecuado
   }

   private void mostrarAlerta(Alert.AlertType alertType, String titulo, String mensaje) {
      Alert alerta = new Alert(alertType);
      alerta.setTitle(titulo);
      alerta.setHeaderText(null);
      alerta.setContentText(mensaje);
      alerta.showAndWait();
   }

   @FXML
   private void handleCancelarAction(ActionEvent event) {
      // Cerrar la ventana
      Stage stage = (Stage) txtOldPassword.getScene().getWindow();
      stage.close();
   }
}
