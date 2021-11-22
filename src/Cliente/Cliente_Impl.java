package Cliente;

import common.Cliente;
import common.Servidor;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Cliente_Impl extends UnicastRemoteObject implements Cliente {

    private final VServidor ventanaServidor;
    private final VLogin ventanaLogin;
    private final VPrincipal ventanaPrincipal;
    String nombreUsuario;
    private Servidor servidor;
    private HashMap<String, Cliente> amigosOnline;
    
    public Cliente_Impl() throws RemoteException {
        ventanaServidor = new VServidor(this);
        ventanaServidor.setLocationRelativeTo(null);
        ventanaLogin = new VLogin(this);
        ventanaLogin.setLocationRelativeTo(ventanaServidor);
        ventanaPrincipal = new VPrincipal(this);
        ventanaPrincipal.setLocationRelativeTo(ventanaLogin);
    }

    public static void main(String args[]) {
        try {
            Cliente_Impl cliente = new Cliente_Impl();
            cliente.lanzarVentanaServidor();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void lanzarVentanaServidor() {
        ventanaServidor.setLocationRelativeTo(null);
        ventanaServidor.setVisible(true);
    }
    
    void lanzarVentanaLogin(String nombre, int puerto) {
        try {
            this.servidor = (Servidor) Naming.lookup("rmi://" + nombre + ":" + puerto + "/servidor");
            ventanaServidor.setVisible(false);
            ventanaLogin.setVisible(true);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
    
    void registrar(String usuario, String contrasenha) {
        try {
            ventanaLogin.estadoRegistro(servidor.registrar(usuario, contrasenha));
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public synchronized void login(String usuario, String contrasenha) {
        try {
            amigosOnline = servidor.login(this, usuario, contrasenha);
            if(amigosOnline == null) ventanaLogin.loginErroneo();
            else{
                // Activamos la ventana principal
                ventanaLogin.setVisible(false);
                ventanaPrincipal.setVisible(true);
                ventanaPrincipal.actualizarAmigos(amigosOnline.keySet());
                this.nombreUsuario = usuario;
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public void enviarMensaje(String emisor, String mensaje) throws RemoteException {
        ventanaPrincipal.registrarMensaje(emisor, mensaje);
    }

    @Override
    public void popUpSolicitud(String solicitante, String solicitado) {
        ventanaPrincipal.popUpSolicitud(solicitante, solicitado);
    }

    public void responderSolicitud(String solicitante, boolean respuesta) {
        try {
            servidor.responderSolicitud(solicitante, this.nombreUsuario, respuesta);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void informarSolicitud(String solicitado, boolean respuesta) {
        ventanaPrincipal.informarSolicitud(solicitado, respuesta);
    }

    @Override
    public synchronized void anadirAmigoOnline(Cliente amigo, String nombre) throws RemoteException {
        amigosOnline.put(nombre, amigo);
        ventanaPrincipal.actualizarAmigos(amigosOnline.keySet());
    }

    @Override
    public synchronized void eliminarAmigoOnline(String nombre) throws RemoteException {
        amigosOnline.remove(nombre);
        ventanaPrincipal.actualizarAmigos(amigosOnline.keySet());
    }

    public synchronized void shutdown() {
        try {
            for(Cliente amigo:amigosOnline.values()) amigo.eliminarAmigoOnline(nombreUsuario);
            servidor.unlogin(nombreUsuario);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    public int enviarSolicitud(String solicitado) {
        // 0: registrada/enviada
        // 1: error indefinido
        // 2: son la misma persona
        // 3: ya son amigos
        try {
            return servidor.enviarSolicitud(this.nombreUsuario, solicitado);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }

    void send(String amigo, String mensaje) {
        try {
            amigosOnline.get(amigo).enviarMensaje(nombreUsuario, mensaje);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
