package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Servidor extends Remote {
    
    public int registrar(String nombre, String contrasenha) throws RemoteException;
    
    public HashMap<String, Cliente> login(Cliente cliente, String nombre, String contrasenha) throws RemoteException;
    
    public boolean enviarSolicitud(String solicitante, String solicitado) throws RemoteException;

    public void responderSolicitud(String solicitante, String solicitado, boolean respuesta) throws RemoteException;

    public void unlogin(String usuario) throws RemoteException;

}
