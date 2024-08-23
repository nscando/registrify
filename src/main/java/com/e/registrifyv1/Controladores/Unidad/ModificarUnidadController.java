package com.e.registrifyv1.Controladores.Unidad;

import com.e.registrifyv1.Dao.UnidadDAO;
import com.e.registrifyv1.Modelos.Unidad.UnidadMenuModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ModificarUnidadController {


    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    private UnidadMenuModel unidadAModificar;


    @FXML
    private TextField txtNombreUnidad; // Ajusta esto con el nombre de la unidad de tus campos en la vista

    @FXML
    private TextField txtUbicacionUnidad; // Ajusta esto con la ubicacion de tus campos en la vista

    private UnidadMenuModel unidadModelo; // Esta variable contendrá los datos de la unidad


    private UnidadDAO unidadDAO;

    @FXML
    public void initialize() {
        // Aquí puedes hacer cualquier inicialización adicional
        // Por ejemplo, llenar los ComboBox con opciones, etc.
        // ...
    }

    public void inicializarDatosModificacion(UnidadMenuModel unidadModelo) {
        this.unidadAModificar = unidadModelo; // Aquí debes asignar la unidad seleccionada

        // Asignar los valores de la unidad a los campos correspondientes
        txtNombreUnidad.setText(unidadModelo.getNombreUnidad()); // Utiliza unidadModelo en lugar de unidadAModificar
        txtUbicacionUnidad.setText(unidadModelo.getUbicacionUnidad());
    }


    @FXML
    private void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }



   @FXML
    public void handleConfirmarButton(ActionEvent event) {
        /*
        RECORDAR QUE PARA VER LA UNIDAD MODIFICADA HAY QUE ACTUALIZAR LA LISTA NUEVAMENTE
         */
       if (unidadAModificar != null) {
           String nombreUnidad = txtNombreUnidad.getText();
           String ubicacionUnidad = txtUbicacionUnidad.getText();

           unidadAModificar.setNombreUnidad(nombreUnidad);
           unidadAModificar.setUbicacionUnidad(ubicacionUnidad);

           UnidadDAO unidadDAO = new UnidadDAO();
           boolean exito = unidadDAO.modificarUnidad(unidadAModificar);

           if (exito) {
               mostrarMensaje(true);
           } else {
               mostrarMensaje(false);
               System.err.println("Error al modificar la unidad."); // Agrega esta línea para mostrar el mensaje de error en la consola de JavaFX
           }
       } else {
           mostrarMensaje(false);
           System.err.println("unidadAModificar es nulo."); // Agrega esta línea para mostrar el mensaje de error en la consola de JavaFX
       }
       Stage stage = (Stage) btnConfirmar.getScene().getWindow();
       stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


    private void mostrarMensaje(boolean exito) {
        Alert alert = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(exito ? "Éxito" : "Error");
        alert.setHeaderText(null);

        if (exito) {
            alert.setContentText("La unidad se modificó correctamente.");
            txtNombreUnidad.clear();
            txtUbicacionUnidad.clear();
        } else {
            alert.setContentText("No se pudo modificar la unidad. ");
        }

        // Configurar el cuadro de diálogo para que sea siempre visible
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait();
    }






}
