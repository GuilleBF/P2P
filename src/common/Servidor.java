package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Servidor extends Remote {
    
    public Boolean registrar(String nombre, String contrasenha) throws RemoteException;
    
    public HashMap<String, Cliente> login(Cliente cliente, String nombre, String contrasenha) throws RemoteException;

}
