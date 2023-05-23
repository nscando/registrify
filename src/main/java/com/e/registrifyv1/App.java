package com.e.registrifyv1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;


import java.io.IOException;

public class App extends Application {

   @Override
   public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      stage.setScene(scene);

      stage.show();
   }

   public static void main(String[] args) {
      launch();
   }
}
