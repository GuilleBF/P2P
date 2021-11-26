/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Cliente.UI;

import Cliente.Cliente_Impl;
import common.Servidor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AppCliente extends Application {
    
    private Cliente_Impl cliente;
    private Servidor servidor;
    private Stage escenario;
    private Alert alertaError;
    
    @Override
    public void start(Stage primaryStage) {
       
        this.escenario = primaryStage;
        this.alertaError = new Alert(AlertType.ERROR);
        
        try{
            // 1. Creamos el objeto remoto
            cliente = new Cliente_Impl();
            
            // 2. Lanzamos ventana inicio (datos servidor)
            FXMLLoader inicioLoader = new FXMLLoader(AppCliente.class.getResource("Inicio.fxml"));
            inicioLoader.setControllerFactory(c -> new InicioController(this));
            this.escenario.setScene(new Scene(inicioLoader.load()));
            this.escenario.show();
            
        }catch(IOException e){
            System.out.println("No se pudo cargar la ventana inicial");
        }
    }
    
    public void lanzarVentanaLogin(String nombre, int puerto) {
        try {
            this.servidor = (Servidor) Naming.lookup("rmi://" + nombre + ":" + puerto + "/servidor");
            FXMLLoader loginLoader = new FXMLLoader(AppCliente.class.getResource("Login.fxml"));
            loginLoader.setControllerFactory(c -> new LoginController(this));
            escenario.setScene(new Scene(loginLoader.load()));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            this.alertaError.setContentText("Error en la conexión al servidor");
            this.alertaError.show();
        } catch (IOException ex) {
            this.alertaError.setContentText("No se pudo cargar la ventana de login");
            this.alertaError.show();
        }
    }
    
    public static void main(String[] args) {
        launch(); 
    }
    
}
