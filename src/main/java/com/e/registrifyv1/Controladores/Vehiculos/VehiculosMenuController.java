package com.e.registrifyv1.Controladores.Vehiculos;

import com.e.registrifyv1.Controladores.Arma.ModificarArmaController;
import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Modelos.Rol.Rol;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import com.e.registrifyv1.Utiles.Session;
import javafx.beans.property.SimpleStringProperty;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehiculosMenuController {

    @FXML
    private TableView<VehiculosModel> tablaMenuVehiculo;

    @FXML
    private TableColumn<VehiculosModel, Integer> idVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> nombreUnidadColum;

    @FXML
    private TableColumn<VehiculosModel, String> gendarmeColum;

    @FXML
    private TableColumn<VehiculosModel, String> tipoVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> marcaVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> modeloVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> patenteColum;

    @FXML
    private TableColumn<VehiculosModel, Integer> kilometrajeColum;

    @FXML
    private TextField txtFieldMenuVehiculo;

    @FXML
    private VehiculoDAO vehiculoDAO;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnBajaVehiculo;

    @FXML
    private Button nuevoVehiculo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnBuscarVehiculoMenu;

    @FXML
    private Button btnGenerarReporte;

    private int idRol = Session.getIdRol();


    @FXML
    private void initialize(){
        vehiculoDAO=new VehiculoDAO();
        configurarColumnas();
        configurarAccesosPorRol(idRol);
    }

    private void configurarAccesosPorRol(int idRol) {

        // Activar accesos basados en el rol
        switch (idRol) {
            case Rol.ADMINISTRADOR:
                // El administrador tiene acceso a todo
                btnSalir.setDisable(false);
                btnBajaVehiculo.setDisable(false);
                nuevoVehiculo.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarVehiculoMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.SUPERVISOR:
                // El supervisor tiene acceso a todo menos a btnEliminar y btnAgregar
                btnSalir.setDisable(false);
                btnBajaVehiculo.setDisable(true);
                nuevoVehiculo.setDisable(true);
                btnModificar.setDisable(false);
                btnBuscarVehiculoMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.USUARIO:
                // El usuario tiene acceso a todo menos a btnEliminar y btnConfiguracion
                btnSalir.setDisable(false);
                btnBajaVehiculo.setDisable(true);
                nuevoVehiculo.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarVehiculoMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
        }
    }

    private void configurarColumnas() {
        idVehiculoColum.setCellFactory(tc -> {
        TableCell<VehiculosModel, Integer> cell = new TableCell<VehiculosModel, Integer>() {
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
    }
// todo> creo que este metodo es redundante.
    @FXML
    public void menuVehiculos(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/AgregarVehiculoForm.fxml")); //corregir.
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Vehiculos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnBuscarVehiculosAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuVehiculo.getText();
        ObservableList<VehiculosModel> vehiculo = vehiculoDAO.buscarVehiculo(valorBusqueda);
        cargarVehiculosEnTableView(vehiculo);
    }

    private void cargarVehiculosEnTableView(ObservableList<VehiculosModel> vehiculo) {

        if (vehiculo != null && !vehiculo.isEmpty()) {

            tablaMenuVehiculo.setItems(vehiculo);

            idVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
            nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
            gendarmeColum.setCellValueFactory(cellData -> {
                VehiculosModel vehiculoItem = cellData.getValue();
                String gendarmeInfo = vehiculoItem.getNombreGendarme() + " " + vehiculoItem.getApellidoGendarme() + " " + vehiculoItem.getDniGendarme();
                return new SimpleStringProperty(gendarmeInfo);
            });
            tipoVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
            marcaVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("marcaVehiculo"));
            modeloVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            patenteColum.setCellValueFactory(new PropertyValueFactory<>("patente"));
            kilometrajeColum.setCellValueFactory(new PropertyValueFactory<>("kilometraje"));

        } else {
            tablaMenuVehiculo.getItems().clear();
        }
    }

    @FXML //ABRIR FORMULARIO PARA AGREGAR Vehiculo-Es necesario???
    // porque tengo creo que lo abre igual sin esto y si es asi, porque
    public void menuAgregarVehiculo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/AgregarVehiculoForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Vehiculo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //todo creo que esto no hace un pingo, probar de borrarlo.
    private void abrirFormularioModificarVehiculo(VehiculosModel vehiculo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/ModificarVehiculoView.fxml"));
            Parent root = loader.load();
            ModificarVehiculoController controller = loader.getController();
            controller.setTablaMenuVehiculo(tablaMenuVehiculo);
            controller.setVehiculosMenuController(this);
            controller.inicializarDatosModificacion(vehiculo);
            Stage stage = new Stage();
            stage.setTitle("Modificar Vehiculo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleModificacionButtonAction(ActionEvent event) {
        VehiculosModel vehiculoSeleccionado = tablaMenuVehiculo.getSelectionModel().getSelectedItem();
        if (vehiculoSeleccionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/ModificarVehiculoForm.fxml"));
                Parent root = (Parent) loader.load();
                ModificarVehiculoController controller = loader.getController();
                controller.inicializarDatosModificacion(vehiculoSeleccionado);
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
            alertError.setContentText("Selecciona un Vehiculo antes de modificar.");
            alertError.show();
        }
    }

    private VehiculosModel obtenerVehiculoSeleccionado() {
        // Lógica para obtener el inventario seleccionado
        return tablaMenuVehiculo.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleBtnBajaVehiculo(ActionEvent event) {
        VehiculosModel vehiculoSeleccionado = obtenerVehiculoSeleccionado();

        if (vehiculoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de dar de eliminar ese objeto. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = vehiculoDAO.bajaVehiculo(vehiculoSeleccionado.getIdVehiculo());

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
        String valorBusqueda = txtFieldMenuVehiculo.getText();
        ObservableList<VehiculosModel> vehiculo = vehiculoDAO.buscarVehiculo(valorBusqueda);
        cargarVehiculosEnTableView(vehiculo);
    }


    public void handleGenerarReporte(ActionEvent event) {
        try {
            // Obtén la lista actual de usuarios en la TableView
            ObservableList<VehiculosModel> vehiculo = tablaMenuVehiculo.getItems();

            // Cargar el diseño del informe desde un archivo jrxml
            // Cambia la ruta según la ubicación real de tu archivo de diseño
            InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_vehiculos.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Crear una fuente de datos para el informe utilizando JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vehiculo);

            // Parámetros del informe (puedes agregar más según tus necesidades)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("VehiculosDataSource", dataSource);

            // Compilar y llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtén la marca de tiempo actual para el nombre único del archivo
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new Date());

            // Construye el nombre del archivo con la marca de tiempo
            String pdfFileName = "src/main/resources/ReportesGenerados/ReportesVehiculos/registrify_report_vehiculos_" + timeStamp + ".pdf";

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
