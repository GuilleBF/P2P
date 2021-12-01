package Cliente.UI;

import javafx.scene.control.TextArea;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class PrincipalController {
    
    private final AppCliente app;
    private final HashMap<String,String> mensajes;
    
    @FXML
    private TextField txtAmistad;
    
    @FXML
    private TextField txtMensaje;
    
    @FXML
    private TextArea areaMensaje;
    
    @FXML
    private ListView listaAmigos;
        
    public PrincipalController(AppCliente app) {
        
        this.app = app;
        this.mensajes = new HashMap<>();
        
    }
    
    @FXML
    public void initialize() {
        
        // Añadimos el listener a la lista
        listaAmigos.getSelectionModel().selectedItemProperty().addListener((ObservableValue ov, Object t, Object t1) -> {
            actualizarPanel();
        });
    }
            
    synchronized void registrarMensaje(String emisor, String mensaje) {
        mensajes.put(emisor, mensajes.get(emisor) + mensaje);
        actualizarPanel();
    }

    private void actualizarPanel() {
        if(listaAmigos.getSelectionModel().getSelectedItem() != null) areaMensaje.setText(mensajes.get((String) listaAmigos.getSelectionModel().getSelectedItem()));
    }
    
    private String construirMensaje(String mensaje){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        return "["+app.getNombreUsuario()+"]["+LocalDateTime.now().format(formato)+"] "+mensaje+"\n";
    }
    
    @FXML
    synchronized void pulsarBtnMensaje(ActionEvent e) {
        String mensaje = construirMensaje(txtMensaje.getText());
        String amigo = (String) listaAmigos.getSelectionModel().getSelectedItem();
        if(!mensaje.isEmpty() && amigo != null){
            app.send(amigo, mensaje);
            mensajes.put(amigo, mensajes.get(amigo)+mensaje);
            actualizarPanel();
            txtMensaje.setText("");
        }
    }
    
    @FXML
    void pulsarBtnAmistad(ActionEvent e) {
        if(!txtAmistad.getText().isBlank())
            app.enviarSolicitud(txtAmistad.getText());
    }

    synchronized void actualizarAmigos(Set<String> amigos) {
        ObservableList<String> lista = FXCollections.observableArrayList();
        
        for(String nombreAmigo : amigos){
            lista.add(nombreAmigo);
            if(!mensajes.containsKey(nombreAmigo)) mensajes.put(nombreAmigo, "");
        }
        
        Platform.runLater(() -> {
            listaAmigos.setItems(lista);
        });
    }
    
}
