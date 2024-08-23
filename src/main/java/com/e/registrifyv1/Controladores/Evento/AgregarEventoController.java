package com.e.registrifyv1.Controladores.Evento;

import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class  AgregarEventoController implements Initializable {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    /*@FXML
    private TextField txbIdGendarme;*/
    @FXML
    private ComboBox<String> comboGendarme;
    @FXML
    private ComboBox<String> comboUnidad;
    @FXML
    private TextField txbDescripcionEvento;
    @FXML
    private TextField txbFechaEvento;
    @FXML
    private TextField txbEstadoEvento;


    private Map<String, Integer> unidadMap = new HashMap<>();
    private Map<String, Integer> gendarmeMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void handleConfirmarButton(ActionEvent event) {

        // Validación de campos vacíos
        if (comboGendarme.getSelectionModel().getSelectedItem() == null) {
            mostrarMensaje(false, "Por favor seleccione un gendarme.");
            return;
        }
        if (comboUnidad.getSelectionModel().getSelectedItem() == null) {
            mostrarMensaje(false, "Por favor seleccione una unidad.");
            return;
        }
        if (txbDescripcionEvento.getText().trim().isEmpty()) {
            mostrarMensaje(false, "El campo Descripción no puede estar vacío.");
            return;
        }
        if (txbFechaEvento.getText().trim().isEmpty()) {
            mostrarMensaje(false, "El campo Fecha no puede estar vacío.");
            return;
        }
        if (txbEstadoEvento.getText().trim().isEmpty()) {
            mostrarMensaje(false, "El campo Estado no puede estar vacío.");
            return;
        }

        int idEvento = 0;
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);
        String descripcion = txbDescripcionEvento.getText().trim();
        String fecha = txbFechaEvento.getText().trim();
        int estado = Integer.parseInt(txbEstadoEvento.getText().trim());

        EventoDiarioModel nuevoEvento = new EventoDiarioModel(idEvento, idUnidad, idGendarme, descripcion, fecha, estado);

        nuevoEvento.setIdEvento(idEvento);
        nuevoEvento.setIdUnidad(idUnidad);
        nuevoEvento.setIdGendarme(idGendarme);
        nuevoEvento.setDescrEvento(descripcion);
        nuevoEvento.setFechaEvento(fecha);
        nuevoEvento.setEstado(estado);

        EventoDAO eventoDAO1 = new EventoDAO();
        boolean carga = eventoDAO1.insertarEvento(nuevoEvento);
        mostrarMensaje(carga);
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void mostrarMensaje(boolean exito, String mensaje) {
        Alert alert = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(exito ? "Éxito" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        if (exito) {
            comboGendarme.getSelectionModel().clearSelection();
            comboUnidad.getSelectionModel().clearSelection();
            comboGendarme.setPromptText("----Seleccione Gendarme----");
            comboUnidad.setPromptText("----Seleccione Unidad----");

        }

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }

    private void mostrarMensaje(boolean carga) {
        Alert alert = new Alert(carga ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(carga ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (carga) {
            alert.setContentText("El Evento se insertó correctamente.");
            txbDescripcionEvento.clear();
            txbFechaEvento.clear();
            txbEstadoEvento.clear();


        } else {
            alert.setContentText("No se pudo insertar el Evento.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
}
