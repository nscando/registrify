module com.e.registrifyv1 {
   requires javafx.controls;
   requires javafx.fxml;
   requires javafx.web;
   requires java.sql;
   requires io.github.cdimascio.dotenv.java;


   requires org.controlsfx.controls;
   requires com.dlsc.formsfx;
   requires net.synedra.validatorfx;
   requires org.kordamp.ikonli.javafx;
   requires org.kordamp.bootstrapfx.core;
   requires jasperreports;

   opens com.e.registrifyv1 to javafx.fxml;
   exports com.e.registrifyv1;
   exports com.e.registrifyv1.Controladores;
   opens com.e.registrifyv1.Controladores to javafx.fxml;
   opens com.e.registrifyv1.Modelos.Usuarios to javafx.base;
   exports com.e.registrifyv1.Modelos.Usuarios; // Añadir esta línea
   exports com.e.registrifyv1.Controladores.Usuario;
   opens com.e.registrifyv1.Controladores.Usuario to javafx.fxml;

   exports com.e.registrifyv1.Controladores.Unidad;
   opens com.e.registrifyv1.Controladores.Unidad to javafx.fxml;
   exports com.e.registrifyv1.Controladores.Arma;
   opens com.e.registrifyv1.Controladores.Arma to javafx.fxml;
   opens com.e.registrifyv1.Modelos.Unidad to javafx.base;

   opens com.e.registrifyv1.Modelos.Arma to javafx.base;
   exports com.e.registrifyv1.Modelos.Unidad;
   exports com.e.registrifyv1.Modelos.Arma;

   exports com.e.registrifyv1.Modelos.Vehiculos;
   exports com.e.registrifyv1.Controladores.Vehiculos;
   opens com.e.registrifyv1.Controladores.Vehiculos to javafx.fxml;
   opens com.e.registrifyv1.Modelos.Vehiculos to javafx.base;// Abre el paquete a javafx.base

   exports com.e.registrifyv1.Controladores.Evento;
   opens com.e.registrifyv1.Controladores.Evento to javafx.fxml;
   opens com.e.registrifyv1.Modelos.EventoDiario to javafx.base;// Abre el paquete a javafx.base

   exports com.e.registrifyv1.Controladores.Inventario;
   opens com.e.registrifyv1.Controladores.Inventario to javafx.fxml;
   opens com.e.registrifyv1.Modelos.Inventario to javafx.base;



}