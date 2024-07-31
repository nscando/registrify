package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
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

public class ModificarArmaController {

/*    @FXML
    private TextField txbIdGendarme;

    @FXML
    private TextField txbIdUnidad;*/

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

    public void inicializarDatosModificacion(ArmaMenuModel arma){
        this.arma = arma;
        this.idArmaSeleccionada = arma.getIdArma();
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

            txbMarcaArma.setText(arma.getMarcaArma());
            txbTipoArma.setText(arma.getTipoArma());
            txbNumeroSerieArma.setText(arma.getNumeroSerieArma());


        } catch (SQLException e) {
            e.printStackTrace();
        }
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

            if(updateArma != null) {
                ArmaDAO armaDAO = new ArmaDAO();

                boolean exito = armaDAO.actualizarArma(updateArma);

                if (exito) {
                    mostrarAlerta("Actualización Exitosa", "Los datos del arma han sido actualizados correctamente.");
                    limpiarCamposFormulario();

                }

                if (armaMenuController != null) {
                    armaMenuController.actualizarTableView();
                }

                Stage stage = (Stage) txbTipoArma.getScene().getWindow(); //todo cambiar a idGendarme?
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

    private ArmaMenuModel obtenerDatosFormulario() {
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);

        String marcaArma = txbMarcaArma.getText();
        String tipoArma = txbTipoArma.getText();
        String numeroDeSerieArma = txbNumeroSerieArma.getText();

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El GENDARME no es un número válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El UNIDAD no es un número válido.");
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
        comboGendarme.getSelectionModel().clearSelection();
        comboUnidad.getSelectionModel().clearSelection();
        txbMarcaArma.clear();
        txbTipoArma.clear();
        txbNumeroSerieArma.clear();

    }


}
