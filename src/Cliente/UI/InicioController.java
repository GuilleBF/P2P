package Cliente.UI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class InicioController {
    
    private final AppCliente app;
    
    @FXML
    private Button btnConectar;

    @FXML
    private Label labelHost;

    @FXML
    private Label labelLogo;

    @FXML
    private Label labelPuerto;

    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtPuerto;

    public InicioController(AppCliente app) {
        this.app = app;
    }

    @FXML
    void pulsarBtnConectar(ActionEvent e) {
        app.lanzarVentanaLogin(txtHost.getText(), Integer.parseInt(txtPuerto.getText()));
    }

}
