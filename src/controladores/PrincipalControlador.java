package controladores;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class PrincipalControlador {

    @FXML
    private AnchorPane menuPedidos;

    @FXML
    private AnchorPane menuUsuario;

    @FXML 
    private ImageView menuUsuarioSeta;

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

}
