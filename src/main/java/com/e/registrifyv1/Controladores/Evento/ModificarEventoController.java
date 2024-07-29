package com.e.registrifyv1.Controladores.Evento;
import com.e.registrifyv1.Dao.EventoDAO;
import  com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;
public class ModificarEventoController {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txbIdGendarme;
    @FXML
    private TextField txbIdUnidad;
    @FXML
    private TextField txbDescripcionEvento;
    @FXML
    private TextField txbFechaEvento;
    @FXML
    private TextField txbEstadoEvento;

    private EventoDiarioModel evento;

    //creacion de la tabla
    @FXML
    private TableView<EventoDiarioModel> tablaMenuEvento;

    private EventoDiarioController eventoDiarioController;
    private int idEventoSeleccionado;

    public void initialize () {

        // Aquí puedes hacer cualquier inicialización adicional
        // Por ejemplo, llenar los ComboBox con opciones, etc.
        // hacer los dropdown list con nombres de gendarmes y de unidad

    }

    public void inicializarDatosModificacion(EventoDiarioModel evento){
        this.evento = evento;
        this.idEventoSeleccionado = evento.getIdEvento();
        txbIdGendarme.setText(String.valueOf(evento.getIdGendarme()));
        txbIdUnidad.setText(String.valueOf(evento.getIdUnidad()));
        txbDescripcionEvento.setText(evento.getDescrEvento());
        txbFechaEvento.setText(evento.getFechaEvento());
        txbEstadoEvento.setText(String.valueOf(evento.getEstado()));// todo ???
    }

    public void setTablaMenuInventario(TableView<EventoDiarioModel> tablaMenuEvento){
        this.tablaMenuEvento = tablaMenuEvento;
    }
    public void setEventoDiarioController (EventoDiarioController eventoDiarioController){
        this.eventoDiarioController = eventoDiarioController;
    }

    @FXML
    private void handleConfirmarModificacion(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Modificación");
        alert.setHeaderText("Está a punto de modificar los datos del evento.");
        alert.setContentText("¿Está seguro de que desea continuar?");

        ButtonType buttonTypeConfirmar = new ButtonType("Confirmar");
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeConfirmar, buttonTypeCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeConfirmar){
            EventoDiarioModel updateEvento = obtenerDatosFormulario();

            if(updateEvento != null) {
                EventoDAO eventoDAO = new EventoDAO();

                boolean exito = eventoDAO.actualizarEventoDiario(updateEvento);

                if (exito) {
                    mostrarAlerta("Actualización Exitosa", "Los datos del arma han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if (eventoDiarioController != null) {
                    eventoDiarioController.actualizarTableView();
                }

                Stage stage = (Stage) txbDescripcionEvento.getScene().getWindow(); //todo cambiar a idGendarme?
                stage.close();
            }
            else{
                mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del armamento.");
            }
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }


    private EventoDiarioModel obtenerDatosFormulario() {
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        String  descrEvento= txbDescripcionEvento.getText();
        String fechaEvento = txbFechaEvento.getText();
        int estadoEvento = Integer.parseInt(txbEstadoEvento.getText());

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_GENDARME no es un número válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_UNIDAD no es un número válido.");
        }

        return new EventoDiarioModel(evento.getIdEvento(), idUnidad, idGendarme, descrEvento, fechaEvento, estadoEvento);
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCamposFormulario() {
        txbIdGendarme.clear();
        txbIdUnidad.clear();
        txbEstadoEvento.clear();
        txbDescripcionEvento.clear();
        txbFechaEvento.clear();
    }


}
