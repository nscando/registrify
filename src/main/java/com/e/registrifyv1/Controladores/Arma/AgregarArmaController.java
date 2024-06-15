package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AgregarArmaController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txbMarcaArma;

    @FXML
    private TextField txbTipoArma;

    @FXML
    private TextField txbNumeroSerieArma;

    @FXML
    private ComboBox<String> comboUnidad;

    @FXML
    private ComboBox<String> comboGendarme;

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
        stage.close();
    }

    @FXML
    private void handleConfirmarButton(ActionEvent event) {
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        if (gendarmeSeleccionado == null || unidadSeleccionada == null) {
            mostrarMensaje(false, "Por favor seleccione un gendarme y una unidad.");
            return;
        }

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);

        String marcaArma = txbMarcaArma.getText();
        String tipoArma = txbTipoArma.getText();
        String numeroSerieArma = txbNumeroSerieArma.getText();

        ArmaMenuModel nuevaArma = new ArmaMenuModel(0, idGendarme, idUnidad, marcaArma, tipoArma, numeroSerieArma);

        ArmaDAO armaDAO1 = new ArmaDAO();
        boolean exito = armaDAO1.insertarArma(nuevaArma);

        mostrarMensaje(exito, exito ? "El arma se insertó correctamente." : "No se pudo insertar el arma.");
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
            txbMarcaArma.clear();
            txbTipoArma.clear();
            txbNumeroSerieArma.clear();
        }

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
}
