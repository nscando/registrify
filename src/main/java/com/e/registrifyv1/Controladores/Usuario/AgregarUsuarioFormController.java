package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AgregarUsuarioFormController implements Initializable {
   @FXML
   private Button btnCancelar;
   @FXML
   private ComboBox<String> comboUnidad;

   @FXML
   private TextArea txtAreaObservaciones;
   @FXML
   private ComboBox<String> comboRango;
   @FXML
   private ComboBox<String> comboArea;

   private UsuarioDAO usuarioDao;

   @FXML
   private TextField txtNombre;

   @FXML
   private TextField txtApellido;

   @FXML
   private TextField txtDni;

   @FXML
   private RadioButton rbAdmin;
   @FXML
   private RadioButton rbSupervisor;
   @FXML
   private RadioButton rbUser;

   @FXML
   private RadioButton rbEstado;
   int idUnidad = 0;


   @FXML
   private void handleConfirmarButton(ActionEvent event) {
      UsuarioModel nuevoUsuario = obtenerDatosFormulario();

      if (nuevoUsuario != null) {
         UsuarioDAO usuarioDAO = new UsuarioDAO();
         boolean exito = usuarioDAO.insertarUsuario(nuevoUsuario);

         if (exito) {
            mostrarAlerta("Usuario Insertado", "El usuario se ha insertado correctamente.");
            limpiarFormulario();
         } else {
            mostrarAlerta("Error", "Error al insertar el usuario.");
         }
      }
   }

   private UsuarioModel obtenerDatosFormulario() {
      String nombre = txtNombre.getText();
      String apellido = txtApellido.getText();
      String username = nombre + apellido;
      byte[] password = "123456".getBytes();
      String dniString = txtDni.getText();
      int idGendarme = 0;

      // Validar que el DNI sea un número válido
      if (!dniString.matches("\\d+")) {
         mostrarAlerta("Error de Validación", "El valor del DNI no es un número válido.");
         return null;
      }

      int dni = Integer.parseInt(dniString);

      String area = comboArea.getValue();
      String rango = comboRango.getValue();
      int unidad = idUnidad;
      String observaciones = txtAreaObservaciones.getText();
      int estado = rbEstado.isSelected() ? 1 : 0;
      int idRol = obtenerIdRol();

      UsuarioModel nuevoUsuario = new UsuarioModel(
              idGendarme, unidad, idRol, nombre, apellido, dni, username, rango, area, password, estado, observaciones
      );

      nuevoUsuario.setIdGendarme(idGendarme);
      nuevoUsuario.setNombre(nombre);
      nuevoUsuario.setApellido(apellido);
      nuevoUsuario.setDni(dni);
      nuevoUsuario.setArea(area);
      nuevoUsuario.setRango(rango);
      nuevoUsuario.setIdUnidad(Integer.parseInt(String.valueOf(unidad)));
      nuevoUsuario.setObservaciones(observaciones);
      nuevoUsuario.setEstado(estado);

      return nuevoUsuario;
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

   private void limpiarFormulario() {
      txtNombre.clear();
      txtApellido.clear();
      txtDni.clear();
      comboArea.getSelectionModel().clearSelection();
      comboRango.getSelectionModel().clearSelection();
      txtAreaObservaciones.clear();
      rbAdmin.setSelected(false);
      rbSupervisor.setSelected(false);
      rbUser.setSelected(false);
      rbEstado.setSelected(false);
   }


   @FXML
   private void handleCancelarButtonAction(ActionEvent event) {
      Stage stage = (Stage) btnCancelar.getScene().getWindow();
      stage.close();
   }


   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      try {
         ObservableList<String> rangoList = FXCollections.observableArrayList("Cabo Primero", "Sargento", "Suboficial Principal", "Oficial Principal");
         comboRango.setItems(rangoList);

         List<UnidadMenuModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
         ObservableList<String> unidadesNombres = FXCollections.observableArrayList();

         for (UnidadMenuModel unidad : unidades) {
            idUnidad = unidad.getIdUnidad();
            unidadesNombres.add(unidad.getNombreUnidad());
         }

         comboUnidad.setItems(unidadesNombres);

         ObservableList<String> areaList = FXCollections.observableArrayList("Sistemas", "Mantenimiento", "Contabilidad", "Otro");
         comboArea.setItems(areaList);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }


}


