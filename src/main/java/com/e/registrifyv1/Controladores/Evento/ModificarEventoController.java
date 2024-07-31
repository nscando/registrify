package com.e.registrifyv1.Controladores.Evento;
import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import  com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @FXML
    private ComboBox<String> comboUnidad;

    @FXML
    private ComboBox<String> comboGendarme;

    private Map<String, Integer> unidadMap = new HashMap<>();
    private Map<String, Integer> gendarmeMap = new HashMap<>();

    public void initialize () {

        // Aquí puedes hacer cualquier inicialización adicional
        // Por ejemplo, llenar los ComboBox con opciones, etc.
        // hacer los dropdown list con nombres de gendarmes y de unidad

    }

    public void inicializarDatosModificacion(EventoDiarioModel evento){
        this.evento = evento;
        this.idEventoSeleccionado = evento.getIdEvento();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            List<UnidadMenuModel> unidades = usuarioDAO.obtenerOpcionesUnidad();
            ObservableList<String> unidadesNombres = FXCollections.observableArrayList();

            for (UnidadMenuModel unidad : unidades) {
                unidadMap.put(unidad.getNombreUnidad(), unidad.getIdUnidad());
                unidadesNombres.add(unidad.getNombreUnidad());
            }

            comboUnidad.setItems(unidadesNombres);
            comboUnidad.setPromptText("----Seleccione Unidad----");



            List<UsuarioModel> usuarios = usuarioDAO.buscarUsuariosActivos();
            ObservableList<String> usuariosNombres = FXCollections.observableArrayList();

            for (UsuarioModel usuario : usuarios) {
                gendarmeMap.put(usuario.getNombre() + " " + usuario.getApellido() + " " + usuario.getDni(), usuario.getIdGendarme());
                usuariosNombres.add(usuario.getNombre() + " " + usuario.getApellido() + " " + usuario.getDni());
            }
            comboGendarme.setItems(usuariosNombres);
            comboGendarme.setPromptText("----Seleccione Gendarme----");

            txbDescripcionEvento.setText(evento.getDescrEvento());
            txbFechaEvento.setText(evento.getFechaEvento());
            txbEstadoEvento.setText(String.valueOf(evento.getEstado()));// todo ???


        } catch (SQLException e) {
            e.printStackTrace();
        }



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
                    mostrarAlerta("Actualización Exitosa", "Los datos del evento han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if (eventoDiarioController != null) {
                    eventoDiarioController.actualizarTableView();
                }

                Stage stage = (Stage) txbDescripcionEvento.getScene().getWindow(); //todo cambiar a idGendarme?
                stage.close();
            }
            else{
                mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del evento.");
            }
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }


    private EventoDiarioModel obtenerDatosFormulario() {
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);

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
        comboGendarme.getSelectionModel().clearSelection();
        comboUnidad.getSelectionModel().clearSelection();
        txbEstadoEvento.clear();
        txbDescripcionEvento.clear();
        txbFechaEvento.clear();
    }


}
