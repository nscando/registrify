package com.e.registrifyv1.Controladores.Vehiculos;

import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
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

public class  AgregarVehiculoController implements Initializable {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txbIdVehiculo;
    @FXML
    private TextField txbTipoVehiculo;
    @FXML
    private TextField txbMarcaVehiculo;
    @FXML
    private TextField txbKilometrajeVehiculo;
    @FXML
    private TextField txbPatenteVehiculo;
    @FXML
    private TextField txbModeloVehiculo;
    @FXML
    private ComboBox<String> comboGendarme;
    @FXML
    private ComboBox<String> comboUnidad;
    @FXML


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

    public void handleConfirmarButton(ActionEvent event) {

        int idVehiculo = Integer.parseInt(txbIdVehiculo.getText());
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        if (gendarmeSeleccionado == null || unidadSeleccionada == null) {
            mostrarMensaje(false, "Por favor seleccione un gendarme y una unidad.");
            return;
        }

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);
        String tipoVehiculo = txbTipoVehiculo.getText();
        String marcaVehiculo = txbMarcaVehiculo.getText();
        String modeloVehiculo = txbModeloVehiculo.getText();
        String patenteVehiculo = txbPatenteVehiculo.getText();
        String kmVehiculo = txbKilometrajeVehiculo.getText();

        VehiculosModel nuevoVehiculo = new VehiculosModel(idVehiculo, idUnidad, idGendarme, tipoVehiculo, marcaVehiculo, modeloVehiculo, patenteVehiculo, kmVehiculo );

        nuevoVehiculo.setIdVehiculo(idVehiculo);
        nuevoVehiculo.setIdUnidad(idUnidad);
        nuevoVehiculo.setIdGendarme(idGendarme);
        nuevoVehiculo.setTipoVehiculo(tipoVehiculo);
        nuevoVehiculo.setMarcaVehiculo(marcaVehiculo);
        nuevoVehiculo.setModelo(modeloVehiculo);
        nuevoVehiculo.setPatente(patenteVehiculo);
        nuevoVehiculo.setKilometraje(kmVehiculo);


        VehiculoDAO vehiculoDAO1 = new VehiculoDAO();
        boolean carga = vehiculoDAO1.insertarVehiculo(nuevoVehiculo);
        mostrarMensaje(carga);
    }
    private void mostrarMensaje(boolean carga) {
        Alert alert = new Alert(carga ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(carga ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (carga) {
            alert.setContentText("El Vehiculo se insertó correctamente.");
            txbIdVehiculo.clear();
            txbTipoVehiculo.clear();
            txbMarcaVehiculo.clear();
            txbModeloVehiculo.clear();
            txbPatenteVehiculo.clear();
            txbKilometrajeVehiculo.clear();
        } else {
            alert.setContentText("No se pudo insertar el Vehiculo.");
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
