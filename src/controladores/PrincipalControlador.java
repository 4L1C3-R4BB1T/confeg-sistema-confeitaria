package controladores;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class PrincipalControlador {

    @FXML
    private AnchorPane menuPedidos;

    @FXML
    private AnchorPane menuUsuario;

    @FXML 
    private ImageView menuUsuarioSeta;

    @FXML
    private HBox areaBotaoMenu;
    
    // São os menus PRINCIPAL, PEDIDOS, BOLOS, ETC.
    private List<Button> menuBotoes = new ArrayList<>();

    @FXML
    public void abrirMenuPedidos(ActionEvent event) {
        adicionaEfeitoMenuSuave(menuPedidos);
    }

    @FXML
    public void abrirMenuUsuario(ActionEvent event) {
        if (!adicionaEfeitoMenuSuave(menuUsuario)) {
            String caminhoSetaBaixo = PrincipalControlador.class.getResource("/telas/principal/images/seta_baixo.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaBaixo));
        } else {
            String caminhoSetaCima = PrincipalControlador.class.getResource("/telas/principal/images/seta_cima.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaCima));
        }
    }

    public boolean adicionaEfeitoMenuSuave(AnchorPane menu) {
        FadeTransition transicao = new FadeTransition(Duration.millis(300), menu);
        if (menu.isVisible()) {
            transicao.setFromValue(1);
            transicao.setToValue(0);
            transicao.setOnFinished( e -> menu.setVisible(false));
            transicao.play();
            return false;
        } else {
            transicao.setFromValue(0);
            transicao.setToValue(1);
            transicao.setOnFinished( e -> menu.setVisible(true));
            transicao.play();
            return true;
        }
    }

    @FXML
    public void initialize() {
        areaBotaoMenu.getChildren().forEach( elemento -> {
            if (!(elemento instanceof Button)) return;
            Button botao = (Button) elemento;
            botao.setOnMouseClicked( event -> {
                menuPedidos.setVisible(false);
                menuBotoes.stream().forEach( bt -> bt.getStyleClass().remove("ativo"));
                botao.getStyleClass().add("ativo");
            });
            menuBotoes.add(botao);
        });
       
    }

}
