package Cliente;

import Cliente.UI.AppCliente;
import common.Cliente;
import common.Servidor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Cliente_Impl extends UnicastRemoteObject implements Cliente {

    String nombreUsuario;
    private HashMap<String, Cliente> amigosOnline;
    private final Servidor servidor;
    private final AppCliente app;
    
    public Cliente_Impl(Servidor servidor, AppCliente app) throws RemoteException{
        this.servidor = servidor;
        this.app = app;
    }
    
    public int registrar(String usuario, String contrasenha) throws RemoteException {
        return servidor.registrar(usuario, contrasenha);
    }
    
    public synchronized boolean login(String usuario, String contrasenha) {
        try {
            amigosOnline = servidor.login(this, usuario, contrasenha);
            if(amigosOnline == null) return false;
            else{
                this.nombreUsuario = usuario;
                return true;
            }
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public void enviarMensaje(String emisor, String mensaje) throws RemoteException {
        app.registrarMensaje(emisor, mensaje);
    }

    @Override
    public void popUpSolicitud(String solicitante, String solicitado) {
        app.popUpSolicitud(solicitante, solicitado);
    }

    public void responderSolicitud(String solicitante, boolean respuesta) {
        try {
            servidor.responderSolicitud(solicitante, this.nombreUsuario, respuesta);
        } catch (RemoteException e) {
        }
    }

    @Override
    public void informarSolicitud(String solicitado, boolean respuesta) {
        app.informarSolicitud(solicitado, respuesta);
    }

    @Override
    public synchronized void anadirAmigoOnline(Cliente amigo, String nombre) throws RemoteException {
        amigosOnline.put(nombre, amigo);
        app.actualizarAmigos(amigosOnline.keySet());
    }

    @Override
    public synchronized void eliminarAmigoOnline(String nombre) throws RemoteException {
        amigosOnline.remove(nombre);
        app.actualizarAmigos(amigosOnline.keySet());
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

    public void send(String amigo, String mensaje) throws RemoteException {
        amigosOnline.get(amigo).enviarMensaje(nombreUsuario, mensaje);
    }

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }
    
}
