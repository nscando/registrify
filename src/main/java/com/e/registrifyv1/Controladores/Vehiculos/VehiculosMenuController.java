package com.e.registrifyv1.Controladores.Vehiculos;

import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehiculosMenuController {

    @FXML
    private TableView<VehiculosModel> tablaMenuVehiculo;

    @FXML
    private TableColumn<VehiculosModel, Integer> idVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, Integer> idUnidadColum;

    @FXML
    private TableColumn<VehiculosModel, Integer> idGendarmeColum;

    @FXML
    private TableColumn<VehiculosModel, String> tipoVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> marcaVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> modeloVehiculoColum;

    @FXML
    private TableColumn<VehiculosModel, String> patenteColum;

    @FXML
    private TableColumn<VehiculosModel, Integer> kilometrajeColum;

    @FXML
    private TextField txtFieldMenuVehiculo;

    @FXML
    private VehiculoDAO vehiculoDAO;

    @FXML
    private Button btnSalir;


    @FXML
    private void initialize(){
        vehiculoDAO=new VehiculoDAO();
        configurarColumnas();
    }

    private void configurarColumnas() {
        idGendarmeColum.setCellFactory(tc -> {
        TableCell<VehiculosModel, Integer> cell = new TableCell<VehiculosModel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item != null ? item.toString() : "");
            }
        };
        cell.setOnMouseClicked(event -> {
            if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                VehiculosModel rowData = cell.getTableView().getItems().get(cell.getIndex());
                abrirFormularioModificarVehiculo(rowData);
            }
        });
        return cell;
    });
    }

    @FXML
    public void menuVehiculos(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/AgregarVehiculoForm.fxml")); //corregir.
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Vehiculos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnBuscarVehiculosAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuVehiculo.getText();
        ObservableList<VehiculosModel> vehiculo = vehiculoDAO.buscarVehiculo(valorBusqueda);
        cargarVehiculosEnTableView(vehiculo);
    }

    private void cargarVehiculosEnTableView(ObservableList<VehiculosModel> vehiculo) {

        if (vehiculo != null && !vehiculo.isEmpty()) {

            tablaMenuVehiculo.setItems(vehiculo);

            idVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
            idGendarmeColum.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
            idUnidadColum.setCellValueFactory(new PropertyValueFactory<>("idUnidad"));
            tipoVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
            marcaVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("marcaVehiculo"));
            modeloVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            patenteColum.setCellValueFactory(new PropertyValueFactory<>("patente"));
            kilometrajeColum.setCellValueFactory(new PropertyValueFactory<>("kilometraje"));

        } else {
            tablaMenuVehiculo.getItems().clear();
        }
    }

    @FXML //ABRIR FORMULARIO PARA AGREGAR Vehiculo-Es necesario???
    // porque tengo creo que lo abre igual sin esto y si es asi, porque
    public void menuAgregarVehiculo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/AgregarVehiculoForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Vehiculo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void abrirFormularioModificarVehiculo(VehiculosModel vehiculo) {/*
        try {
            todo //Generar ModificarVehiculoForm
            todo //Generar ModificarVehiculoController

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Vehiculos/ModificarVehiculoForm.fxml"));
            Parent root = loader.load();
            ModificarArmaController controller = loader.getController();
            controller.setTablaMenuArma(tablaMenuArmas);
            controller.setArmaMenuController(this);
            controller.inicializarDatosModificacion(arma);
            Stage stage = new Stage();
            stage.setTitle("Modificar Arma");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuVehiculo.getText();
        ObservableList<VehiculosModel> vehiculo = vehiculoDAO.buscarVehiculo(valorBusqueda);
        cargarVehiculosEnTableView(vehiculo);
    }


}
