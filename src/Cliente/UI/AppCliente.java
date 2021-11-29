package Cliente.UI;

import Cliente.Cliente_Impl;
import common.Servidor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Alert alerta;
    private PrincipalController controladorPrincipal;
    
    @Override
    public void start(Stage primaryStage) {
       
        this.escenario = primaryStage;
        this.alertaError = new Alert(AlertType.ERROR);
        
        try{
            
            // 1. Lanzamos ventana inicio (datos servidor)
            FXMLLoader inicioLoader = new FXMLLoader(AppCliente.class.getResource("Inicio.fxml"));
            inicioLoader.setControllerFactory(c -> new InicioController(this));
            this.escenario.setScene(new Scene(inicioLoader.load()));
            this.escenario.show();
            
        }catch(IOException e){
            System.out.println("No se pudo cargar la ventana inicial");
        }
    }
    
    @Override
    public void stop(){
        cliente.shutdown();
        System.exit(0);
    }
    
    public void lanzarVentanaLogin(String nombre, int puerto) {
        try {
            // Creamos el objeto cliente pasandole el objeto servidor y la aplicación
            cliente = new Cliente_Impl((Servidor) Naming.lookup("rmi://" + nombre + ":" + puerto + "/servidor"), this);
            
            // Lanzamos la ventana de login/registro
            FXMLLoader loginLoader = new FXMLLoader(AppCliente.class.getResource("Login.fxml"));
            loginLoader.setControllerFactory(c -> new LoginController(this));
            escenario.setScene(new Scene(loginLoader.load()));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            this.alertaError.setContentText("Error en la conexión al servidor");
            this.alertaError.show();
        } catch (IOException e) {
            this.alertaError.setContentText("No se pudo cargar la ventana de login");
            this.alertaError.show();
        }
    }
    
    void registrar(String usuario, String contrasenha) {
        // Informamos del estado del registro
        try {
            switch(cliente.registrar(usuario, contrasenha)){
                case 0:
                    alerta.setContentText("Registrado correctamente");
                    alerta.show();
                    break;
                case 1:
                    alertaError.setContentText("El usuario ya existe");
                    break;
                case 2:
                    alertaError.setContentText("Error de conexión, vuelva a intentarlo");
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
    
    void login(String usuario, String contra) {
        // Si login correcto, lanzamos la ventana principal
        if(cliente.login(usuario, contra)){
            try {
                // Lanzamos ventana principal
                FXMLLoader principalLoader = new FXMLLoader(AppCliente.class.getResource("Principal.fxml"));
                principalLoader.setControllerFactory(c -> new PrincipalController(this));
                escenario.setScene(new Scene(principalLoader.load()));
            } catch (IOException ex) {
                this.alertaError.setContentText("No se pudo cargar la ventana principal");
                this.alertaError.show();
            }
        }else{
            // Error en el login
            alertaError.setContentText("Error en el login");
            alertaError.show();
        }
    }
    
    public static void main(String[] args) {
        launch(); 
    }

    public void registrarMensaje(String emisor, String mensaje) {
        controladorPrincipal.registrarMensaje(emisor, mensaje);
    }

    public String getNombreUsuario() {
        return cliente.getNombreUsuario();
    }

    public void send(String amigo, String mensaje) {
        try {
            cliente.send(amigo, mensaje);
        } catch (RemoteException ex) {
        }
    }

    public void enviarSolicitud(String solicitado) {
        switch(cliente.enviarSolicitud(solicitado)){
                case 0:
                    alerta.setContentText("Solicitud enviada a "+solicitado);
                    alerta.show();
                    break;
                case 1:
                    alertaError.setContentText("Error inesperado, usuario no alcanzable");
                    alertaError.show();
                    break;
                case 2:
                    alertaError.setContentText("Ya eres tu propio amigo :)");
                    alertaError.show();
                    break;
                default:
                    alertaError.setContentText("Ya eres amigo de " + solicitado);
                    alertaError.show();
            }
    }

    
    
}
