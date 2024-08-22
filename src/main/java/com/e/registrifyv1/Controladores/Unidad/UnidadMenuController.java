package com.e.registrifyv1.Controladores.Unidad;

import com.e.registrifyv1.Dao.UnidadDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Rol.Rol;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.Session;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class UnidadMenuController{

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button nuevaUnidad;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnBuscarUnidadMenu;


    @FXML
    private Button btnGenerarReporte;

    private int idRol = Session.getIdRol();

    @FXML
    private TextField txtFieldMenuUnidad;
    private UnidadDAO unidadDAO;

    private UsuarioDAO usuarioDAO;

    @FXML
    private TableView<UnidadMenuModel> tablaMenuUnidad;

    @FXML
    private TableColumn<UnidadMenuModel, Integer> idUnidadColum;

    @FXML
    private TableColumn<UnidadMenuModel, String> nombreUnidadColum;

    @FXML
    private TableColumn<UnidadMenuModel, String> ubicacionUnidadColum;

    @FXML
    private void initialize() {
        unidadDAO = new UnidadDAO();
        usuarioDAO = new UsuarioDAO();

        configurarColumnas();
        configurarAccesosPorRol(idRol);

    }

    private void configurarAccesosPorRol(int idRol) {

        // Activar accesos basados en el rol
        switch (idRol) {
            case Rol.ADMINISTRADOR:
                // El administrador tiene acceso a todo
                btnSalir.setDisable(false);
                btnEliminar.setDisable(false);
                nuevaUnidad.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarUnidadMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.SUPERVISOR:
                // El supervisor tiene acceso a todo menos a btnEliminar y btnAgregar
                btnSalir.setDisable(false);
                btnEliminar.setDisable(true);
                nuevaUnidad.setDisable(true);
                btnModificar.setDisable(false);
                btnBuscarUnidadMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.USUARIO:
                // El usuario tiene acceso a todo menos a btnEliminar y btnConfiguracion
                btnSalir.setDisable(false);
                btnEliminar.setDisable(true);
                nuevaUnidad.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarUnidadMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
        }
    }


    private void configurarColumnas(){
        nombreUnidadColum.setCellFactory(tc -> {
            TableCell<UnidadMenuModel, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    UnidadMenuModel rowData = cell.getTableView().getItems().get(cell.getIndex());
                    abrirFormularioModificarUnidad(rowData);
                }
            });
            return cell;
        });
    }
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    private void btnBuscarUnidadAction(ActionEvent event) {
        String valorBusqueda = txtFieldMenuUnidad.getText();

        ObservableList<UnidadMenuModel> unidad = unidadDAO.buscarUnidad(valorBusqueda);
        cargarUnidadesEnTableView(unidad);
    }

    private void cargarUnidadesEnTableView(ObservableList<UnidadMenuModel> unidad) {
        if (unidad != null && !unidad.isEmpty()) {
            tablaMenuUnidad.setItems(unidad);
            idUnidadColum.setCellValueFactory(new PropertyValueFactory<>("idUnidad"));
            nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
            ubicacionUnidadColum.setCellValueFactory(new PropertyValueFactory<>("ubicacionUnidad"));
        } else {
            tablaMenuUnidad.getItems().clear();
        }
    }

    @FXML //ABRIR FORMULARIO PARA AGREGAR UNIDAD
    public void menuAgregarUnidad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/AgregarUnidadView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar unidad");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML //ABRIR FORMULARIO PARA MODIFICAR LA UNIDAD
    private void abrirFormularioModificarUnidad(UnidadMenuModel unidad) {
        try {
            //ABRE EL FORMULARIO DE LA UNIDAD PARA SER MODIFICADA
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/ModificarUnidadView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario de modificación
            ModificarUnidadController controller = loader.getController();

            // Pasar los datos de la unidad seleccionado al controlador del formulario de modificación
            controller.inicializarDatosModificacion(unidad);

            // Mostrar el formulario de modificación
            Stage stage = new Stage();
            stage.setTitle("Modificar Unidad");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModificarUnidadButtonAction(ActionEvent event) {
        UnidadMenuModel unidadSeleccionada = obtenerUnidadSeleccionada();

        if(unidadSeleccionada != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/ModificarUnidadView.fxml"));
                Parent root = loader.load();
                ModificarUnidadController controller = loader.getController();
                controller.inicializarDatosModificacion(unidadSeleccionada);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al modificar la unidad");
            alertError.setContentText("Selecciona una unidad antes de modificarla.");
            alertError.show();
        }
    }

    @FXML
    private void handleBtnBajaClick(ActionEvent event) {
        UnidadMenuModel unidadSeleccionada = obtenerUnidadSeleccionada();

        if (unidadSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de dar de baja la unidad. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = unidadDAO.bajaUnidad(unidadSeleccionada.getIdUnidad());

                    if (bajaExitosa) {
                        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        alertConfirmacion.setTitle("Confirmación");
                        alertConfirmacion.setHeaderText("¡Unidad eliminada correctamente!");
                        alertConfirmacion.show();
                        actualizarTableView();
                        // Realiza acciones adicionales después de dar de baja si es necesario
                    } else {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error al dar de baja");
                        alertError.setContentText("Hubo un problema al eliminar la unidad.");
                        alertError.show();
                    }
                } catch (Exception e) {
                    // Imprime la excepción en la consola
                    e.printStackTrace();
                }
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al dar de baja");
            alertError.setContentText("Selecciona una unidad antes de dar de baja.");
            alertError.show();
        }
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuUnidad.getText();
        ObservableList<UnidadMenuModel> unidad = unidadDAO.buscarUnidad(valorBusqueda);
        cargarUnidadesEnTableView(unidad);
    }

    private UnidadMenuModel obtenerUnidadSeleccionada() {
        // Lógica para obtener la unidad seleccionado
        return tablaMenuUnidad.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void handleGenerarReporte(ActionEvent event) {
        try {
            // Obtén la lista actual de usuarios en la TableView
            ObservableList<UnidadMenuModel> unidad = tablaMenuUnidad.getItems();

            // Cargar el diseño del informe desde un archivo jrxml
            // Cambia la ruta según la ubicación real de tu archivo de diseño
            InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_unidades.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Crear una fuente de datos para el informe utilizando JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(unidad);

            // Parámetros del informe (puedes agregar más según tus necesidades)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("UnidadDataSource", dataSource);

            // Compilar y llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtén la marca de tiempo actual para el nombre único del archivo
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new Date());

            // Construye el nombre del archivo con la marca de tiempo
            String pdfFileName = "src/main/resources/ReportesGenerados/ReportesUnidad/registrify_report_unidades_" + timeStamp + ".pdf";

            // Guardar el informe como un archivo PDF en la carpeta ReportesGenerados
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
}
