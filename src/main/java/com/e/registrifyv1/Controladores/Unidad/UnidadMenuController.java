package com.e.registrifyv1.Controladores.Unidad;

import com.e.registrifyv1.Dao.UnidadDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class UnidadMenuController{

    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtFieldMenuUnidad;
    private UnidadDAO unidadDAO;

    @FXML
    private TableView<UnidadMenuModel> tablaMenuUnidad;

    @FXML
    private TableColumn<UnidadMenuModel, Integer> idUnidadColum;

    @FXML
    private TableColumn<UnidadMenuModel, String> nombreUnidadColum;

    @FXML
    private TableColumn<UnidadMenuModel, String> ubicacionUnidadColum;

    @FXML
    private void initialize() {
        unidadDAO = new UnidadDAO();
        configurarColumnas();
        // Agregar evento de doble clic a la columna que deseas
    }

    private void configurarColumnas(){
        nombreUnidadColum.setCellFactory(tc -> {
            TableCell<UnidadMenuModel, String> cell = new TableCell<UnidadMenuModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    UnidadMenuModel rowData = cell.getTableView().getItems().get(cell.getIndex());
                    abrirFormularioModificarUnidad(rowData);
                }
            });
            return cell;
        });
    }
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnBuscarUnidadAction(ActionEvent event) {
        String valorBusqueda = txtFieldMenuUnidad.getText();

        ObservableList<UnidadMenuModel> unidad = unidadDAO.buscarUnidad(valorBusqueda);
        cargarUnidadesEnTableView(unidad);
    }

    private void cargarUnidadesEnTableView(ObservableList<UnidadMenuModel> unidad) {
        if (unidad != null && !unidad.isEmpty()) {
            tablaMenuUnidad.setItems(unidad);

            idUnidadColum.setCellValueFactory(new PropertyValueFactory<>("idUnidad"));
            nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
            ubicacionUnidadColum.setCellValueFactory(new PropertyValueFactory<>("ubicacionUnidad"));
        } else {
            tablaMenuUnidad.getItems().clear();
        }
    }

    @FXML //ABRIR FORMULARIO PARA AGREGAR UNIDAD
    public void menuAgregarUnidad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/AgregarUnidadView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar unidad");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML //ABRIR FORMULARIO PARA MODIFICAR LA UNIDAD
    private void abrirFormularioModificarUnidad(UnidadMenuModel unidad) {
        try {
            //ABRE EL FORMULARIO DE LA UNIDAD PARA SER MODIFICADA
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/ModificarUnidadView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario de modificación
            ModificarUnidadController controller = loader.getController();

            // Pasar los datos de la unidad seleccionado al controlador del formulario de modificación
            controller.inicializarDatosModificacion(unidad);

            // Mostrar el formulario de modificación
            Stage stage = new Stage();
            stage.setTitle("Modificar Unidad");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModificarUnidadButtonAction(ActionEvent event) {
        UnidadMenuModel unidadSeleccionada = obtenerUnidadSeleccionada();

        if(unidadSeleccionada != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Unidad/ModificarUnidadView.fxml"));
                Parent root = (Parent) loader.load();
                ModificarUnidadController controller = loader.getController();
                controller.inicializarDatosModificacion(unidadSeleccionada);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al modificar la unidad");
            alertError.setContentText("Selecciona una unidad antes de modificarla.");
            alertError.show();
        }
    }

    @FXML
    private void handleBtnBajaClick(ActionEvent event) {
        UnidadMenuModel unidadSeleccionada = obtenerUnidadSeleccionada();

        if (unidadSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de dar de baja la unodad. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = unidadDAO.bajaUnidad(unidadSeleccionada.getIdUnidad());

                    if (bajaExitosa) {
                        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        alertConfirmacion.setTitle("Confirmación");
                        alertConfirmacion.setHeaderText("¡Unidad dada de baja correctamente!");
                        alertConfirmacion.show();
                        actualizarTableView();
                        // Realiza acciones adicionales después de dar de baja si es necesario
                    } else {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error al dar de baja");
                        alertError.setContentText("Hubo un problema al dar de baja la unidad.");
                        alertError.show();
                    }
                } catch (Exception e) {
                    // Imprime la excepción en la consola
                    e.printStackTrace();
                }
            }
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al dar de baja");
            alertError.setContentText("Selecciona una unidad antes de dar de baja.");
            alertError.show();
        }
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuUnidad.getText();
        ObservableList<UnidadMenuModel> unidad = unidadDAO.buscarUnidad(valorBusqueda);
        cargarUnidadesEnTableView(unidad);
    }

    private UnidadMenuModel obtenerUnidadSeleccionada() {
        // Lógica para obtener la unidad seleccionado
        return tablaMenuUnidad.getSelectionModel().getSelectedItem();
    }


}
