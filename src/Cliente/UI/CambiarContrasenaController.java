
package Cliente.UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CambiarContrasenaController {
    
    private final AppCliente app;
    
    @FXML
    private TextField txtContrasenaNueva;
    
    @FXML
    private TextField txtContrasenaActual;
    
    public CambiarContrasenaController(AppCliente app){
        this.app = app;
    }
    
    @FXML
    void pulsarBtnAceptar(ActionEvent e) {
        if(!txtContrasenaNueva.getText().isBlank() && !txtContrasenaActual.getText().isBlank())
            app.cambiarContra(txtContrasenaActual.getText(), txtContrasenaNueva.getText());
            Platform.runLater(() -> {
                ((Stage) txtContrasenaNueva.getScene().getWindow()).close();
            });
    }
    
}
