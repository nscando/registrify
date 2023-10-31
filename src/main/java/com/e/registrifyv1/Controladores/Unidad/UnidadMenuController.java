package com.e.registrifyv1.Controladores.Unidad;

import com.e.registrifyv1.Dao.UnidadDAO;
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
        // Agregar evento de doble clic a la columna que deseas

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








}
