module com.e.registrifyv1 {
   requires javafx.controls;
   requires javafx.fxml;
   requires javafx.web;
   requires java.sql;

   requires org.controlsfx.controls;
   requires com.dlsc.formsfx;
   requires net.synedra.validatorfx;
   requires org.kordamp.ikonli.javafx;
   requires org.kordamp.bootstrapfx.core;
   requires com.almasb.fxgl.all;

   opens com.e.registrifyv1 to javafx.fxml;
   exports com.e.registrifyv1;
   exports com.e.registrifyv1.Controlador;
   opens com.e.registrifyv1.Controlador to javafx.fxml;
}