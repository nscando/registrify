package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModificarUsuarioFormController {

   @FXML
   private TextField txtNombre; // Ajusta esto con el nombre de tus campos en la vista
   @FXML
   private TextField txtApellido; // Ajusta esto con el nombre de tus campos en la vista


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

   private UsuarioDAO usuarioDao;

   private UsuarioModel usuario; // Esta variable contendrá los datos del usuario

   @FXML
   public void initialize() {
      // Aquí puedes hacer cualquier inicialización adicional
      // Por ejemplo, llenar los ComboBox con opciones, etc.
      // ...
   }

   // Este método será llamado desde UsuariosMenuController para pasar los datos del usuario seleccionado
   public void inicializarDatos(UsuarioModel usuario) {
      this.usuario = usuario;

      // Asignar los valores del usuario a los campos correspondientes
      txtNombre.setText(usuario.getNombre());
      txtApellido.setText(usuario.getApellido());
      txtDni.setText(String.valueOf(usuario.getDni())); // Convertir a cadena
      comboRango.setValue(usuario.getRango());
      comboArea.setValue(usuario.getArea());
      txtAreaObservaciones.setText(usuario.getObservaciones());

      // Dependiendo del estado, seleccionar el RadioButton correspondiente
      if (usuario.getEstado() == 1) {
         rbEstado.setSelected(true);
      } else {
         rbEstado.setSelected(false);
      }

      // Dependiendo del idRol, seleccionar el RadioButton correspondiente
      int idRol = usuario.getIdRol();
      switch (idRol) {
         case 1:
            rbAdmin.setSelected(true);
            break;
         case 2:
            rbSupervisor.setSelected(true);
            break;
         case 3:
            rbUser.setSelected(true);
            break;
         default:
            // No se selecciona ningún RadioButton
            break;
      }

      // Aquí puedes agregar el código para inicializar los ComboBox según los datos del usuario
   }


   @FXML
   private void handleCancelarButtonAction(ActionEvent event) {
      Stage stage = (Stage) btnCancelar.getScene().getWindow();
      stage.close();
   }


}



