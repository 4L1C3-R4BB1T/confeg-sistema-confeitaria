package controladores.principal.bolo;

import aplicacao.App;
import controladores.crudPedidos.RegistrarPedidoControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidades.Bolo;

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

    private Bolo bolo;
    
    private Node areaDeAlerta;

    private boolean clicouBotaoPedir = false;

    @FXML
    public void pedir(ActionEvent event) throws Exception {
        if (!clicouBotaoPedir) {
            carregarTelaDePedido(bolo);
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "JANELA", "Já nela aberta.");
        }
    }

    public void carregarTelaDePedido(Bolo bolo) {
        try {
            clicouBotaoPedir = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/pedidos/cadastro/registrarPedido.fxml"));
            Parent raiz = carregar.load();
            RegistrarPedidoControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            if (bolo != null) {
                controlador.setBolo(bolo);
            }
            palco.showAndWait();
            clicouBotaoPedir = false;
            if (controlador.getRegistrouPedido()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "PEDIDO", "Pedido registrado com sucesso");
            } else if (controlador.getErro()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "PEDIDO", "Não foi possível registrar pedido.");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

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
    
    public void setBolo(Bolo bolo) {
        this.bolo = bolo;
    }

    public void setAreaDeAlerta(Node node) {
        this.areaDeAlerta = node;
    }

}
