package com.e.registrifyv1.Controladores.Usuario;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.sql.Date;



public class UsuariosMenuController {

   @FXML
   private Button btnSalir;

   @FXML
   private Button btnBaja;
   @FXML
   private TableView<UsuarioModel> tablaMenuUsuario;
   @FXML
   private TableColumn<UsuarioModel, Integer> idCol;
   @FXML
   private CheckBox cBoxIncluirUsuariosBaja;
   @FXML
   private TableColumn<UsuarioModel, String> nombreCol;
   @FXML
   private TableColumn<UsuarioModel, String> apellidoCol;
   @FXML
   private TableColumn<UsuarioModel, String> rangoCol;
   @FXML
   private TableColumn<UsuarioModel, String> observacionesCol;
   @FXML
   private TableColumn<UsuarioModel, String> dniCol;
   @FXML
   private TableColumn<UsuarioModel, String> usernameCol;
   @FXML
   private TableColumn<UsuarioModel, String> rolCol;
   @FXML
   private TableColumn<UsuarioModel, Integer> estadoCol;
   @FXML
   private TableColumn<UsuarioModel, Timestamp> fechaAltaCol;
   @FXML
   private TextField txtFieldMenuUsuario;
   @FXML
   private DatePicker fechaDesde;
   @FXML
   private DatePicker fechaHasta;

   private UsuarioDAO usuarioDAO;

   @FXML
   private void initialize() {
      usuarioDAO = new UsuarioDAO();
      configurarColumnas();
   }

   private void configurarColumnas() {
      idCol.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
      nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
      apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
      dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
      observacionesCol.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
      rangoCol.setCellValueFactory(new PropertyValueFactory<>("rango"));
      usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
      rolCol.setCellValueFactory(new PropertyValueFactory<>("idRol"));
      estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
      fechaAltaCol.setCellValueFactory(new PropertyValueFactory<>("dateAdd"));


      tablaMenuUsuario.setOnMouseClicked(event -> {
         if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            UsuarioModel selectedUser = tablaMenuUsuario.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
               abrirFormularioModificarUsuario(selectedUser);
            }
         }
      });
   }

   @FXML
   private void handleSalirButtonAction(ActionEvent event) {
      Stage stage = (Stage) btnSalir.getScene().getWindow();
      stage.close();
   }

   @FXML
   private void btBuscarAction(ActionEvent event) {
      String valorBusqueda = txtFieldMenuUsuario.getText();
      boolean incluirBaja = cBoxIncluirUsuariosBaja.isSelected();
      ObservableList<UsuarioModel> usuarios = usuarioDAO.buscarUsuarios(valorBusqueda, incluirBaja, null, null);
      cargarUsuariosEnTableView(usuarios);
   }

   @FXML
   private void btnFiltrarFechasAction(ActionEvent event) {
      String valorBusqueda = txtFieldMenuUsuario.getText();
      boolean incluirBaja = cBoxIncluirUsuariosBaja.isSelected();
      Date datepickerDesde = fechaDesde.getValue() != null ? Date.valueOf(fechaDesde.getValue()) : null;
      Date datepickerHasta = fechaHasta.getValue() != null ? Date.valueOf(fechaHasta.getValue()) : null;

      ObservableList<UsuarioModel> usuarios = usuarioDAO.buscarUsuarios(valorBusqueda, incluirBaja, datepickerDesde, datepickerHasta);
      cargarUsuariosEnTableView(usuarios);
   }

   private void cargarUsuariosEnTableView(ObservableList<UsuarioModel> usuarios) {
      if (usuarios != null && !usuarios.isEmpty()) {
         tablaMenuUsuario.setItems(usuarios);
      } else {
         tablaMenuUsuario.getItems().clear();
      }
   }

   @FXML
   private void abrirFormularioAgregarUsuario(ActionEvent event) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/AgregarUsuarioForm.fxml"));
         Parent root = loader.load();
         Stage stage = new Stage();
         stage.setTitle("Agregar Usuario");
         stage.setScene(new Scene(root));
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void abrirFormularioModificarUsuario(UsuarioModel usuario) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/ModificarUsuarioForm.fxml"));
         Parent root = loader.load();
         ModificarUsuarioFormController controller = loader.getController();
         controller.setTablaMenuUsuario(tablaMenuUsuario);
         controller.setUsuariosMenuController(this);
         controller.inicializarDatos(usuario);
         Stage stage = new Stage();
         stage.setTitle("Modificar Usuario");
         stage.setScene(new Scene(root));
         stage.show();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @FXML
   private void handleModificarUsuarioButtonAction(ActionEvent event) {
      UsuarioModel usuarioSeleccionado = tablaMenuUsuario.getSelectionModel().getSelectedItem();
      if (usuarioSeleccionado != null) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Usuarios/ModificarUsuarioForm.fxml"));
            Parent root = loader.load();
            ModificarUsuarioFormController controller = loader.getController();
            controller.inicializarDatos(usuarioSeleccionado);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      } else {
         System.out.println("Por favor, seleccione un usuario para modificar.");
      }
   }

   @FXML
   private void handleBtnBajaClick(ActionEvent event) {
      UsuarioModel usuarioSeleccionado = obtenerUsuarioSeleccionado();

      if (usuarioSeleccionado != null) {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Advertencia");
         alert.setHeaderText("¡Atención!");
         alert.setContentText("Estás a punto de dar de baja a un usuario. ¿Estás seguro?");
         alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);



         Optional<ButtonType> result1 = alert.showAndWait();

         if (result1.isPresent() && result1.get() == ButtonType.YES) {
            boolean bajaExitosa = usuarioDAO.bajaUsuario(usuarioSeleccionado.getIdGendarme(), 1);

            if (bajaExitosa) {
               Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
               alert2.setTitle("Confirmación");
               alert2.setHeaderText("¡Usuario dado de baja correctamente!");
               alert2.show();
               actualizarTableView();
            } else {
               Alert alertError = new Alert(Alert.AlertType.ERROR);
               alertError.setTitle("Error");
               alertError.setHeaderText("Error al dar de baja");
               alertError.setContentText("Hubo un problema al dar de baja al usuario.");
               alertError.show();
            }
         }
      } else {
         Alert alertError = new Alert(Alert.AlertType.ERROR);
         alertError.setTitle("Error");
         alertError.setHeaderText("Error al dar de baja");
         alertError.setContentText("Selecciona un usuario antes de dar de baja.");
         alertError.show();
      }
   }

   private UsuarioModel obtenerUsuarioSeleccionado() {
      return tablaMenuUsuario.getSelectionModel().getSelectedItem();
   }

   @FXML
   private void handleGenerarReporte(ActionEvent event) {
      try {
         // Obtén la lista actual de usuarios en la TableView
         ObservableList<UsuarioModel> usuarios = tablaMenuUsuario.getItems();

         // Cargar el diseño del informe desde un archivo jrxml
         // Cambia la ruta según la ubicación real de tu archivo de diseño
         InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_usuarios.jrxml");
         JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

         // Crear una fuente de datos para el informe utilizando JRBeanCollectionDataSource
         JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usuarios);

         // Parámetros del informe (puedes agregar más según tus necesidades)
         Map<String, Object> parameters = new HashMap<>();
         parameters.put("UsuarioDataSource", dataSource);

         // Compilar y llenar el informe con los datos
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

         // Obtén la marca de tiempo actual para el nombre único del archivo
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
         String timeStamp = dateFormat.format(new java.util.Date());

         // Construye el nombre del archivo con la marca de tiempo
         String pdfFileName = "src/main/resources/ReportesGenerados/ReportesUsuarios/registrify_report_usuarios_" + timeStamp + ".pdf";

         JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);

         // Muestra un mensaje de éxito
         mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Informe generado correctamente", "El informe se ha guardado en: " + pdfFileName);
      } catch (JRException e) {
         e.printStackTrace();
         // Muestra un mensaje de error
         mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al generar el informe", e.getMessage());
      }
   }

   private void mostrarAlerta(Alert.AlertType alertType, String title, String headerText, String contentText) {
      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.setHeaderText(headerText);
      alert.setContentText(contentText);
      alert.showAndWait();
   }

   public void actualizarTableView() {
      String valorBusqueda = txtFieldMenuUsuario.getText();
      boolean incluirBaja = cBoxIncluirUsuariosBaja.isSelected();
      ObservableList<UsuarioModel> usuarios = usuarioDAO.buscarUsuarios(valorBusqueda, incluirBaja, null, null);
      cargarUsuariosEnTableView(usuarios);
   }
}
