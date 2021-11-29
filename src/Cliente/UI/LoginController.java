package Cliente.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    
    private final AppCliente app;
    
    @FXML
    private TextField txtUsuario;
    
    @FXML
    private TextField txtPassword;
        
    public LoginController(AppCliente app) {
        this.app = app;
    }
            
    @FXML
    void pulsarBtnRegistro(ActionEvent e) {
        String usuario = txtUsuario.getText();
        String contra = txtPassword.getText();
        if(!usuario.isBlank() && !contra.isBlank()){
            app.registrar(usuario, contra);
            txtPassword.setText("");
            txtUsuario.setText("");
        }
            
    }
    
    @FXML
    void pulsarBtnLogin(ActionEvent e) {
        String usuario = txtUsuario.getText();
        String contra = txtPassword.getText();
        if(!usuario.isBlank() && !contra.isBlank()){
            app.login(usuario, contra);
            txtPassword.setText("");
            txtUsuario.setText("");
        }
    }
}
