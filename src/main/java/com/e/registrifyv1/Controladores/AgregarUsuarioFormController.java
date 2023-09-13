package com.e.registrifyv1.Controladores;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;

public class AgregarUsuarioFormController implements Initializable {
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
      // Obtener los datos del formulario

      String nombre = txtNombre.getText();
      String apellido = txtApellido.getText();
      String username = nombre + apellido ;
      byte[] password = "123456".getBytes();
      String dniString = txtDni.getText(); // Obtener el valor del campo DNI como una cadena
      int dni = 0; // Inicializar dni como 0 por defecto

      // Validar que la cadena de DNI sea numérica antes de intentar convertirla
      if (dniString.matches("\\d+")) {
         dni = Integer.parseInt(dniString);
      } else {
         System.out.println("El valor del DNI no es un número válido.");
         return; // Salir del método si el DNI no es válido
      }

      String area = comboArea.getValue();
      String rango = comboRango.getValue();
      int unidad = idUnidad;
      String observaciones = txtAreaObservaciones.getText();
      int estado = rbEstado.isSelected() ? 1 : 0;
      int idRol =  0;

      // Determinar el valor de idRol en función de la selección del usuario
      if (rbAdmin.isSelected()) {
         idRol = 1; // Asignar el valor correspondiente al rol de administrador
      } else if (rbSupervisor.isSelected()) {
         idRol = 2; // Asignar el valor correspondiente a otro rol (ajusta esto según tus roles)
      }else  {
         idRol = 3; // Asignar el valor correspondiente a otro rol (ajusta esto según tus roles)
      }



      // Crear un nuevo objeto UsuarioModel con los datos del formulario
      UsuarioModel nuevoUsuario = new UsuarioModel(
               unidad, idRol, nombre, apellido, dni, username, rango, area, password, estado, observaciones
      );
      nuevoUsuario.setNombre(nombre);
      nuevoUsuario.setApellido(apellido);
      nuevoUsuario.setDni(dni);
      nuevoUsuario.setArea(area);
      nuevoUsuario.setRango(rango);
      nuevoUsuario.setIdUnidad(Integer.parseInt(String.valueOf(unidad)));
      nuevoUsuario.setObservaciones(observaciones);
      nuevoUsuario.setEstado(estado);

      // Llamar al método insertarUsuario en UsuarioDAO
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      boolean exito = usuarioDAO.insertarUsuario(nuevoUsuario);

      if (exito) {
         System.out.println("Usuario insertado correctamente.");
      } else {
         System.out.println("Error al insertar el usuario.");
      }
   }



   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      try {
         ObservableList<String> rangoList = FXCollections.observableArrayList("Cabo Primero", "Sargento", "Suboficial Principal","Oficial Principal");
         comboRango.setItems(rangoList);

         List<UnidadModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
         ObservableList<String> unidadesNombres = FXCollections.observableArrayList();

         for (UnidadModel unidad : unidades) {
            idUnidad = unidad.getIdUnidad();
            unidadesNombres.add(unidad.getNombreUnidad());
         }

         comboUnidad.setItems(unidadesNombres);

         ObservableList<String> areaList = FXCollections.observableArrayList("Sistemas", "Mantenimiento", "Contabilidad","Otro");
         comboArea.setItems(areaList);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }



}


