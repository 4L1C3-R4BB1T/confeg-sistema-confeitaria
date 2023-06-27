package controladores.sobre;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SobreControlador {

    private Stage stage;

    @FXML
    public void fechar(MouseEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    public void initialize() throws Exception { }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
