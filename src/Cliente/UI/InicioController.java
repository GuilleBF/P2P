package Cliente.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InicioController {

    @FXML
    private Button btnAceptar;

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
    
    public void initialize() {

        // do initialization and configuration work...

        // trivial example, could also be done directly in the fxml:
        labelLogo.setText("Foo");
    }

}
