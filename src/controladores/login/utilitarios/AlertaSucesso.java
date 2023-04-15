package controladores.login.utilitarios;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AlertaSucesso {
    
    @FXML
    private Label titulo;

    @FXML
    private Label descricao;

    @FXML
    public void fechar(MouseEvent event) {
        Window janela = (Window) ((Node) event.getSource()).getScene().getWindow();
        ((Stage) janela).close();
    }

    public void setTitulo(String valor) {
        titulo.setText(valor);
        
    }

    public void setDescricao(String valor) {
        descricao.setText(valor);
    }
}
