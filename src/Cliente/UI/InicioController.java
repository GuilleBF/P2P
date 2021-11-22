package Cliente.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class InicioController {

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

    @FXML
    public void initialize() {
    }

    @FXML
    protected void pulsarBtnConectar(ActionEvent e) {
        System.out.println("Hola");
        labelHost.setText("Hola");
    }

}
