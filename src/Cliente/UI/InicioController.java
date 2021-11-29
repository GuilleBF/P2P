package Cliente.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class InicioController {
    
    private final AppCliente app;

    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtPuerto;

    public InicioController(AppCliente app) {
        this.app = app;
    }

    @FXML
    void pulsarBtnConectar(ActionEvent e) {
        String usuario = txtHost.getText();
        String puerto = txtPuerto.getText();
        if(!usuario.isBlank() && !puerto.isBlank())
            app.lanzarVentanaLogin(usuario, Integer.parseInt(puerto));
    }

}
