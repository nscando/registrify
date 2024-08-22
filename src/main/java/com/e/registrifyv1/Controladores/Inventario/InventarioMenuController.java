package com.e.registrifyv1.Controladores.Inventario;


import com.e.registrifyv1.Dao.InventarioDAO;


import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;

import com.e.registrifyv1.Modelos.Rol.Rol;
import com.e.registrifyv1.Utiles.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
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


public class InventarioMenuController {

    @FXML
    private TableView<InventarioModel> tablaMenuInventario;

    @FXML
    private TableColumn<InventarioModel, Integer> idAccesorioColum;

    @FXML
    private TableColumn<InventarioModel, String> nombreUnidadColum;

    @FXML
    private TableColumn<InventarioModel, String> gendarmeColum;

    @FXML
    private TableColumn<InventarioModel, String> nombreAccesorioColum;

    @FXML
    private TableColumn<InventarioModel, String> descrAccesorioColum;

    @FXML
    private TextField txtFieldMenuInventario;

    @FXML
    private InventarioDAO inventarioDAO;

    @FXML
    private Button btnSalir;


    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnBuscarInventarioMenu;



    @FXML
    private Button btnGenerarReporte;

    private int idRol = Session.getIdRol();

    @FXML
    private void initialize(){
        inventarioDAO=new InventarioDAO();
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
                btnAgregar.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarInventarioMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.SUPERVISOR:
                // El supervisor tiene acceso a todo menos a btnEliminar y btnAgregar
                btnSalir.setDisable(false);
                btnEliminar.setDisable(true);
                btnAgregar.setDisable(true);
                btnModificar.setDisable(false);
                btnBuscarInventarioMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.USUARIO:
                // El usuario tiene acceso a todo menos a btnEliminar y btnConfiguracion
                btnSalir.setDisable(false);
                btnEliminar.setDisable(true);
                btnAgregar.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarInventarioMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
        }
    }

    private void configurarColumnas() {

        idAccesorioColum.setCellValueFactory(new PropertyValueFactory<>("idAccesorio"));

        gendarmeColum.setCellValueFactory(cellData -> {
            InventarioModel item = cellData.getValue();
            String gendarmeInfo = item.getNombreGendarme() + " " + item.getApellidoGendarme() + " " + item.getDniGendarme();
            return new SimpleStringProperty(gendarmeInfo);
        });

        nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
        nombreAccesorioColum.setCellValueFactory(new PropertyValueFactory<>("nombreAccesorio"));
        descrAccesorioColum.setCellValueFactory(new PropertyValueFactory<>("descrAccesorio"));
        /*
        idGendarmeColum.setCellFactory(tc -> {
            TableCell<InventarioModel, Integer> cell = new TableCell<InventarioModel, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item != null ? item.toString() : "");
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    VehiculosModel rowData = cell.getTableView().getItems().get(cell.getIndex());
                    abrirFormularioModificarVehiculo(rowData);
                }
            });
            return cell;
        });

             */
    }

    @FXML
    private void btnBuscarInventarioAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuInventario.getText();
        ObservableList<InventarioModel> inventario = inventarioDAO.buscarInventario(valorBusqueda);
        cargarInventariosEnTableView(inventario);
    }

    private void cargarInventariosEnTableView(ObservableList<InventarioModel> inventario) {

        if (inventario != null && !inventario.isEmpty()) {
            tablaMenuInventario.setItems(inventario);
        } else {
            tablaMenuInventario.getItems().clear();
        }
    }

    @FXML
    public void menuAgregarInventario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Inventario/AgregarInventarioView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Inventario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleModificacionInventario(ActionEvent event) {
        InventarioModel inventarioSeleccionado = tablaMenuInventario.getSelectionModel().getSelectedItem();
        if (inventarioSeleccionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Inventario/ModificarInventarioForm.fxml"));
                Parent root = (Parent) loader.load();
                ModificarInventarioController controller = loader.getController();
                controller.inicializarDatosModificacion(inventarioSeleccionado);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al seleccionar");
            alertError.setContentText("Selecciona un Objeto antes de modificar.");
            alertError.show();
        }
    }

    private InventarioModel obtenerInventarioSeleccionado() {
        // Lógica para obtener el inventario seleccionado
        return tablaMenuInventario.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleBtnBajaInv(ActionEvent event) {
        InventarioModel inventarioSeleccionado = obtenerInventarioSeleccionado();

        if (inventarioSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de dar de eliminar ese objeto. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = inventarioDAO.bajaInventario(inventarioSeleccionado.getIdAccesorio());

                    if (bajaExitosa) {
                        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        alertConfirmacion.setTitle("Confirmación");
                        alertConfirmacion.setHeaderText("¡Objeto eliminado del inventario correctamente!");
                        alertConfirmacion.show();
                        actualizarTableView();
                        // Realiza acciones adicionales después de dar de baja si es necesario
                    } else {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error al dar de baja");
                        alertError.setContentText("Hubo un problema al dar de baja el objeto.");
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
            alertError.setContentText("Selecciona un objeto antes de dar de baja.");
            alertError.show();
        }
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuInventario.getText();
        ObservableList<InventarioModel> inventario = inventarioDAO.buscarInventario(valorBusqueda);
        cargarInventariosEnTableView(inventario);
    }

    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


    private void mostrarAlerta(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    @FXML
    public void handleGenerarReporte(ActionEvent event) {
        try {
            // Obtén la lista actual de usuarios en la TableView
            ObservableList<InventarioModel> inventario = tablaMenuInventario.getItems();

            // Cargar el diseño del informe desde un archivo jrxml
            // Cambia la ruta según la ubicación real de tu archivo de diseño
            InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_inventario.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Crear una fuente de datos para el informe utilizando JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(inventario);

            // Parámetros del informe (puedes agregar más según tus necesidades)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("InventarioDataSource", dataSource);

            // Compilar y llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtén la marca de tiempo actual para el nombre único del archivo
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new Date());

            // Construye el nombre del archivo con la marca de tiempo
            String pdfFileName = "src/main/resources/ReportesGenerados/ReportesInventario/registrify_report_inventario" + timeStamp + ".pdf";

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

}

