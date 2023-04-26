package controladores.chat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ComentarioControlador {

    @FXML
    private Label mensagem;

    @FXML
    private Label nome;

    public void setMensagem(String mensagem) {
        this.mensagem.setText(mensagem);
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

}
