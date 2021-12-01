
package Cliente.UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class SolicitudController {
    
    private final AppCliente app;
    private final String solicitante;
    
    @FXML
    private Label texto;


    public SolicitudController(AppCliente app, String solicitante) {
        this.app = app;
        this.solicitante = solicitante;
    }
    
     @FXML
    public void initialize() {
        this.texto.setText("El usuario "+solicitante+" quiere ser su amigo");
        this.texto.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML
    void pulsarBtnAceptar(ActionEvent e) {
        app.responderSolicitud(solicitante, true);
        Platform.runLater(() -> {
            ((Stage) texto.getScene().getWindow()).close();
        });
        
    }
    
    @FXML
    void pulsarBtnRechazar(ActionEvent e) {
        app.responderSolicitud(solicitante, false);
        Platform.runLater(() -> {
            ((Stage) texto.getScene().getWindow()).close();
        });
    }
}
