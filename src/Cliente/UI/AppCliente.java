/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Cliente.UI;

import Cliente.Cliente_Impl;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppCliente extends Application {
    
    Cliente_Impl cliente;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        // 1. Creamos el objeto remoto
        cliente = new Cliente_Impl();
        
        // 2. Lanzamos ventana servidor
        FXMLLoader fxmlLoader = new FXMLLoader(AppCliente.class.getResource("Inicio.fxml"));
        fxmlLoader.setControllerFactory(c -> new Object());
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(); 
    }
    
}
