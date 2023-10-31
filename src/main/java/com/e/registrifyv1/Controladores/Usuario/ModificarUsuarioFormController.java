package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ModificarUsuarioFormController {

   @FXML
   private TextField txtNombre;
   @FXML
   private TextField txtApellido;
   @FXML
   private TextField txtDni;
   @FXML
   private ComboBox<String> comboRango;
   @FXML
   private ComboBox<String> comboArea;
   @FXML
   private TextArea txtAreaObservaciones;
   @FXML
   private ComboBox<String> comboUnidad;
   @FXML
   private RadioButton rbAdmin;
   @FXML
   private RadioButton rbSupervisor;
   @FXML
   private RadioButton rbUser;
   @FXML
   private RadioButton rbEstado;
   @FXML
   private Button btnCancelar;

   private UsuarioModel usuario;
   private int idUnidad = 0;
   private TableView<UsuarioModel> tablaMenuUsuario;
   private UsuariosMenuController usuariosMenuController;
   private int idGendarmeSeleccionado;

   public void initialize() {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      try {
         ObservableList<String> rangoList = FXCollections.observableArrayList("Cabo Primero", "Sargento", "Suboficial Principal", "Oficial Principal");
         comboRango.setItems(rangoList);

         List<UnidadModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
         ObservableList<String> unidadesNombres = FXCollections.observableArrayList();

         for (UnidadModel unidad : unidades) {
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

   public void inicializarDatos(UsuarioModel usuario) {
      this.usuario = usuario;
      this.idGendarmeSeleccionado = usuario.getIdGendarme();

      txtNombre.setText(usuario.getNombre());
      txtApellido.setText(usuario.getApellido());
      txtDni.setText(String.valueOf(usuario.getDni()));
      comboRango.setValue(usuario.getRango());
      comboArea.setValue(usuario.getArea());
      txtAreaObservaciones.setText(usuario.getObservaciones());

      rbEstado.setSelected(usuario.getEstado() == 1);

      switch (usuario.getIdRol()) {
         case 1 -> rbAdmin.setSelected(true);
         case 2 -> rbSupervisor.setSelected(true);
         case 3 -> rbUser.setSelected(true);
      }
   }

   public void setTablaMenuUsuario(TableView<UsuarioModel> tablaMenuUsuario) {
      this.tablaMenuUsuario = tablaMenuUsuario;
   }

   public void setUsuariosMenuController(UsuariosMenuController usuariosMenuController) {
      this.usuariosMenuController = usuariosMenuController;
   }

   @FXML
   private void handleConfirmarButtonAction(ActionEvent event) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmar Modificación");
      alert.setHeaderText("Está a punto de modificar los datos del usuario.");
      alert.setContentText("¿Está seguro de que desea continuar?");

      ButtonType buttonTypeConfirmar = new ButtonType("Confirmar");
      ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
      alert.getButtonTypes().setAll(buttonTypeConfirmar, buttonTypeCancelar);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent() && result.get() == buttonTypeConfirmar) {
         UsuarioModel updateUsuario = obtenerDatosFormulario();

         if (updateUsuario != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean exito = usuarioDAO.actualizarUsuario(updateUsuario);

            if (exito) {
               mostrarAlerta("Actualización Exitosa", "Los datos del usuario han sido actualizados correctamente.");
               limpiarCamposFormulario();

               if (usuariosMenuController != null) {
                  usuariosMenuController.actualizarTableView();
               }

               Stage stage = (Stage) txtNombre.getScene().getWindow();
               stage.close();
            } else {
               mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del usuario.");
            }
         }
      }
   }

   private UsuarioModel obtenerDatosFormulario() {
      String nombre = txtNombre.getText();
      String apellido = txtApellido.getText();
      String username = nombre + apellido;
      String dniString = txtDni.getText();
      String password = "123456";
      int idGendarme = usuario.getIdGendarme();

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

      UsuarioModel updateUsuario = new UsuarioModel(
              idGendarme, unidad, idRol, nombre, apellido, dni, username, rango, area, password.getBytes(), estado, observaciones
      );

      updateUsuario.setIdGendarme(idGendarme);
      updateUsuario.setNombre(nombre);
      updateUsuario.setApellido(apellido);
      updateUsuario.setDni(dni);
      updateUsuario.setArea(area);
      updateUsuario.setRango(rango);
      updateUsuario.setIdUnidad(Integer.parseInt(String.valueOf(unidad)));
      updateUsuario.setObservaciones(observaciones);
      updateUsuario.setEstado(estado);

      return updateUsuario;
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

   private void limpiarCamposFormulario() {
      txtNombre.clear();
      txtApellido.clear();
      txtDni.clear();
      comboRango.getSelectionModel().clearSelection();
      comboArea.getSelectionModel().clearSelection();
      txtAreaObservaciones.clear();
      comboUnidad.getSelectionModel().clearSelection();
      rbAdmin.setSelected(false);
      rbSupervisor.setSelected(false);
      rbUser.setSelected(false);
      rbEstado.setSelected(false);
   }

   private void mostrarAlerta(String titulo, String mensaje) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(titulo);
      alert.setHeaderText(null);
      alert.setContentText(mensaje);
      alert.showAndWait();
   }

   @FXML
   private void handleCancelarButtonAction(ActionEvent event) {
      Stage stage = (Stage) btnCancelar.getScene().getWindow();
      stage.close();
   }
}
