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
        menuPedidos.setVisible(!menuPedidos.isVisible());
    }

    @FXML
    public void abrirMenuUsuario(ActionEvent event) {
        FadeTransition transicao = new FadeTransition(Duration.millis(300), menuUsuario);

        if (menuUsuario.isVisible()) {
            transicao.setFromValue(1);
            transicao.setToValue(0);
            transicao.setOnFinished( e -> {
                menuUsuario.setVisible(false);
                String caminhoSetaBaixo = PrincipalControlador.class.getResource("/telas/principal/images/seta_baixo.png").toExternalForm();
                menuUsuarioSeta.setImage(new Image(caminhoSetaBaixo));
            });
        } else {
            transicao.setFromValue(0);
            transicao.setToValue(1);
            transicao.setOnFinished( e -> {
                menuUsuario.setVisible(true);
                String caminhoSetaCima = PrincipalControlador.class.getResource("/telas/principal/images/seta_cima.png").toExternalForm();
                menuUsuarioSeta.setImage(new Image(caminhoSetaCima));
            });
        }

        transicao.play();
      
    }

}
