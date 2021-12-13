package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Servidor extends Remote {
    
    public int registrar(String nombre, String contrasenha) throws RemoteException;
    
    public HashMap<String, Cliente_Restricted> login(Cliente cliente, String nombre, String contrasenha) throws RemoteException;
    
    public boolean cambiarContrasenha(String nombre, String contrasenhaAnt, String contrasenhaNue) throws RemoteException;
    
    public int enviarSolicitud(String solicitante, String solicitado, String contrasenha) throws RemoteException;

    public void responderSolicitud(String solicitante, String solicitado, boolean respuesta, String contrasenha) throws RemoteException;

    public void unlogin(String usuario, String contrasenha) throws RemoteException;

    public ArrayList<String> obtenerSugerencias(String usuario, String contrasenha, String busqueda) throws RemoteException;

}
