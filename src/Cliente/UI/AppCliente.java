package Cliente.UI;

import Cliente.Cliente_Impl;
import common.Servidor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Set;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppCliente extends Application {

    private Cliente_Impl cliente;
    private Stage escenario;
    private Alert alertaError;
    private Alert alerta;
    private PrincipalController controladorPrincipal;
    private ArrayList<String> solicitantes;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        this.escenario = primaryStage;
        this.alertaError = new Alert(AlertType.ERROR);
        this.alerta = new Alert(AlertType.INFORMATION);
        this.solicitantes = new ArrayList<>();

        try {

            // 1. Lanzamos ventana inicio (datos servidor)
            FXMLLoader inicioLoader = new FXMLLoader(AppCliente.class.getResource("Inicio.fxml"));
            inicioLoader.setControllerFactory(c -> new InicioController(this));
            this.escenario.setScene(new Scene(inicioLoader.load()));
            this.escenario.show();

        } catch (IOException e) {
            System.out.println("No se pudo cargar la ventana inicial");
        }
    }

    @Override
    public void stop() {
        if (cliente != null) {
            cliente.shutdown();
        }
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

    public void registrar(String usuario, String contrasenha) {
        // Informamos del estado del registro
        try {
            switch (cliente.registrar(usuario, contrasenha)) {
                case 0:
                    alerta.setContentText("Registrado correctamente");
                    alerta.show();
                    break;
                case 1:
                    alertaError.setContentText("El usuario ya existe");
                    alertaError.show();
                    break;
                case 2:
                    alertaError.setContentText("Error de conexión, vuelva a intentarlo");
                    alertaError.show();
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login(String usuario, String contra) {
        // Si login correcto, lanzamos la ventana principal
        if (cliente.login(usuario, contra)) {
            try {
                // Lanzamos ventana principal
                controladorPrincipal = new PrincipalController(this);
                FXMLLoader principalLoader = new FXMLLoader(AppCliente.class.getResource("Principal.fxml"));
                principalLoader.setControllerFactory(c -> controladorPrincipal);
                escenario.setScene(new Scene(principalLoader.load()));
                actualizarAmigos(cliente.getAmigosOnline());
            } catch (IOException ex) {
                this.alertaError.setContentText("No se pudo cargar la ventana principal");
                this.alertaError.show();

            }
        } else {
            // Error en el login
            this.alertaError.setContentText("Error en el login");
            this.alertaError.show();
        }
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
        switch (cliente.enviarSolicitud(solicitado)) {
            case 0:
                alerta.setContentText("Solicitud enviada a " + solicitado);
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

    public void actualizarAmigos(Set<String> amigos) {
        controladorPrincipal.actualizarAmigos(amigos);
    }

    public synchronized void popUpSolicitud(String solicitante, String solicitado) {

        if (solicitantes.contains(solicitante)) {
            return; // Si el solicitante ya le mandó una petición, no se repite
        }        // Lanzamos popup
        solicitantes.add(solicitante);
        Platform.runLater(() -> {
            try {
                Stage nuevoEscenario = new Stage();
                nuevoEscenario.initModality(Modality.NONE);
                FXMLLoader amistadLoader = new FXMLLoader(AppCliente.class.getResource("SolicitudAmistad.fxml"));
                amistadLoader.setControllerFactory(c -> new SolicitudController(this, solicitante));
                nuevoEscenario.setScene(new Scene(amistadLoader.load()));
                nuevoEscenario.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public synchronized void responderSolicitud(String solicitante, boolean respuesta) {
        solicitantes.remove(solicitante);
        cliente.responderSolicitud(solicitante, respuesta);
    }

    public void informarSolicitud(String solicitado, boolean respuesta) {
        Platform.runLater(() -> {
            alerta.hide();
            if (respuesta) {
                alerta.setContentText("Ahora es amigo de " + solicitado);
            } else {
                alerta.setContentText("El usuario " + solicitado + " ha rechazado su peticion");
            }
            alerta.show();
        });
    }

    public void mostrarSugerencias(String busqueda) {
        ArrayList<String> sugerencias = cliente.obtenerSugerencias(busqueda);
        if (sugerencias != null && sugerencias.size() > 1) {
            try {
                // Mostrar ventana de sugerencias
                Stage nuevoEscenario = new Stage();
                nuevoEscenario.initModality(Modality.NONE);
                FXMLLoader sugerenciasLoader = new FXMLLoader(AppCliente.class.getResource("Sugerencias.fxml"));
                sugerenciasLoader.setControllerFactory(c -> new SugerenciasController(this, sugerencias));
                nuevoEscenario.setScene(new Scene(sugerenciasLoader.load()));
                nuevoEscenario.show();
            } catch (IOException ex) {
                alertaError.setContentText("No se han podido mostrar sugerencias");
                alertaError.show();
            }
        } else if (sugerencias != null && sugerencias.size() > 0) {
            enviarSolicitud(busqueda);
        } else {
            // Mostrar NO sugerencias
            alertaError.setContentText("No se han encontrado sugerencias");
            alertaError.show();
        }
    }

    public void cambiarContra(String contraNueva) {
        if(cliente.cambiarContra(contraNueva)){
            alerta.hide();
            alerta.setContentText("Cambiada correctamente");
            alerta.show();
        }else{
            alertaError.setContentText("Error");
            alertaError.show();
        }
            
        
    }
}
