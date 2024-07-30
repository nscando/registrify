package com.e.registrifyv1.Controladores.Arma;

import com.e.registrifyv1.Dao.ArmaDAO;
import com.e.registrifyv1.Modelos.Arma.ArmaMenuModel;
import com.e.registrifyv1.Modelos.Rol.Rol;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import com.e.registrifyv1.Modelos.Usuarios.UsuarioModel;
import com.e.registrifyv1.Utiles.Session;
import javafx.beans.property.SimpleStringProperty;
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

public class ArmaMenuController {

    //Creacion de la Tabla de Armas
    @FXML
    private TableView <ArmaMenuModel> tablaMenuArmas;

    @FXML
    private TableColumn<ArmaMenuModel, Integer> idArmaColum;

    @FXML
    private TableColumn<ArmaMenuModel, String> nombreUnidadColum;

    @FXML
    private TableColumn<ArmaMenuModel, String> gendarmeColum;

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
    private Button btnEliminar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnBuscarArmaMenu;


    @FXML
    private Button btnGenerarReporte;

    private int idRol = Session.getIdRol();




    @FXML
    private void initialize() {
        armaDAO = new ArmaDAO();
        configurarColumnas();
        configurarAccesosPorRol(idRol);

    }

    private void configurarColumnas() {

        idArmaColum.setCellValueFactory(new PropertyValueFactory<>("idArma"));
        gendarmeColum.setCellValueFactory(cellData -> {
            ArmaMenuModel arma = cellData.getValue();
            String gendarmeInfo = arma.getNombreGendarme() + " " + arma.getApellidoGendarme() + " " + arma.getDniGendarme();
            return new SimpleStringProperty(gendarmeInfo);
        });
        nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
        marcaArmaColum.setCellValueFactory(new PropertyValueFactory<>("marcaArma"));
        tipoArmaColum.setCellValueFactory(new PropertyValueFactory<>("tipoArma"));
        numeroSerieArmaColum.setCellValueFactory(new PropertyValueFactory<>("numeroSerieArma"));

        /*
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


         */

/*
            ESTA PARTE DEL CODIGO ES LA QUE ABRE EL MENU DE MODIFICAR EL USUARIO SIN QUE SE HAGA DOBLE CLICK.
            tablaMenuArmas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                abrirFormularioModificarArma(newValue);
            }
        });*/
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

    private void configurarAccesosPorRol(int idRol) {

        // Activar accesos basados en el rol
        switch (idRol) {
            case Rol.ADMINISTRADOR:
                // El administrador tiene acceso a todo
                btnSalir.setDisable(false);

                btnEliminar.setDisable(false);
                btnAgregar.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarArmaMenu.setDisable(false);

                btnGenerarReporte.setDisable(false);
                break;
            case Rol.SUPERVISOR:
                // El supervisor tiene acceso a todo menos a btnEliminar y btnAgregar
                btnSalir.setDisable(false);

                btnEliminar.setDisable(true);
                btnAgregar.setDisable(true);
                btnModificar.setDisable(false);
                btnBuscarArmaMenu.setDisable(false);

                btnGenerarReporte.setDisable(false);
                break;
            case Rol.USUARIO:
                // El usuario tiene acceso a todo menos a btnEliminar y btnConfiguracion
                btnSalir.setDisable(false);

                btnEliminar.setDisable(true);
                btnAgregar.setDisable(false);
                btnModificar.setDisable(false);
                btnBuscarArmaMenu.setDisable(false);

                btnGenerarReporte.setDisable(false);
                break;
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

    private void cargarArmasEnTableView(ObservableList<ArmaMenuModel> armas) {

        if (armas != null && !armas.isEmpty()) {

            tablaMenuArmas.setItems(armas);

            idArmaColum.setCellValueFactory(new PropertyValueFactory<>("idArma"));
            gendarmeColum.setCellValueFactory(cellData -> {
                ArmaMenuModel arma = cellData.getValue();
                String gendarmeInfo = arma.getNombreGendarme() + " " + arma.getApellidoGendarme() + " (" + arma.getDniGendarme() + ")";
                return new SimpleStringProperty(gendarmeInfo);
            });
            nombreUnidadColum.setCellValueFactory(new PropertyValueFactory<>("nombreUnidad"));
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
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error al dar de baja");
            alertError.setContentText("Selecciona un armamento antes de dar de baja.");
            alertError.show();
        }
    }

    @FXML
    private void handleBtnBajaClick(ActionEvent event) {
        ArmaMenuModel armaSeleccionada = obtenerArmaSeleccionada();

        if (armaSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¡Atención!");
            alert.setContentText("Estás a punto de dar de baja un arma. ¿Estás seguro?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean bajaExitosa = armaDAO.bajaArma(armaSeleccionada.getIdArma());

                    if (bajaExitosa) {
                        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        alertConfirmacion.setTitle("Confirmación");
                        alertConfirmacion.setHeaderText("¡Armamento dado de baja correctamente!");
                        alertConfirmacion.show();
                        actualizarTableView();
                        // Realiza acciones adicionales después de dar de baja si es necesario
                    } else {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error al dar de baja");
                        alertError.setContentText("Hubo un problema al dar de baja el armamento.");
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
            alertError.setContentText("Selecciona un armamento antes de dar de baja.");
            alertError.show();
        }
    }


    private ArmaMenuModel obtenerArmaSeleccionada() {
        // Lógica para obtener el armamento seleccionado
        return tablaMenuArmas.getSelectionModel().getSelectedItem();
    }

    public void actualizarTableView() {
        String valorBusqueda = txtFieldMenuArma.getText();
        ObservableList<ArmaMenuModel> armas = armaDAO.buscarArma(valorBusqueda);
        cargarArmasEnTableView(armas);
    }

    private void mostrarAlerta(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    @FXML
    public void handleGenerarReporte(ActionEvent event) {
        try {
            // Obtén la lista actual de usuarios en la TableView
            ObservableList<ArmaMenuModel> arma = tablaMenuArmas.getItems();

            // Cargar el diseño del informe desde un archivo jrxml
            // Cambia la ruta según la ubicación real de tu archivo de diseño
            InputStream inputStream = getClass().getResourceAsStream("/Reports/registrify_report_armas.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Crear una fuente de datos para el informe utilizando JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(arma);

            // Parámetros del informe (puedes agregar más según tus necesidades)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ArmaDataSource", dataSource);

            // Compilar y llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Obtén la marca de tiempo actual para el nombre único del archivo
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new Date());

            // Construye el nombre del archivo con la marca de tiempo
            String pdfFileName = "src/main/resources/ReportesGenerados/ReportesArmas/registrify_report_armas_" + timeStamp + ".pdf";

            // Guardar el informe como un archivo PDF en la carpeta ReportesGenerados
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);

            // Muestra un mensaje de éxito
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Informe generado correctamente", "El informe se ha guardado en: " + pdfFileName);
        } catch (JRException e) {
            e.printStackTrace();
            // Muestra un mensaje de error
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al generar el informe", e.getMessage());
        }
    }



}
