package com.e.registrifyv1.Controladores;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController {
   @FXML
   private Label loginMensajeLabel;
   @FXML
   private Button salirBtn;

   @FXML
   private TextField usuarioTxtField;

   @FXML
   private PasswordField passwordTxtField;

   @FXML
   private Button ingresarBtn;

   private UsuarioDAO usuarioDAO;

   public LoginController() {
      usuarioDAO = new UsuarioDAO();
   }

   @FXML
   private void initialize() {
      // Asignar eventos para el manejo de teclas
      usuarioTxtField.setOnKeyPressed(this::handleKeyPress);
      passwordTxtField.setOnKeyPressed(this::handleKeyPress);
      ingresarBtn.setOnKeyPressed(event -> {
         if (event.getCode() == KeyCode.ENTER) {
            loginBtnAction(new ActionEvent()); // Llamar al método de inicio de sesión
         }
      });
      salirBtn.setOnKeyPressed(this::handleKeyPress);
   }

   private void handleKeyPress(KeyEvent event) {
      if (event.getCode() == KeyCode.TAB) {
         if (event.getSource() == usuarioTxtField) {
            passwordTxtField.requestFocus();
         } else if (event.getSource() == passwordTxtField) {
            ingresarBtn.requestFocus();
         } else if (event.getSource() == ingresarBtn) {
            salirBtn.requestFocus();
         } else if (event.getSource() == salirBtn) {
            usuarioTxtField.requestFocus();
         }
      }
   }
   public void loginBtnAction(ActionEvent e) {
      String usuario = usuarioTxtField.getText().trim();
      String password = passwordTxtField.getText().trim();

      // Expresiones regulares para validar que no haya caracteres especiales
      String validUsernamePattern = "^[a-zA-Z0-9_]+$";  // Permite solo letras, números y guiones bajos
      String validPasswordPattern = "^[a-zA-Z0-9_!@#$%^&*()]+$";  // Permite letras, números, guiones bajos y caracteres especiales comunes

      // Verifica si alguno de los campos está vacío, contiene espacios en blanco, o caracteres no permitidos
      if (usuario.isEmpty() || password.isEmpty() ||
              !usuario.matches(validUsernamePattern) ||
              !password.matches(validPasswordPattern)) {
         loginMensajeLabel.setText("Usuario y/o Contraseña incorrectos o contienen caracteres no permitidos!");
      } else {
         validarLogin();
      }
   }


   public void salirBtnAction(ActionEvent e) {
      Stage stage = (Stage) salirBtn.getScene().getWindow();
      stage.close();
   }

   public void validarLogin() {
      String username = usuarioTxtField.getText().trim();
      String password = passwordTxtField.getText().trim();

      // Validación de usuario y contraseña
      if (username.isEmpty() || password.isEmpty()) {
         loginMensajeLabel.setText("Usuario y Contraseña no pueden estar vacíos.");
         return;
      }

      // Validación en la base de datos
      UsuarioModel usuario = usuarioDAO.getUsuarioByUsernameAndPassword(username, password);

      if (usuario != null) {
         Session.iniciarSesion(usuario);

         loginMensajeLabel.setText("Bienvenido a Registrify");

         // Cerrar la ventana actual
         Stage stageActual = (Stage) ingresarBtn.getScene().getWindow();
         stageActual.close();

         // Abrir la ventana del menú principal
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Otros/MenuPrincipalView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Principal");

            stage.show();
         } catch (Exception e) {
            e.printStackTrace();
            loginMensajeLabel.setText("Error al abrir la ventana del menú principal.");
         }
      } else {
         loginMensajeLabel.setText("Usuario y/o Contraseña incorrectos!");
      }
   }

}
