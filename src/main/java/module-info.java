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

   opens com.e.registrifyv1 to javafx.fxml;
   exports com.e.registrifyv1;
   exports com.e.registrifyv1.Controladores;
   opens com.e.registrifyv1.Controladores to javafx.fxml;
   opens com.e.registrifyv1.Modelos to javafx.base;
}