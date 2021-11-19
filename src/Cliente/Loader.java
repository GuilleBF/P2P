/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Cliente;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author itocd
 */
public class Loader extends Application {
    Font font = Font.loadFont("file:./Recuersos/Typo_Round_Bold_Demo.otf", 45);
    Font font2 = Font.loadFont("file:./Recuersos/Typo_Round_Regular_Demo.otf", 18);
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Loader.class.getResource("Inicio.fxml"));
        //Hay que crear un escenario y dentro poner escenas, es decir, la ventana y después los componentes
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    @FXML
    private Label labelHost;
    
    public void prueba(){
        labelHost.setText("Prueba");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();        
    }
    
}
