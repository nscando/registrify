package com.e.registrifyv1.Controladores;

import com.e.registrifyv1.Utiles.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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


   public void loginBtnAction(ActionEvent e) {

      if (!usuarioTxtField.getText().isBlank() && !passwordTxtField.getText().isBlank()) {
         //loginMensajeLabel.setText("Bievenido a Registrify!");
         validarLogin();

      } else {
         loginMensajeLabel.setText("Usuario y/o Contraseña incorrectos!");


      }


   }

   public void salirBtnAction(ActionEvent e) {
      Stage stage = (Stage) salirBtn.getScene().getWindow();
      stage.close();
   }

   public void validarLogin() {
      DBConnection connectNow = new DBConnection();
      Connection connectDB = connectNow.getConexion();

      String verificarLogin = "SELECT count(1) FROM USUARIO WHERE username = ? AND password = ?";

      try {
         PreparedStatement preparedStatement = connectDB.prepareStatement(verificarLogin);
         preparedStatement.setString(1, usuarioTxtField.getText());
         preparedStatement.setString(2, passwordTxtField.getText());
         ResultSet queryResult = preparedStatement.executeQuery();

         while (queryResult.next()) {
            if (queryResult.getInt(1) == 1) {
               loginMensajeLabel.setText("Bienvenido a Registrify");

               // Cerrar la ventana actual
               Stage stageActual = (Stage) ingresarBtn.getScene().getWindow();
               stageActual.close();

               // Abrir la ventana del menú principal
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/e/registrifyv1/Vistas/MenuPrincipalView.fxml"));
               Parent root = loader.load();
               Stage stage = new Stage();
               stage.setScene(new Scene(root));
               stage.setTitle("Menú Principal");
               stage.show();
            } else {
               loginMensajeLabel.setText("Usuario y/o Contraseña incorrectos!");
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }



}