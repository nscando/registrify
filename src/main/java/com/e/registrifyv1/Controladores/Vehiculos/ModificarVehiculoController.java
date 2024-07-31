package com.e.registrifyv1.Controladores.Vehiculos;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import  com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
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

public class ModificarVehiculoController {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    @FXML
   private TextField txbIdVehiculo;
    @FXML
    private TextField txbIdGendarme;
    @FXML
    private TextField txbIdUnidad;
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

    private VehiculosModel vehiculo;

    //creacion de la tabla
    @FXML
    private TableView<VehiculosModel> tablaMenuVehiculo;

    private VehiculosMenuController vehiculosMenuController;
    private int idVehiculoSeleccionado;

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


    public void inicializarDatosModificacion(VehiculosModel vehiculo){
        this.vehiculo = vehiculo;
        this.idVehiculoSeleccionado = vehiculo.getIdVehiculo();//todo como funciona esto..?//antes estaba idGendarme

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

            txbTipoVehiculo.setText(vehiculo.getTipoVehiculo());
            txbMarcaVehiculo.setText(vehiculo.getMarcaVehiculo());
            txbModeloVehiculo.setText(vehiculo.getModelo());
            txbKilometrajeVehiculo.setText(vehiculo.getKilometraje());
            txbPatenteVehiculo.setText(vehiculo.getPatente());


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void setTablaMenuVehiculo(TableView<VehiculosModel> tablaMenuVehiculo){
        this.tablaMenuVehiculo = tablaMenuVehiculo;
    }
    public void setVehiculosMenuController (VehiculosMenuController vehiculosMenuController){
        this.vehiculosMenuController = vehiculosMenuController;
    }

    @FXML
    private void handleConfirmarButtonAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Modificación");
        alert.setHeaderText("Está a punto de modificar los datos del vehiculo.");
        alert.setContentText("¿Está seguro de que desea continuar?");

        ButtonType buttonTypeConfirmar = new ButtonType("Confirmar");
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeConfirmar, buttonTypeCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeConfirmar){
            VehiculosModel updateVehiculo = obtenerDatosFormulario();

            if(updateVehiculo != null) {
                VehiculoDAO vehiculoDAO = new VehiculoDAO();

                boolean exito = vehiculoDAO.actualizarVehiculo(updateVehiculo);

                if (exito) {
                    mostrarAlerta("Actualización Exitosa", "Los datos del vehiculo han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if (vehiculosMenuController != null) {
                    vehiculosMenuController.actualizarTableView();
                }

                Stage stage = (Stage) txbPatenteVehiculo.getScene().getWindow(); //todo cambiar a idGendarme?
                stage.close();
            }
            else{
                mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del vehiculo.");
            }
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }


    private VehiculosModel obtenerDatosFormulario() {
        //int idVehiculo = Integer.parseInt(txbIdVehiculo.getText());
/*        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());*/

        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);

        String tipoVehiculo = txbTipoVehiculo.getText();
        String marcaVehiculo = txbMarcaVehiculo.getText();
        String modelo = txbModeloVehiculo.getText();
        String patente = txbPatenteVehiculo.getText();
        String kilometraje = txbKilometrajeVehiculo.getText();

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El GENDARME no es válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "la UNIDAD no es válida.");
        }

        return new VehiculosModel(vehiculo.getIdVehiculo(), idUnidad, idGendarme, tipoVehiculo, marcaVehiculo, modelo, patente, kilometraje);
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
        txbPatenteVehiculo.clear();
        txbModeloVehiculo.clear();
        txbMarcaVehiculo.clear();
        txbKilometrajeVehiculo.clear();
        txbTipoVehiculo.clear();

    }


// 1/07/24
}


