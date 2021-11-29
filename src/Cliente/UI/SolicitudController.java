
package Cliente.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SolicitudController {
    
    private final AppCliente app;
    private final String solicitante;
    
    @FXML
    private TextField texto;


    public SolicitudController(AppCliente app, String solicitante) {
        this.app = app;
        this.solicitante = solicitante;
        this.texto.setText("El usuario "+solicitante+" quiere ser su amigo");
    }

    @FXML
    void pulsarBtnAceptar(ActionEvent e) {
        app.responderSolicitud(solicitante, true);
        ((Stage) texto.getScene().getWindow()).close();
    }
    
    @FXML
    void pulsarBtnRechazar(ActionEvent e) {
        app.responderSolicitud(solicitante, true);
        ((Stage) texto.getScene().getWindow()).close();
    }
}
