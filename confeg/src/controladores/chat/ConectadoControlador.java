package controladores.chat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConectadoControlador {

    @FXML
    private Label tipo;

    @FXML
    private Label nome;

    public void setTipo(String tipo) {
        this.tipo.setText(tipo);
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

}
