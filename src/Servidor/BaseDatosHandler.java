package Servidor;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class BaseDatosHandler {

    private java.sql.Connection conexion = null;

    public BaseDatosHandler() {
        conexion = establecerConexion("baseDatos.properties");
        if (conexion == null) {
            System.out.println("No se pudo establecer la conexión con las base de datos. Revise baseDatos.properties");
        }
    }

    private java.sql.Connection establecerConexion(String archivoConf) {
        java.sql.Connection conn = null;
        Properties configuracion = new Properties();
        Properties usuario = new Properties();

        try {

            try (FileInputStream archivoConfiguracion = new FileInputStream(archivoConf)) {
                configuracion.load(archivoConfiguracion);
            }

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));

            conn = java.sql.DriverManager.getConnection("jdbc:"
                    + configuracion.getProperty("gestor") + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public boolean usuarioExiste(String nombre) {
        PreparedStatement query = null;
        boolean existe;
        try {
            query = conexion.prepareStatement("select nombre "
                    + "from usuario "
                    + "where nombre=?");
            query.setString(1, nombre);
            existe = query.executeQuery().next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            existe = false;
        }
        
        // Cerramos los punteros
        try {
            if (query != null) query.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return existe;
    }

    public boolean registrarUsuario(String nombre, String contrasenha) {
        PreparedStatement insercion = null;
        boolean registrado;
        
        try {
            insercion = conexion.prepareStatement("INSERT INTO usuario "
                + "VALUES (?, crypt(?,gen_salt('md5')))");
            insercion.setString(1, nombre);
            insercion.setString(2, contrasenha);
            insercion.executeUpdate();
            registrado = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            registrado = false;
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return registrado;
    }

    public boolean checkUsuario(String nombre, String contrasenha) {
        PreparedStatement query = null;
        boolean correcto;
        
        try {
            query = conexion.prepareStatement("select * "
                    + "from usuario "
                    + "where nombre=? and contrasenha=crypt(?,contrasenha)");
            query.setString(1, nombre);
            query.setString(2, contrasenha);
            correcto = query.executeQuery().next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            correcto = false;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      
        return correcto;
    }

    ArrayList<String> obtenerAmigos(String nombre) {
        PreparedStatement query = null;
        ResultSet resultados;
        ArrayList<String> amigos = new ArrayList<>();
        
        try {
            query = conexion.prepareStatement("select usuario2 "
                    + "from amigo "
                    + "where usuario1=?");
            query.setString(1, nombre);
            resultados = query.executeQuery();
            while(resultados.next()){
                amigos.add(resultados.getString("usuario2"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            amigos = null;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      
        return amigos;
    }

    ArrayList<String> obtenerSolicitudes(String solicitado) {
        PreparedStatement query = null;
        ResultSet resultados;
        ArrayList<String> solicitantes = new ArrayList<>();
        
        try {
            query = conexion.prepareStatement("select solicitante "
                    + "from solicitud "
                    + "where solicitado=?");
            query.setString(1, solicitado);
            resultados = query.executeQuery();
            while(resultados.next()){
                solicitantes.add(resultados.getString("solicitante"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            solicitantes = null;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      
        return solicitantes;
    }

    public boolean registrarSolicitud(String solicitante, String solicitado) {
        PreparedStatement insercion = null;
        boolean estado;
        
        try {
            insercion = conexion.prepareStatement("INSERT INTO solicitud "
                + "VALUES (?, ?) ");
            insercion.setString(1, solicitante);
            insercion.setString(2, solicitado);
            insercion.executeUpdate();
            estado = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            estado = false;
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return estado;
    }

    void registrarAmistad(String solicitante, String solicitado) {
        PreparedStatement insercion = null;
        
        try {
            insercion = conexion.prepareStatement("INSERT INTO amigo "
                + "VALUES (?, ?), (?, ?)");
            insercion.setString(1, solicitante);
            insercion.setString(2, solicitado);
            insercion.setString(3, solicitado);
            insercion.setString(4, solicitante);
            insercion.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void eliminarSolicitud(String solicitante, String solicitado) {
        PreparedStatement insercion = null;
        
        try {
            insercion = conexion.prepareStatement("DELETE FROM solicitud "
                + "where solicitante=? and solicitado=?");
            insercion.setString(1, solicitante);
            insercion.setString(2, solicitado);
            insercion.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
