package com.e.registrifyv1.Controladores.Evento;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.e.registrifyv1.Controladores.Arma.ModificarArmaController;
import com.e.registrifyv1.Dao.EventoDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Modelos.EventoDiario.EventoDiarioModel;
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

public class EventoDiarioController {
    @FXML
    private TableView<EventoDiarioModel> tablaMenuEvento;

    @FXML
    private TableColumn<EventoDiarioModel, Integer> idEventoColumn;

    @FXML
    private TableColumn<EventoDiarioModel, Integer> idUnidadColum;

    @FXML
    private TableColumn<EventoDiarioModel, Integer> idGendarmeColum;

    @FXML
    private TableColumn<EventoDiarioModel, String> descrEventoColumn;

    @FXML
    private TableColumn<EventoDiarioModel, String> fechaEventoColumn;

    @FXML
    private TableColumn<EventoDiarioModel, Integer> estadoEventoColumn;

    @FXML
    private TextField txtFieldMenuEvento;

    @FXML
    private EventoDAO eventoDAO;



    @FXML
    private Button btnSalir;

    @FXML
    private void initialize(){
        eventoDAO=new EventoDAO();
        configurarColumnas();
    }

    private void configurarColumnas() {
        idGendarmeColum.setCellFactory(tc -> {
            TableCell<EventoDiarioModel, Integer> cell = new TableCell<EventoDiarioModel, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item != null ? item.toString() : "");
                }
            };
            //todo  doble click para modificar
            //cell.setOnMouseClicked(event -> {
              //  if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                //    EventoDiarioModel rowData = cell.getTableView().getItems().get(cell.getIndex());
              //    abrirFormularioModificarVehiculo(rowData);
               // }
           // });
            return cell;
        });
    }
    @FXML
    private void handleSalirButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void btnBuscarEventoAction(ActionEvent event) {

        String valorBusqueda = txtFieldMenuEvento.getText();
        ObservableList<EventoDiarioModel> evento = eventoDAO.buscarEvento(valorBusqueda);
        cargarEventosEnTableView(evento);
    }

    private void cargarEventosEnTableView(ObservableList<EventoDiarioModel> evento) {

        if (evento != null && !evento.isEmpty()) {

            tablaMenuEvento.setItems(evento);

            idEventoColumn.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
            idUnidadColum.setCellValueFactory(new PropertyValueFactory<>("idUnidad"));
            idGendarmeColum.setCellValueFactory(new PropertyValueFactory<>("idGendarme"));
            descrEventoColumn.setCellValueFactory(new PropertyValueFactory<>("descrEvento"));
            fechaEventoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaEvento"));
            estadoEventoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));


        } else {
            tablaMenuEvento.getItems().clear();
        }
    }


}
