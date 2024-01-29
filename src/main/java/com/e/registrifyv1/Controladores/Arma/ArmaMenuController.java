package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
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

public class ArmaMenuController {

    //Creacion de la Tabla de Armas
    @FXML
    private TableView <ArmaMenuModel> tablaMenuArmas;

    //Creacion de las 6 columnas que componen la tabla de Armas
    @FXML
    private TableColumn<ArmaMenuModel, Integer> idArmaColum;

    @FXML
    private TableColumn<ArmaMenuModel, Integer> idGendarmeColum;

    @FXML
    private TableColumn<ArmaMenuModel, Integer> idUnidadColum;

    @FXML
    private TableColumn<ArmaMenuModel, String> marcaArmaColum;

    @FXML
    private TableColumn<ArmaMenuModel, String> tipoArmaColum;

    @FXML
    private TableColumn<ArmaMenuModel, String> numeroSerieArmaColum;

    @FXML
    private TextField txtFieldMenuArma;

    @FXML
    private ArmaDAO armaDAO;

    @FXML
    private Button btnSalir;



    @FXML
    private void initialize() {
        armaDAO = new ArmaDAO();
        configurarColumnas();

    }

    private void configurarColumnas() {
        idGendarmeColum.setCellFactory(tc -> {
            TableCell<ArmaMenuModel, Integer> cell = new TableCell<ArmaMenuModel, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item != null ? item.toString() : "");
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    ArmaMenuModel rowData = cell.getTableView().getItems().get(cell.getIndex());
                    abrirFormularioModificarArma(rowData);
                }
            });
            return cell;
        });

        tablaMenuArmas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                abrirFormularioModificarArma(newValue);
            }
        });
    }

    @FXML
    public void menuArma(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Arma/ArmaMenuView.fxml")); //corregir.
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Arma");
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
    private void btnBuscarArmaAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuArma.getText();

        ObservableList<ArmaMenuModel> arma = armaDAO.buscarArma(valorBusqueda);

        cargarArmasEnTableView(arma);

    }

    private void cargarArmasEnTableView(ObservableList<ArmaMenuModel> arma) {

        if (arma != null && !arma.isEmpty()) {

            tablaMenuArmas.setItems(arma);

            idArmaColum.setCellValueFactory(new PropertyValueFactory<>("idArma"));
            idGendarmeColum.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
            idUnidadColum.setCellValueFactory(new PropertyValueFactory<>("idUnidad"));
            marcaArmaColum.setCellValueFactory(new PropertyValueFactory<>("marcaArma"));
            tipoArmaColum.setCellValueFactory(new PropertyValueFactory<>("tipoArma"));
            numeroSerieArmaColum.setCellValueFactory(new PropertyValueFactory<>("numeroSerieArma"));
        } else {
            tablaMenuArmas.getItems().clear();
        }
    }

    @FXML //ABRIR FORMULARIO PARA AGREGAR UNIDAD
    public void menuAgregarArma(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Arma/AgregarArmaView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar arma");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirFormularioModificarArma(ArmaMenuModel arma) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Arma/ModificarArmaView.fxml"));
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
        }
    }

    public void handleModificarUsuarioButtonAction(ActionEvent event) {
        ArmaMenuModel armaSeleccionada = tablaMenuArmas.getSelectionModel().getSelectedItem();
        if (armaSeleccionada != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Arma/ModificarArmaView.fxml"));
                Parent root = (Parent) loader.load();
                ModificarArmaController controller = loader.getController();
                controller.inicializarDatosModificacion(armaSeleccionada);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Porfavor, seleccione un armamento para modificar.");
        }
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuArma.getText();
        ObservableList<ArmaMenuModel> armas = armaDAO.buscarArma(valorBusqueda);
        cargarArmasEnTableView(armas);
    }



}
