package com.e.registrifyv1.Controladores;

import com.e.registrifyv1.Dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AgregarUsuarioFormController implements Initializable {
   @FXML
   private ComboBox<String> comboUnidad;

   private UsuarioDAO usuarioDao;


   public void initialize(URL location, ResourceBundle resources) {
      // Crea una instancia del UsuariosDao
      usuarioDao = new UsuarioDAO();

      try {
         // Configura las opciones de los ComboBoxes
         comboUnidad.setItems(FXCollections.observableArrayList(usuarioDao.obtenerOpcionesUnidad()));

      } catch (SQLException e) {
         // Manejo de excepciones, si es necesario
         e.printStackTrace();
      }
   }

   // Otro código del controlador...
}


