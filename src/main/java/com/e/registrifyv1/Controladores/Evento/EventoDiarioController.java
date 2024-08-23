package com.e.registrifyv1.Controladores.Evento;

import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import com.e.registrifyv1.Modelos.Rol.Rol;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventoDiarioController {

    @FXML
    private TableView<EventoDiarioModel> tablaMenuEvento;

    @FXML
    private DatePicker datePickerDesde;

    @FXML
    private DatePicker datePickerHasta;

    @FXML
    private CheckBox cBoxIncluirEventoBaja;

    @FXML
    private TableColumn<EventoDiarioModel, Integer> idEventoColumn;

    @FXML
    private TableColumn<EventoDiarioModel, String> gendarmeColum;

    @FXML
    private TableColumn<EventoDiarioModel, String> nombreUnidadColum;

    @FXML
    private TableColumn<EventoDiarioModel, String> descrEventoColumn;

    @FXML
    private TableColumn<EventoDiarioModel, String> fechaEventoColumn;

    @FXML
    private TableColumn<EventoDiarioModel, Integer> estadoEventoColumn;

    @FXML
    private TextField txtFieldMenuEvento;

    @FXML
    private EventoDAO eventoDAO;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnBajaEvnt;

    @FXML
    private Button agregarEvento;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnBuscarEventoMenu;

    @FXML
    private Button btnGenerarReporte;

    private Map<String, Stage> ventanasAbiertas = new HashMap<>();


    private int idRol = Session.getIdRol();

    @FXML
    private void initialize() {
        eventoDAO = new EventoDAO();
        configurarColumnas();
        configurarAccesosPorRol(idRol);
    }

    private void configurarAccesosPorRol(int idRol) {
        switch (idRol) {
            case Rol.ADMINISTRADOR:
                btnSalir.setDisable(false);
                btnBajaEvnt.setDisable(false);
                agregarEvento.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarEventoMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.SUPERVISOR:
                btnSalir.setDisable(false);
                btnBajaEvnt.setDisable(true);
                agregarEvento.setDisable(true);
                btnModificar.setDisable(false);
                btnBuscarEventoMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
            case Rol.USUARIO:
                btnSalir.setDisable(false);
                btnBajaEvnt.setDisable(true);
                agregarEvento.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarEventoMenu.setDisable(false);
                btnGenerarReporte.setDisable(false);
                break;
        }
    }

    private void configurarColumnas() {
        idEventoColumn.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        gendarmeColum.setCellValueFactory(cellData -> {
            EventoDiarioModel evento = cellData.getValue();
            String gendarmeInfo = evento.getNombreGendarme() + " " + evento.getApellidoGendarme() + "\nDNI: " + evento.getDniGendarme();
            return new SimpleStringProperty(gendarmeInfo);
        });
        nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
        descrEventoColumn.setCellValueFactory(new PropertyValueFactory<>("descrEvento"));
        fechaEventoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaEvento"));
        estadoEventoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        estadoEventoColumn.setCellFactory(column -> new TableCell<EventoDiarioModel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item == 1 ? "Activo" : "Inactivo");
                }
            }
        });
    }

    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));    }

    @FXML
    private void btnBuscarEventoAction(ActionEvent event) {
        String valorBusqueda = txtFieldMenuEvento.getText();
        boolean incluirBaja = cBoxIncluirEventoBaja.isSelected(); // True si se selecciona el checkbox, false si no.

        LocalDate localDateDesde = datePickerDesde.getValue();
        LocalDate localDateHasta = datePickerHasta.getValue();

        Date fechaDesde = (localDateDesde != null) ? Date.valueOf(localDateDesde) : null;
        Date fechaHasta = (localDateHasta != null) ? Date.valueOf(localDateHasta) : null;

        ObservableList<EventoDiarioModel> eventos;

        if (incluirBaja) {
            // Traer todos los eventos, incluyendo los inactivos
            eventos = eventoDAO.buscarEventoConFecha(valorBusqueda, true, fechaDesde, fechaHasta);
        } else {
            // Traer solo los eventos activos
            eventos = eventoDAO.buscarEventoConFecha(valorBusqueda, false, fechaDesde, fechaHasta);
        }

        cargarEventosEnTableView(eventos);
    }


    private void cargarEventosEnTableView(ObservableList<EventoDiarioModel> eventos) {
        if (eventos != null && !eventos.isEmpty()) {
            tablaMenuEvento.setItems(eventos);
        } else {
            tablaMenuEvento.getItems().clear();
        }
    }

    @FXML
    public void menuAgregarEvento(ActionEvent event) {
        String menuKey = "AgregarEventoMenu"; // Identificador único para el menú de vehículos

        if (ventanasAbiertas.containsKey(menuKey)) {
            // Si la ventana ya está abierta, no permitir abrir otra
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El menú ya está abierto.");
            alert.show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EventoDiario/AgregarEventoView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Agregar Evento");
            stage.setScene(new Scene(root));

            // Almacenar la ventana abierta en el Map
            ventanasAbiertas.put(menuKey, stage);

            // Agregar un listener para removerla cuando se cierre
            stage.setOnCloseRequest(e -> ventanasAbiertas.remove(menuKey));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private EventoDiarioModel obtenerEventoSeleccionado() {
        return tablaMenuEvento.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleBtnBajaEvnt(ActionEvent event) {
        EventoDiarioModel eventoSeleccionado = obtenerEventoSeleccionado();

        if (eventoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de eliminar este Evento. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = eventoDAO.bajaEvento(eventoSeleccionado.getIdEvento());

                    if (bajaExitosa) {
                        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        alertConfirmacion.setTitle("Confirmación");
                        alertConfirmacion.setHeaderText("¡Evento eliminado correctamente!");
                        alertConfirmacion.show();
                        actualizarTableView();
                    } else {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error al dar de baja");
                        alertError.setContentText("Hubo un problema al dar de baja el Evento.");
                        alertError.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al dar de baja");
            alertError.setContentText("Selecciona un Evento antes de dar de baja.");
            alertError.show();
        }
    }

    public void handleModificacionEvento(ActionEvent event) {
        EventoDiarioModel eventoSeleccionado = obtenerEventoSeleccionado();
        if (eventoSeleccionado != null) {
            String menuKey = "ModificarVehiculoMenu"; // Identificador único para el vehículo a modificar
            if (ventanasAbiertas.containsKey(menuKey)) {
                // Si la ventana ya está abierta, no permitir abrir otra
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("La ventana de modificación ya está abierta.");
                alert.show();
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EventoDiario/ModificarEventoView.fxml"));
                Parent root = (Parent) loader.load();
                ModificarEventoController controller = loader.getController();
                controller.inicializarDatosModificacion(eventoSeleccionado);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                // Almacenar la ventana abierta en el Map
                ventanasAbiertas.put(menuKey, stage);

                // Agregar un listener para removerla cuando se cierre
                stage.setOnCloseRequest(e -> ventanasAbiertas.remove(menuKey));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al seleccionar");
            alertError.setContentText("Selecciona un Evento antes de modificar.");
            alertError.show();
        }
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuEvento.getText();
        ObservableList<EventoDiarioModel> evento = eventoDAO.buscarEvento(valorBusqueda);
        cargarEventosEnTableView(evento);
    }

    public void handleGenerarReporte(ActionEvent event) {
        try {
            ObservableList<EventoDiarioModel> eventos = tablaMenuEvento.getItems();

            InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_eventos_diarios.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(eventos);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("EventosDiariosDataSource", dataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new java.util.Date());

            String pdfFileName = "src/main/resources/ReportesGenerados/ReportesEventos/registrify_report_eventos_diarios_" + timeStamp + ".pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Informe generado correctamente", "El informe se ha guardado en: " + pdfFileName);
        } catch (JRException e) {
            e.printStackTrace();
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
