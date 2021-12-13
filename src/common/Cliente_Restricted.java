package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cliente_Restricted extends Remote {

    public void enviarMensaje(String emisor, String mensaje) throws RemoteException;
    
}
