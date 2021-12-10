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

    boolean usuarioExiste(String nombre) {
        PreparedStatement query = null;
        boolean existe;
        try {
            query = conexion.prepareStatement("select nombre "
                    + "from usuario "
                    + "where nombre=?");
            query.setString(1, nombre);
            existe = query.executeQuery().next();
        } catch (SQLException e) {
            existe = false;
        }
        
        // Cerramos los punteros
        try {
            if (query != null) query.close();
        } catch (SQLException e) {
        }
        
        return existe;
    }

    boolean registrarUsuario(String nombre, String contrasenha) {
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
            registrado = false;
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
        }
        
        return registrado;
    }

    boolean checkUsuario(String nombre, String contrasenha) {
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
            correcto = false;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
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
            amigos = null;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
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
            solicitantes = null;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
        }
      
        return solicitantes;
    }

    int registrarSolicitud(String solicitante, String solicitado) {
        PreparedStatement insercion = null;
        int estado;
        
        try {
            insercion = conexion.prepareStatement("INSERT INTO solicitud "
                + "VALUES (?, ?) ");
            insercion.setString(1, solicitante);
            insercion.setString(2, solicitado);
            insercion.executeUpdate();
            estado = 0;
        } catch (SQLException e) {
            estado = 1;
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
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
        }
        
        // Cerramos los punteros
        try {
            if (insercion != null) insercion.close();
        } catch (SQLException e) {
        }
    }

    boolean sonAmigos(String usuario1, String usuario2) {
        PreparedStatement query = null;
        boolean amigos;
        
        try {
            query = conexion.prepareStatement("select * "
                    + "from amigo "
                    + "where usuario1=? and usuario2=?");
            query.setString(1, usuario1);
            query.setString(2, usuario2);
            amigos = query.executeQuery().next();
        } catch (SQLException e) {
             // En caso de de error asumimos que son amigos para impedir que continue la solicitud
            amigos = true;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
        }
      
        return amigos;
    }

    ArrayList<String> obtenerSugerencias(String busqueda) {
        PreparedStatement query = null;
        ResultSet resultados;
        ArrayList<String> sugerencias = new ArrayList<>();
        
        try {
            query = conexion.prepareStatement("select nombre "
                    + "from usuario "
                    + "where nombre like ?");
            query.setString(1, busqueda+"%");
            resultados = query.executeQuery();
            while(resultados.next()){
                sugerencias.add(resultados.getString("nombre"));
            }
        } catch (SQLException e) {
            sugerencias = null;
        }
        
        // Cerramos los punteros
        try {
            if(query!=null) query.close();
        } catch (SQLException e) {
        }
      
        return sugerencias;
    }

    boolean actualizarContra(String nombre, String contrasenhaNue) {
        PreparedStatement update = null;
        boolean exito;
        
        try {
            update = conexion.prepareStatement("UPDATE usuario "
                + "SET contrasenha=? "
                + "WHERE usuario=?");
            update.setString(1, contrasenhaNue);
            update.setString(2, nombre);
            exito = update.executeUpdate()>0;
        } catch (SQLException e) {
            exito = false;
        }
        
        // Cerramos los punteros
        try {
            if (update != null) update.close();
        } catch (SQLException e) {
        }
        
        return exito;
    }
}
