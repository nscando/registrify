package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class ModificarArmaController {

    @FXML
    private TextField txbIdGendarme;

    @FXML
    private TextField txbIdUnidad;

    @FXML
    private TextField txbMarcaArma;

    @FXML
    private TextField txbTipoArma;

    @FXML
    private TextField txbNumeroSerieArma;

    @FXML
    private Button btnCancelar;

    private int idArma = 0;

    private ArmaMenuModel arma;

    //Creacion de la Tabla de Armas
    @FXML
    private TableView <ArmaMenuModel> tablaMenuArmas;

    private ArmaMenuController armaMenuController;

    private int idArmaSeleccionada;

    public void initialize () {

        // Aquí puedes hacer cualquier inicialización adicional
        // Por ejemplo, llenar los ComboBox con opciones, etc.
        // hacer los dropdown list con nombres de gendarmes y de unidad

    }

    public void inicializarDatosModificacion(ArmaMenuModel arma){
        this.arma = arma;
        this.idArmaSeleccionada = arma.getIdGendarme();

        txbIdGendarme.setText(String.valueOf(arma.getIdGendarme()));
        txbIdUnidad.setText(String.valueOf(arma.getIdUnidad()));
        txbMarcaArma.setText(arma.getMarcaArma());
        txbTipoArma.setText(arma.getTipoArma());
        txbNumeroSerieArma.setText(arma.getNumeroSerieArma());
    }

    public void setTablaMenuArma(TableView<ArmaMenuModel> tablaMenuArmas){
        this.tablaMenuArmas = tablaMenuArmas;
    }

    public void setArmaMenuController (ArmaMenuController armaMenuController){
        this.armaMenuController = armaMenuController;
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
            ArmaMenuModel updateArma = obtenerDatosFormulario();

            if(updateArma != null){
                ArmaDAO armaDAO = new ArmaDAO();

                boolean exito = armaDAO.actualizarArma(updateArma);

                if(exito){
                    mostrarAlerta("Actualización Exitosa", "Los datos del arma han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if(armaMenuController != null){
                    armaMenuController.actualizarTableView();
                }

                Stage stage = (Stage) txbIdGendarme.getScene().getWindow();
                stage.close();
            }else{
                mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del armamento.");
            }
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private ArmaMenuModel obtenerDatosFormulario() {
        int idGendarme = Integer.parseInt(txbIdGendarme.getText());
        int idUnidad = Integer.parseInt(txbIdUnidad.getText());
        String marcaArma = txbMarcaArma.getText();
        String tipoArma = txbTipoArma.getText();
        String numeroDeSerieArma = txbNumeroSerieArma.getText();

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_GENDARME no es un número válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El ID_UNIDAD no es un número válido.");
        }

        return new ArmaMenuModel(arma.getIdArma(), idGendarme, idUnidad, marcaArma, tipoArma, numeroDeSerieArma);
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCamposFormulario() {
        txbIdGendarme.clear();
        txbIdUnidad.clear();
        txbMarcaArma.clear();
        txbTipoArma.clear();
        txbNumeroSerieArma.clear();

    }


}
