package com.e.registrifyv1.Controladores.Evento;


import com.e.registrifyv1.Controladores.Inventario.ModificarInventarioController;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.e.registrifyv1.Controladores.Arma.ModificarArmaController;
import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
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

public class EventoDiarioController {
    @FXML
    private TableView<EventoDiarioModel> tablaMenuEvento;

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
    private void initialize(){
        eventoDAO=new EventoDAO();
        configurarColumnas();
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

   /*
        idGendarmeColum.setCellFactory(tc -> {
            TableCell<EventoDiarioModel, Integer> cell = new TableCell<EventoDiarioModel, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item != null ? item.toString() : "");
                }
            };

            //todo  doble click para modificar
            //cell.setOnMouseClicked(event -> {
              //  if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                //    EventoDiarioModel rowData = cell.getTableView().getItems().get(cell.getIndex());
              //    abrirFormularioModificarVehiculo(rowData);
               // }
           // });
            return cell;
        });
*/
    }
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void btnBuscarEventoAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuEvento.getText();
        ObservableList<EventoDiarioModel> evento = eventoDAO.buscarEvento(valorBusqueda);
        cargarEventosEnTableView(evento);
    }

    private void cargarEventosEnTableView(ObservableList<EventoDiarioModel> eventos) {

        if (eventos != null && !eventos.isEmpty()) {

            tablaMenuEvento.setItems(eventos);

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

        } else {
            tablaMenuEvento.getItems().clear();
        }
    }

    @FXML
    public void menuAgregarEvento(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EventoDiario/AgregarEventoView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Evento");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private EventoDiarioModel obtenerEventoSeleccionado() {
        // Lógica para obtener el inventario seleccionado
        return tablaMenuEvento.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleBtnBajaEvnt(ActionEvent event) {
        EventoDiarioModel eventoSeleccionado = obtenerEventoSeleccionado();

        if (eventoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de dar de eliminar ese objeto. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = eventoDAO.bajaEvento(eventoSeleccionado.getIdEvento());

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

   /* public void handleModificacionEvento(ActionEvent event) {
        EventoDiarioModel eventoSeleccionado = tablaMenuEvento.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EventoDiario/ModificarEventoView.fxml"));
                Parent root = (Parent) loader.load();
                ModificarEventoController controller = loader.getController();
                controller.inicializarDatosModificacion(eventoSeleccionado);
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
    }*/

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuEvento.getText();
        ObservableList<EventoDiarioModel> evento = eventoDAO.buscarEvento(valorBusqueda);
        cargarEventosEnTableView(evento);
    }

    public void handleGenerarReporte(ActionEvent event) {
        try {
            // Obtén la lista actual de usuarios en la TableView
            ObservableList<EventoDiarioModel> eventos = tablaMenuEvento.getItems();

            // Cargar el diseño del informe desde un archivo jrxml
            // Cambia la ruta según la ubicación real de tu archivo de diseño
            InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_eventos_diarios.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Crear una fuente de datos para el informe utilizando JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(eventos);

            // Parámetros del informe (puedes agregar más según tus necesidades)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("EventosDiariosDataSource", dataSource);

            // Compilar y llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtén la marca de tiempo actual para el nombre único del archivo
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new Date());

            // Construye el nombre del archivo con la marca de tiempo
            String pdfFileName = "src/main/resources/ReportesGenerados/ReportesEventos/registrify_report_eventos_diarios_" + timeStamp + ".pdf";

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
