package com.e.registrifyv1.Controladores.Inventario;
import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Dao.VehiculoDAO;
import  com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;
public class ModificarInventarioController {
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    //@FXML
    //private TextField txbIdAccesorio;
    @FXML
    private TextField txbIdUnidad;
    @FXML
    private TextField txbIdGendarme;
    @FXML
    private TextField txbNombre;
    @FXML
    private TextField txbDescripcion;

    private InventarioModel inventario;

    //creacion de la tabla
    @FXML
    private TableView<InventarioModel> tablaMenuInventario;

    private InventarioMenuController inventarioMenuController;
    private int idInventarioSeleccionado;

    public void initialize () {

        // Aquí puedes hacer cualquier inicialización adicional
        // Por ejemplo, llenar los ComboBox con opciones, etc.
        // hacer los dropdown list con nombres de gendarmes y de unidad

    }

    public void inicializarDatosModificacion(InventarioModel inventario){
        this.inventario = inventario;
        this.idInventarioSeleccionado = inventario.getIdAccesorio();//todo como funciona esto..?//antes estaba idGendarme
        txbIdGendarme.setText(String.valueOf(inventario.getIdGendarme()));
        txbIdUnidad.setText(String.valueOf(inventario.getIdUnidad()));
        txbNombre.setText(inventario.getNombreAccesorio());
        txbDescripcion.setText(inventario.getNombreAccesorio());
    }

    public void setTablaMenuInventario(TableView<InventarioModel> tablaMenuInventario){
        this.tablaMenuInventario = tablaMenuInventario;
    }
    public void setInventarioMenuController (InventarioMenuController inventarioMenuController){
        this.inventarioMenuController = inventarioMenuController;
    }

    @FXML
    private void handleConfirmarModificacion(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Modificación");
        alert.setHeaderText("Está a punto de modificar los datos del armamento.");
        alert.setContentText("¿Está seguro de que desea continuar?");

        ButtonType buttonTypeConfirmar = new ButtonType("Confirmar");
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeConfirmar, buttonTypeCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeConfirmar){
            InventarioModel updateInventario = obtenerDatosFormulario();

            if(updateInventario != null) {
                InventarioDAO inventarioDAO = new InventarioDAO();

                boolean exito = inventarioDAO.actualizarInventario(updateInventario);

                if (exito) {
                    mostrarAlerta("Actualización Exitosa", "Los datos del arma han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if (inventarioMenuController != null) {
                    inventarioMenuController.actualizarTableView();
                }

                Stage stage = (Stage) txbNombre.getScene().getWindow(); //todo cambiar a idGendarme?
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


    private InventarioModel obtenerDatosFormulario() {
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        String  nombreAccesorio= txbNombre.getText();
        String descrAccesorio = txbDescripcion.getText();

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_GENDARME no es un número válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_UNIDAD no es un número válido.");
        }

        return new InventarioModel(inventario.getIdAccesorio(), idUnidad, idUnidad, nombreAccesorio, descrAccesorio);
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
        txbNombre.clear();
        txbDescripcion.clear();
    }


}
