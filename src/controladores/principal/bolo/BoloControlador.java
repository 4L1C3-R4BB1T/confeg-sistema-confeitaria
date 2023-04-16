package controladores.principal.bolo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BoloControlador {

    @FXML 
    private ImageView imagem;

    @FXML
    private Label descricao;

    @FXML
    private Label validade;

    @FXML
    private Label fabricao;

    @FXML
    private Label preco;

    @FXML
    private Label peso;


    public void setImagem(String caminho) {
        imagem.setImage(new Image(caminho));
    }

    public void setDescricao(String descricao) {
        this.descricao.setText(descricao);
    }

    public void setValidade(String validade) {
        this.validade.setText(validade);
    }

    public void setFabricao(String fabricao) {
        this.fabricao.setText(fabricao);
    }

    public void setPreco(String preco) {
        this.preco.setText(preco);
    }

    public void setPeso(String peso) {
        this.peso.setText(peso);
    }
}
