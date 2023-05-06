package com.e.registrifyv1.Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


   public void loginBtnAction(ActionEvent e) {

      if(!usuarioTxtField.getText().isBlank() && !passwordTxtField.getText().isBlank()){
         loginMensajeLabel.setText("Bievenido a Registrify!");

      }else {
         loginMensajeLabel.setText("Error en Usuario y/o Contraseña vuelva a intentar.");


      }


   }

   public void salirBtnAction(ActionEvent e) {
      Stage stage = (Stage) salirBtn.getScene().getWindow();
      stage.close();
   }
}