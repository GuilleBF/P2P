package Cliente.UI;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SugerenciasController {

    private final AppCliente app;
    private final ArrayList<String> sugerencias;

    @FXML
    private ListView listaSugerencias;

    public SugerenciasController(AppCliente app, ArrayList<String> sugerencias) {
        this.app = app;
        this.sugerencias = sugerencias;
    }

    @FXML
    public void initialize() {
        ObservableList<String> lista = FXCollections.observableArrayList();

        sugerencias.forEach(sugerencia -> {
            lista.add(sugerencia);
        });

        Platform.runLater(() -> {
            listaSugerencias.setItems(lista);
        });
    }

    @FXML
    void pulsarBtnEnviar(ActionEvent e) {
        String seleccionado = (String) listaSugerencias.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            app.enviarSolicitud(seleccionado);
            Platform.runLater(() -> {
                ((Stage) listaSugerencias.getScene().getWindow()).close();
            });
        }
    }

}
