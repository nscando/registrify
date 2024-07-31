package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

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
   private ComboBox<String> comboUnidad;

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

   private UsuarioModel usuario;
   private TableView<UsuarioModel> tablaMenuUsuario;
   private UsuariosMenuController usuariosMenuController;
   private UsuarioDAO usuarioDAO;

   public void setTablaMenuUsuario(TableView<UsuarioModel> tablaMenuUsuario) {
      this.tablaMenuUsuario = tablaMenuUsuario;
   }

   public void setUsuariosMenuController(UsuariosMenuController usuariosMenuController) {
      this.usuariosMenuController = usuariosMenuController;
   }

   @FXML
   public void initialize() throws SQLException {
      usuarioDAO = new UsuarioDAO();
      cargarOpcionesArea();
      cargarOpcionesRango();
      cargarOpcionesUnidad();
   }

   private void cargarOpcionesArea() {
      List<String> areas = usuarioDAO.obtenerTodasLasAreas();
      comboArea.getItems().addAll(areas);
   }

   private void cargarOpcionesRango() {
      List<String> rangos = usuarioDAO.obtenerTodosLosRangos();
      comboRango.getItems().addAll(rangos);
   }

   private void cargarOpcionesUnidad() throws SQLException {
      List<UnidadMenuModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
      ObservableList<String> unidadesNombres = FXCollections.observableArrayList();
      for (UnidadMenuModel unidad : unidades) {
         unidadesNombres.add(unidad.getNombreUnidad());
      }
      comboUnidad.setItems(unidadesNombres);
   }

   public void inicializarDatos(UsuarioModel usuario) throws SQLException {
      this.usuario = usuario;
      txtNombre.setText(usuario.getNombre());
      txtApellido.setText(usuario.getApellido());
      txtDni.setText(usuario.getDni());
      comboArea.setValue(usuario.getArea());
      comboRango.setValue(usuario.getRango());
      comboUnidad.setValue(obtenerNombreUnidad(usuario.getIdUnidad()));
      txtAreaObservaciones.setText(usuario.getObservaciones());
      rbEstado.setSelected(usuario.getEstado() == 1);

      ToggleGroup rolGroup = new ToggleGroup();
      rbAdmin.setToggleGroup(rolGroup);
      rbSupervisor.setToggleGroup(rolGroup);
      rbUser.setToggleGroup(rolGroup);

      if (usuario.getIdRol() == 1) {
         rbAdmin.setSelected(true);
      } else if (usuario.getIdRol() == 2) {
         rbSupervisor.setSelected(true);
      } else if (usuario.getIdRol() == 3) {
         rbUser.setSelected(true);
      }
   }

   private String obtenerNombreUnidad(int idUnidad) throws SQLException {
      List<UnidadMenuModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
      for (UnidadMenuModel unidad : unidades) {
         if (unidad.getIdUnidad() == idUnidad) {
            return unidad.getNombreUnidad();
         }
      }
      return null;
   }

   @FXML
   private void handleConfirmarButtonAction(ActionEvent event) throws SQLException {
      UsuarioModel usuarioActualizado = obtenerDatosFormulario();
      if (usuarioActualizado != null) {
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

   private UsuarioModel obtenerDatosFormulario() throws SQLException {
      String nombre = txtNombre.getText();
      String apellido = txtApellido.getText();
      String username = nombre + apellido;
      String dni = txtDni.getText();

      if (!dni.matches("\\d+")) {
         mostrarAlerta("Error de Validación", "El valor del DNI no es un número válido.");
         return null;
      }

      String area = comboArea.getValue();
      String rango = comboRango.getValue();
      String unidad = comboUnidad.getValue(); // Obtener la unidad seleccionada
      String observaciones = txtAreaObservaciones.getText();
      int estado = rbEstado.isSelected() ? 1 : 0;
      int idRol = obtenerIdRol();

      return new UsuarioModel(
              usuario.getIdGendarme(), obtenerIdUnidadPorNombre(unidad), idRol, nombre, apellido, dni, username, rango, area, usuario.getPassword(), estado, observaciones, usuario.getDateAdd());
   }

   private int obtenerIdUnidadPorNombre(String nombreUnidad) throws SQLException {
      List<UnidadMenuModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
      for (UnidadMenuModel unidad : unidades) {
         if (unidad.getNombreUnidad().equals(nombreUnidad)) {
            return unidad.getIdUnidad();
         }
      }
      return 0; // O manejar de otra manera si no se encuentra
   }

   private int obtenerIdRol() {
      if (rbAdmin.isSelected()) {
         return 1;
      } else if (rbSupervisor.isSelected()) {
         return 2;
      } else if (rbUser.isSelected()) {
         return 3;
      }
      return 0;
   }

   private void mostrarAlerta(String titulo, String mensaje) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(titulo);
      alert.setHeaderText(mensaje);
      alert.showAndWait();
   }

   @FXML
   private void handleCancelarButtonAction(ActionEvent event) {
      Stage stage = (Stage) txtNombre.getScene().getWindow();
      stage.close();
   }

   private int obtenerUsuarioModificadorId() {
      // Implementa la lógica para obtener el ID del usuario que realiza la modificación
      return 1; // Cambia esto según la lógica de tu aplicación
   }
}
