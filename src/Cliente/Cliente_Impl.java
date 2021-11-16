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
    private Servidor servidor;
    private HashMap<String, Cliente> amigosOnline;
    
    public Cliente_Impl() throws RemoteException {
        ventanaServidor = new VServidor(this);
        ventanaServidor.setLocationRelativeTo(null);
        ventanaLogin = new VLogin(this);
        ventanaLogin.setLocationRelativeTo(null);
        ventanaPrincipal = new VPrincipal(this);
        ventanaPrincipal.setLocationRelativeTo(null);
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
    
    void login(String usuario, String contrasenha) {
        try {
            amigosOnline = servidor.login(this, usuario, contrasenha);
            if(amigosOnline == null) ventanaLogin.loginErroneo();
            else{
                // Activamos la ventana principal
                ventanaLogin.setVisible(false);
                ventanaPrincipal.setVisible(true);
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void enviarMensaje(String emisor, String mensaje) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void popUpSolicitud(String solicitante, String solicitado) {
        ventanaPrincipal.popUpSolicitud(solicitante, solicitado);
    }

    public void responderSolicitud(String solicitante, String solicitado, boolean respuesta) {
        servidor.responderSolicitud(solicitante, solicitado, respuesta);
    }

    @Override
    public void informarSolicitud(String solicitado, boolean respuesta) {
        ventanaPrincipal.informarSolicitud(solicitado, respuesta);
    }
    
}
