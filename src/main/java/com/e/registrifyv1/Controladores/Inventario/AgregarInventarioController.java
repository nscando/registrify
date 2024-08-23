package com.e.registrifyv1.Controladores.Inventario;

import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
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

public class  AgregarInventarioController implements Initializable {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txbIdAccesorio;

    @FXML
    private TextField txbNombre;
    @FXML
    private TextField txbDescripcion;
    @FXML
    private ComboBox<String> comboGendarme;
    @FXML
    private ComboBox<String> comboUnidad;


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
        if (txbNombre.getText().trim().isEmpty()) {
            mostrarMensaje(false, "El campo Nombre no puede estar vacío.");
            return;
        }
        if (txbDescripcion.getText().trim().isEmpty()) {
            mostrarMensaje(false, "El campo Descripción no puede estar vacío.");
            return;
        }

        int idAccesorio = 0;
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);
        String nombre = txbNombre.getText().trim();
        String descripcion = txbDescripcion.getText().trim();

        InventarioModel nuevoInventario = new InventarioModel(idAccesorio, idUnidad, idGendarme, nombre, descripcion);

        nuevoInventario.setIdAccesorio(idAccesorio);
        nuevoInventario.setIdUnidad(idUnidad);
        nuevoInventario.setIdGendarme(idGendarme);
        nuevoInventario.setNombreAccesorio(nombre);
        nuevoInventario.setDescrAccesorio(descripcion);

        InventarioDAO inventarioDAO1 = new InventarioDAO();
        boolean carga = inventarioDAO1.insertarInventario(nuevoInventario);
        mostrarMensaje(carga);
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    private void mostrarMensaje(boolean carga) {
        Alert alert = new Alert(carga ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(carga ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (carga) {
            alert.setContentText("El Objeto se insertó correctamente.");
            txbNombre.clear();
            txbDescripcion.clear();

        } else {
            alert.setContentText("No se pudo insertar el Objeto.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
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
}
