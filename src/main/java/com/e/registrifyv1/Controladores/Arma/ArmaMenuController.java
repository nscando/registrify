package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
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

        // Agregar evento de doble clic a la columna que deseas
/*
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
        */
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


/*
    @FXML
    public void handleConfirmarButton(ActionEvent event) {
        int idUnidad = 0;

        String nombreUnidad = txtNombreUnidad.getText();
        String ubicacionUnidad = txtUbicacionUnidad.getText();

        UnidadMenuModel nuevaUnidad = new UnidadMenuModel(idUnidad, nombreUnidad, ubicacionUnidad);

        nuevaUnidad.setIdUnidad(idUnidad);
        nuevaUnidad.setNombreUnidad(nombreUnidad);
        nuevaUnidad.setUbicacionUnidad(ubicacionUnidad);

        // Llamar al método insertarUsuario en UsuarioDAO
        UnidadDAO unidadDAO1 = new UnidadDAO();
        boolean exito = unidadDAO1.insertarUnidad(nuevaUnidad);

        mostrarMensaje(exito);
    }

    private void mostrarMensaje(boolean exito) {
        Alert alert = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(exito ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (exito) {
            alert.setContentText("La unidad se insertó correctamente.");
            txtNombreUnidad.clear();
            txtUbicacionUnidad.clear();
        } else {
            alert.setContentText("No se pudo insertar la unidad.");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }
    */

}
