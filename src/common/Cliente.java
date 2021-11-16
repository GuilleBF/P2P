package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cliente extends Remote {

    public void enviarMensaje(String emisor, String mensaje) throws RemoteException;
    
    public void popUpSolicitud(String solicitante, String solicitado) throws RemoteException;

    public void informarSolicitud(String solicitado, boolean respuesta) throws RemoteException;
    
    public void anadirAmigoOnline(Cliente amigo, String nombre) throws RemoteException;
    
    public void eliminarAmigoOnline(String nombre) throws RemoteException;
}
