package com.e.registrifyv1.Controladores.Inventario;
import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Dao.UsuarioDAO;
import com.e.registrifyv1.Dao.VehiculoDAO;
import  com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
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

    public void inicializarDatosModificacion(InventarioModel inventario){
        this.inventario = inventario;
        this.idInventarioSeleccionado = inventario.getIdAccesorio();//todo como funciona esto..?//antes estaba idGendarme
/*        txbIdGendarme.setText(String.valueOf(inventario.getIdGendarme()));
        txbIdUnidad.setText(String.valueOf(inventario.getIdUnidad()));*/
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

            txbNombre.setText(inventario.getNombreAccesorio());
            txbDescripcion.setText(inventario.getNombreAccesorio());


        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        alert.setHeaderText("Está a punto de modificar los datos del inventario.");
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
                    mostrarAlerta("Actualización Exitosa", "Los datos del inventario han sido actualizados correctamente.");
                    limpiarCamposFormulario();
                }

                if (inventarioMenuController != null) {
                    inventarioMenuController.actualizarTableView();
                }

                Stage stage = (Stage) txbNombre.getScene().getWindow(); //todo cambiar a idGendarme?
                stage.close();
            }
            else{
                mostrarAlerta("Error de Actualización", "Hubo un error al intentar actualizar los datos del inventario.");
            }
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }


    private InventarioModel obtenerDatosFormulario() {
        String gendarmeSeleccionado = comboGendarme.getSelectionModel().getSelectedItem();
        String unidadSeleccionada = comboUnidad.getSelectionModel().getSelectedItem();

        int idGendarme = gendarmeMap.get(gendarmeSeleccionado);
        int idUnidad = unidadMap.get(unidadSeleccionada);

        String  nombreAccesorio= txbNombre.getText();
        String descrAccesorio = txbDescripcion.getText();

        String idGen = String.valueOf(idGendarme);
        String idUni = String.valueOf(idUnidad);

        if(!idGen.matches("\\d+")){
            mostrarAlerta("Error de Validación", "El GENDARME no es un válido.");
        }

        if(!idUni.matches("\\d+")){
            mostrarAlerta("Error de Validación", "La UNIDAD no es válida.");
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
        comboGendarme.getSelectionModel().clearSelection();
        comboUnidad.getSelectionModel().clearSelection();
        txbNombre.clear();
        txbDescripcion.clear();
    }


}
