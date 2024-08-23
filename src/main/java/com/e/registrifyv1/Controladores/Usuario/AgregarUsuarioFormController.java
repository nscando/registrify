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
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class AgregarUsuarioFormController implements Initializable {
   @FXML
   private Button btnCancelar;

   @FXML
   private Button btnConfirmar;

   @FXML
   private ComboBox<String> comboUnidad;

   @FXML
   private TextArea txtAreaObservaciones;

   @FXML
   private ComboBox<String> comboRango;

   @FXML
   private ComboBox<String> comboArea;

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
         boolean exito = usuarioDAO.insertarUsuario(nuevoUsuario, obtenerUsuarioModificadorId());

         if (exito) {
            mostrarAlerta("Usuario Insertado", "El usuario se ha insertado correctamente.");
            limpiarFormulario();
         } else {
            mostrarAlerta("Error", "Error al insertar el usuario.");
         }
      }

      Stage stage = (Stage) btnConfirmar.getScene().getWindow();
      stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
   }

   private UsuarioModel obtenerDatosFormulario() {
      // Obtener datos del formulario
      String nombre = txtNombre.getText().trim();
      String apellido = txtApellido.getText().trim();
      String dni = txtDni.getText().trim(); // Tratar dni como String
      String area = comboArea.getValue();
      String rango = comboRango.getValue();
      String observaciones = txtAreaObservaciones.getText().trim();
      byte[] password = "123456".getBytes();
      int idGendarme = 0;
      int unidad = idUnidad;
      int estado = rbEstado.isSelected() ? 1 : 0;
      int idRol = obtenerIdRol();
      String username = nombre + apellido;

      // Validar que los campos no estén vacíos
      if (nombre.isEmpty()) {
         mostrarAlerta("Error de Validación", "El campo Nombre no puede estar vacío.");
         return null;
      }
      if (apellido.isEmpty()) {
         mostrarAlerta("Error de Validación", "El campo Apellido no puede estar vacío.");
         return null;
      }
      if (dni.isEmpty()) {
         mostrarAlerta("Error de Validación", "El campo DNI no puede estar vacío.");
         return null;
      }
      if (area == null || area.isEmpty()) {
         mostrarAlerta("Error de Validación", "Debe seleccionar un Área.");
         return null;
      }
      if (rango == null || rango.isEmpty()) {
         mostrarAlerta("Error de Validación", "Debe seleccionar un Rango.");
         return null;
      }

      // Validar que el DNI sea un número válido
      if (!dni.matches("\\d+")) {
         mostrarAlerta("Error de Validación", "El valor del DNI no es un número válido.");
         return null;
      }

      // Incluir el Timestamp actual al crear un nuevo usuario
      Timestamp dateAdd = new Timestamp(System.currentTimeMillis());

      // Crear y devolver el modelo de usuario con los datos validados
      return new UsuarioModel(idGendarme, unidad, idRol, nombre, apellido, dni, username, rango, area, password, estado, observaciones, dateAdd);
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
      stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

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

   private int obtenerUsuarioModificadorId() {
      // Aquí puedes implementar la lógica para obtener el ID del usuario que está realizando la modificación
      return 1; // Suponiendo que el ID del usuario modificador es 1 para este ejemplo
   }
}
