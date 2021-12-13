package common;

import java.rmi.RemoteException;

public interface Cliente extends Cliente_Restricted {
    
    public void popUpSolicitud(String solicitante) throws RemoteException;

    public void informarSolicitud(String solicitado, boolean respuesta) throws RemoteException;
    
    public void anadirAmigoOnline(Cliente_Restricted amigo, String nombre) throws RemoteException;
    
    public void eliminarAmigoOnline(String nombre) throws RemoteException;
}
