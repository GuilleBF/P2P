package Servidor;

import common.Cliente;
import common.Servidor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;


public class Servidor_Impl extends UnicastRemoteObject implements Servidor {
    
    private java.sql.Connection conexion = null;
    private final HashMap<String, Cliente> clientesOnline;
    
    
    public Servidor_Impl() throws RemoteException{
        establecerConexion();
        if (conexion == null){
            System.out.println("No se pudo establecer la conexión con las base de datos. Revise baseDatos.properties");
        }
        clientesOnline = new HashMap<>();
    }
    
    private void establecerConexion(){
        Properties configuracion = new Properties();
        Properties usuario = new Properties();
        
        try {
            
            try (FileInputStream archivoConfiguracion = new FileInputStream("baseDatos.properties")) {
                configuracion.load(archivoConfiguracion);
            }
            
            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            
            conexion = java.sql.DriverManager.getConnection("jdbc:" 
                    + configuracion.getProperty("gestor") + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);
            
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Boolean registrar(String nombre, String contrasenha) throws RemoteException {
        PreparedStatement query = null;
        PreparedStatement insercion = null;
        ResultSet resultado;
        Boolean estado;
        try {
            query = conexion.prepareStatement("select * "
                    + "from usuario "
                    + "where nombre=?");
            query.setString(1, nombre);
            resultado = query.executeQuery();
            if (resultado.next()) estado = false;
            else{
                insercion = conexion.prepareStatement("INSERT INTO usuario "
                    + "VALUES (?, ?) ");
                insercion.setString(1, nombre);
                insercion.setString(2, contrasenha);
                insercion.executeUpdate();
                estado = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            estado = false;
        } finally {
            try {
                if(query!=null) query.close();
                if(insercion!=null) insercion.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return estado;
    }

    @Override
    public synchronized HashMap<String, Cliente> login(Cliente cliente, String nombre, String contrasenha) throws RemoteException {
        PreparedStatement query = null;
        ResultSet resultado;
        HashMap<String, Cliente> amigos = new HashMap<>();
        String amigo;
        
        try {
            query = conexion.prepareStatement("select * "
                    + "from usuario "
                    + "where nombre=? and contrasenha=?");
            query.setString(1, nombre);
            query.setString(2, contrasenha);
            resultado = query.executeQuery();
            if (resultado.next()){
//                query = conexion.prepareStatement("select * "
//                    + "from amigo "
//                    + "where usuario1=?");
//                query.setString(1, nombre);
//                resultado = query.executeQuery();
//                while (resultado.next()){
//                    amigo = resultado.getString("usuario2");
//                    if(clientesOnline.containsKey(amigo)) amigos.put(amigo, clientesOnline.get(amigo));
//                }
            }
            else amigos = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(query!=null) query.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return amigos;
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
