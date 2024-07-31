package com.e.registrifyv1.Controladores.Evento;

import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
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
    private TextField txbDescripcionEvento;
    @FXML
    private TextField txbFechaEvento;
    @FXML
    private TextField txbEstadoEvento;

    @FXML
    private ComboBox<String> comboUnidad;
    @FXML
    private ComboBox<String> comboGendarme;

    private EventoDiarioModel evento;
    private Map<String, Integer> unidadMap = new HashMap<>();
    private Map<String, Integer> gendarmeMap = new HashMap<>();
    private EventoDiarioController eventoDiarioController;
    private EventoDAO eventoDAO;

    @FXML
    private TableView<EventoDiarioModel> tablaMenuEvento;

    public void initialize() {
        // Método vacío: aquí se puede agregar inicialización adicional si es necesario
    }

    public void inicializarDatosModificacion(EventoDiarioModel evento) {
        this.evento = evento;

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
            txbEstadoEvento.setText(String.valueOf(evento.getEstado()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmarModificacion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Modificación");
        alert.setHeaderText("Está a punto de modificar los datos del evento.");
        alert.setContentText("¿Está seguro de que desea continuar?");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EventoDiarioModel updateEvento = obtenerDatosFormulario();

            if (updateEvento != null) {
                EventoDAO eventoDAO = new EventoDAO();
                boolean exito = eventoDAO.actualizarEventoDiario(updateEvento);
                if (exito) {
                    mostrarAlerta("Actualización Exitosa", "Los datos del evento han sido actualizados correctamente.");
                    limpiarCamposFormulario();

                    // Llamada para actualizar el TableView en el controlador de evento diario
                    if (eventoDiarioController != null) {
                        eventoDiarioController.actualizarTableView();
                    }

                    Stage stage = (Stage) btnConfirmar.getScene().getWindow();
                    stage.close();
                } else {
                    mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del evento.");
                }
            } else {
                mostrarAlerta("Error de Validación", "Por favor, complete todos los campos correctamente.");
            }
        }
    }

    public void actualizarTableView() {
        ObservableList<EventoDiarioModel> eventos = eventoDAO.buscarEvento(""); // Realiza una búsqueda con un criterio vacío para obtener todos los eventos
        tablaMenuEvento.setItems(eventos);
    }

    private EventoDiarioModel obtenerDatosFormulario() {
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        if (gendarmeSeleccionado == null || unidadSeleccionada == null) {
            mostrarAlerta("Error de Validación", "Debe seleccionar un gendarme y una unidad.");
            return null;
        }

        int idGendarme = gendarmeMap.getOrDefault(gendarmeSeleccionado, -1);
        int idUnidad = unidadMap.getOrDefault(unidadSeleccionada, -1);

        if (idGendarme == -1 || idUnidad == -1) {
            mostrarAlerta("Error de Validación", "ID de gendarme o unidad no válido.");
            return null;
        }

        String descrEvento = txbDescripcionEvento.getText().trim();
        String fechaEvento = txbFechaEvento.getText().trim();
        int estadoEvento;

        try {
            estadoEvento = Integer.parseInt(txbEstadoEvento.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Validación", "El estado del evento debe ser un número entero.");
            return null;
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

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setEventoDiarioController(EventoDiarioController eventoDiarioController) {
        this.eventoDiarioController = eventoDiarioController;
    }
}
