package controladores.principal;

import java.util.ArrayList;
import java.util.List;

import aplicacao.App;
import controladores.principal.bolo.BoloControlador;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidades.Bolo;

// TELA PRINCIPAL DA APLICAÇÃO
public class PrincipalControlador {

    @FXML
    private AnchorPane menuPedidos;

    @FXML
    private AnchorPane menuUsuario;

    @FXML 
    private ImageView menuUsuarioSeta;

    @FXML
    private HBox areaBotaoMenu;

    @FXML 
    private FlowPane areaBolo;

    private BoloDAO boloDAO = new BoloDAO(App.conexao);
    
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
                limparJanelasAbertas();
                menuBotoes.stream().forEach( bt -> bt.getStyleClass().remove("ativo"));
                botao.getStyleClass().add("ativo");
            });
            menuBotoes.add(botao);
        });
        atualizarAreaBolo();
       
    }

    public void limparJanelasAbertas() {
        // Por enquanto....
        menuPedidos.setVisible(false);
    }

    public void atualizarAreaBolo() {
        areaBolo.getChildren().clear();
        String telaBolo = "/telas/principal/bolo/bolo.fxml";
        String baseFotoBolo = getClass().getResource("/telas/principal/bolo/images/").toExternalForm();

        try {

            for(Bolo objeto: boloDAO.buscarTodos()) {
                FXMLLoader carregar = new FXMLLoader(getClass().getResource(telaBolo));
                Node node = carregar.load();
                BoloControlador controlador = carregar.getController();
    
                controlador.setImagem(baseFotoBolo + objeto.getSabor().getCodigo() + ".png");
                areaBolo.getChildren().add(node);
            }



           
            
        } catch (Exception erro) {
            System.out.println("Erro linha 111 Class: PrincipalControlador");
            erro.printStackTrace();
        }

  
    }

}
