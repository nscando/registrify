package com.e.registrifyv1.Controladores.Inventario;

import com.e.registrifyv1.Dao.InventarioDAO;
import com.e.registrifyv1.Dao.VehiculoDAO;
import com.e.registrifyv1.Modelos.Inventario.InventarioModel;
import com.e.registrifyv1.Modelos.Vehiculos.VehiculosModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
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


public class InventarioMenuController {

    @FXML
    private TableView<InventarioModel> tablaMenuInventario;

    @FXML
    private TableColumn<InventarioModel, Integer> idAccesorioColum;

    @FXML
    private TableColumn<InventarioModel, Integer> idUnidadColum;

    @FXML
    private TableColumn<InventarioModel, Integer> idGendarmeColum;

    @FXML
    private TableColumn<InventarioModel, String> nombreAccesorioColum;

    @FXML
    private TableColumn<InventarioModel, String> descrAccesorioColum;

    @FXML
    private TextField txtFieldMenuInventario;

    @FXML
    private InventarioDAO inventarioDAO;

    @FXML
    private void initialize(){
        inventarioDAO=new InventarioDAO();
        configurarColumnas();
    }

    private void configurarColumnas() {
        idGendarmeColum.setCellFactory(tc -> {
            TableCell<InventarioModel, Integer> cell = new TableCell<InventarioModel, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item != null ? item.toString() : "");
                }
            };
            /*cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    VehiculosModel rowData = cell.getTableView().getItems().get(cell.getIndex());
                    abrirFormularioModificarVehiculo(rowData);
                }
            });*/
            return cell;
        });
    }

    @FXML
    private void btnBuscarInventarioAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuInventario.getText();
        ObservableList<InventarioModel> inventario = inventarioDAO.buscarInventario(valorBusqueda);
        cargarInventariosEnTableView(inventario);
    }

    private void cargarInventariosEnTableView(ObservableList<InventarioModel> inventario) {

        if (inventario != null && !inventario.isEmpty()) {

            tablaMenuInventario.setItems(inventario);

            idAccesorioColum.setCellValueFactory(new PropertyValueFactory<>("idAccesorio"));
            idGendarmeColum.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
            idUnidadColum.setCellValueFactory(new PropertyValueFactory<>("idUnidad"));
            nombreAccesorioColum.setCellValueFactory(new PropertyValueFactory<>("nombreAccesorio"));
            descrAccesorioColum.setCellValueFactory(new PropertyValueFactory<>("descrAccesorio"));

        } else {
            tablaMenuInventario.getItems().clear();
        }
    }




    @FXML
    private Button btnSalir;
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
}

