package Servidor;

import common.Cliente;
import common.Servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor_Impl extends UnicastRemoteObject implements Servidor {
    
    private final HashMap<String, Cliente> usuariosOnline;
    private final BaseDatosHandler bbdd;
    
    public Servidor_Impl() throws RemoteException {
        usuariosOnline = new HashMap<>();
        bbdd = new BaseDatosHandler();
    }

    @Override
    public int registrar(String nombre, String contrasenha) throws RemoteException {
        if(!bbdd.usuarioExiste(nombre)){
            if(bbdd.registrarUsuario(nombre, contrasenha)) return 0; // Usuario registrado
            return 2; // Error: error indeterminado
        }
        return 1; // Error: Usuario ya existe
    }

    @Override
    public synchronized HashMap<String, Cliente> login(Cliente cliente, String nombre, String contrasenha) throws RemoteException {
        if(bbdd.checkUsuario(nombre, contrasenha)){
            
            // Ponemos al cliente como online
            usuariosOnline.put(nombre, cliente);
            
            // Procesamos sus solicitudes de amistad guardadas
            for(String solicitante : bbdd.obtenerSolicitudes(nombre)){
                enviarSolicitud(solicitante, nombre);
            }
            
            // Le obtenemos su lista de amigos
            ArrayList<String> amigos = bbdd.obtenerAmigos(nombre);
            if (amigos == null) return null; // Si hubo un problema obteniendo la lista de amigos
            
            // Le devolvemos sus amigos online
            HashMap<String, Cliente> amigosOnline = new HashMap<>();
            for(String amigo : amigos){
                if(usuariosOnline.containsKey(amigo)) amigosOnline.put(amigo, usuariosOnline.get(amigo));
            }
            
            // Le añadimos a las listas de sus amigos
            for(Cliente amigo:amigosOnline.values()) amigo.anadirAmigoOnline(cliente, nombre);
            
            return amigosOnline;
        }
        return null; // Si no existe un usuario con esas credenciales
    }
    
    @Override
    public synchronized boolean enviarSolicitud(String solicitante, String solicitado){
        // La flag bd indica si la solicitud se ha lanzado desde la base de datos
        
        Cliente cliente_solicitado = usuariosOnline.get(solicitado);
        
        if(solicitado!=null){  // Si está online, se le manda el popup
            try {
                cliente_solicitado.popUpSolicitud(solicitante, solicitado);
                return true;
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        
        // Sino, queda registrada
        return bbdd.registrarSolicitud(solicitante, solicitado);

    }
    
    @Override
    public void responderSolicitud(String solicitante, String solicitado, boolean respuesta) {
        Cliente cliente_solicitante = usuariosOnline.get(solicitante);
        Cliente cliente_solicitado = usuariosOnline.get(solicitado);
        
        // Si el solicitante está online, le informamos de la respuesta y añadimos el uno a la lista del otro
        if(cliente_solicitante != null) try {
            cliente_solicitante.informarSolicitud(solicitado, respuesta);
            cliente_solicitante.anadirAmigoOnline(cliente_solicitado, solicitado);
            cliente_solicitado.anadirAmigoOnline(cliente_solicitante, solicitante);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        
        // Registramos la nueva amistad
        if(respuesta) bbdd.registrarAmistad(solicitante, solicitado);
        
        // Eliminamos la solicitud si estaba en la BBDD
        bbdd.eliminarSolicitud(solicitante, solicitado);
    }

    public static void main(String args[]) {
        
        // Inicialización del servidor
        try {
            
            Servidor_Impl servidor = new Servidor_Impl();
            
            // Obtenemos el puerto
            BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Introduzca el puerto del servidor (1099 por defecto):");
            String input = consola.readLine().trim();
            int puerto;
            if(!input.isEmpty()) puerto = Integer.parseInt(input);
            else puerto = 1099;

            // Iniciamos el registro y registramos el servidor
            try {
                Registry registro = LocateRegistry.getRegistry(puerto);
                registro.list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(puerto);
            }
            Naming.rebind("rmi://localhost:" + puerto + "/servidor", (Servidor) servidor);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
