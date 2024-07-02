package com.e.registrifyv1.Controladores.Vehiculos;
import com.e.registrifyv1.Dao.VehiculoDAO;
import  com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    public void initialize () {

        // Aquí puedes hacer cualquier inicialización adicional
        // Por ejemplo, llenar los ComboBox con opciones, etc.
        // hacer los dropdown list con nombres de gendarmes y de unidad

    }


    public void inicializarDatosModificacion(VehiculosModel vehiculo){
        this.vehiculo = vehiculo;
        this.idVehiculoSeleccionado = vehiculo.getIdGendarme();//todo como funciona esto..?

        txbIdGendarme.setText(String.valueOf(vehiculo.getIdGendarme()));
        txbIdUnidad.setText(String.valueOf(vehiculo.getIdUnidad()));
        txbTipoVehiculo.setText(vehiculo.getTipoVehiculo());
        txbMarcaVehiculo.setText(vehiculo.getMarcaVehiculo());
        txbKilometrajeVehiculo.setText(vehiculo.getKilometraje());
        txbModeloVehiculo.setText(vehiculo.getModelo());
        txbPatenteVehiculo.setText(vehiculo.getPatente());
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
        alert.setHeaderText("Está a punto de modificar los datos del armamento.");
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
                    mostrarAlerta("Actualización Exitosa", "Los datos del arma han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if (vehiculosMenuController != null) {
                    vehiculosMenuController.actualizarTableView();
                }

                Stage stage = (Stage) txbIdVehiculo.getScene().getWindow(); //todo cambiar a idGendarme?
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


    private VehiculosModel obtenerDatosFormulario() {
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        String tipoVehiculo = txbTipoVehiculo.getText();
        String marcaVehiculo = txbMarcaVehiculo.getText();
        String modelo = txbModeloVehiculo.getText();
        String patente = txbPatenteVehiculo.getText();
        String kilometraje = txbKilometrajeVehiculo.getText();

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_GENDARME no es un número válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_UNIDAD no es un número válido.");
        }

        return new VehiculosModel(vehiculo.getIdVehiculo(), idGendarme, idUnidad, tipoVehiculo, marcaVehiculo, modelo, patente, kilometraje);
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
        txbPatenteVehiculo.clear();
        txbModeloVehiculo.clear();
        txbMarcaVehiculo.clear();
        txbIdVehiculo.clear();
        txbKilometrajeVehiculo.clear();
        txbTipoVehiculo.clear();

    }


// 1/07/24
}


