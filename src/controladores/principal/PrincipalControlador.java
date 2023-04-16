package controladores.principal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import aplicacao.App;
import controladores.crudBolo.CrudBoloControlador;
import controladores.login.LoginControlador;
import controladores.principal.bolo.BoloControlador;
import controladores.principal.perfil.PerfilControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.BoloDAO;
import modelos.entidades.Funcionario;

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
    private Button areaFuncionario;

    @FXML 
    private FlowPane areaBolo;

    @FXML
    private Button botaoAdministradorUsuario;

    @FXML 
    private HBox areaDeAlerta;

    private Stage tela;
    
    private List<Stage> telas = new ArrayList<>();

    private BoloDAO boloDAO = new BoloDAO(App.conexao);
    
    // São os menus PRINCIPAL, PEDIDOS, BOLOS, ETC.
    private List<Button> menuBotoes = new ArrayList<>();

    private Funcionario conectado;

    // Verificar se clicou nos botoes para que não haja duplicidade
    private boolean clicouPerfil = false;
    private boolean clicouBolo = false;

    @FXML
    public void abrirMenuPedidos(ActionEvent event) {
        if (menuPedidos.isVisible()) {
            App.removerEfeitoSuave(menuPedidos);
        } else {
            App.adicionaEfeitoSuave(menuPedidos);
        }
    }

    @FXML
    public void abrirMenuUsuario(ActionEvent event) {
        if (menuUsuario.isVisible()) {
            App.removerEfeitoSuave(menuUsuario);
            String caminhoSetaBaixo = PrincipalControlador.class.getResource("/telas/principal/images/seta_baixo.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaBaixo));
        } else {
            App.adicionaEfeitoSuave(menuUsuario);
            String caminhoSetaCima = PrincipalControlador.class.getResource("/telas/principal/images/seta_cima.png").toExternalForm();
            menuUsuarioSeta.setImage(new Image(caminhoSetaCima));
        }
    }

    @FXML
    public void fecharTela(MouseEvent event) {
        if (tela != null) { // Fechar o palco atual, no caso a tela princpal
            tela.close();
        }
    }

    @FXML 
    public void minimizarTela(MouseEvent event) {
        tela.setIconified(true);
    }

    @FXML
    public void verPerfil(MouseEvent event) throws Exception {
        if (!clicouPerfil) {
            carregarTelaPerfil(conectado);
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
        }
    }

    @FXML
    public void irTelaBolo(ActionEvent event) throws Exception {
       if (!clicouBolo) {
            carregarTelaCrudBolo();
       } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "TELA", "A tela está sendo exibida.");
       }
    }

    @FXML
    public void irParaTelaFuncionarios(ActionEvent event) {
        System.out.println("Ir para tela funcionários");
    }

    @FXML
    public void deslogar(MouseEvent event) {
        carregarTelaLogin();
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
        boloDAO.buscarTodos().stream()
            .map( bolo -> {
                try {
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/bolo/bolo.fxml"));
                    Node node = carregar.load();
                    BoloControlador controlador = carregar.getController();
                    controlador.setImagem(getClass().getResource("/telas/principal/bolo/images/").toExternalForm() + bolo.getSabor().getCodigo() + ".png");
                    controlador.setDescricao(bolo.getDescricao());
                    controlador.setFabricao("Fabricação: " + bolo.getFabricacao().toString());
                    controlador.setPeso("Peso: " + bolo.getPeso().toString() + " kg");
                    controlador.setPreco("Preço: R$ " + bolo.getPreco().toString());
                    controlador.setValidade("Validade: " + bolo.getVencimento().toString());
                    return node;
                } catch (Exception erro) {
                    return null;
                }
            })
            .forEach( bolo -> areaBolo.getChildren().add(bolo));
    }

    public void carregarTelaPerfil(Funcionario funcionario) {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/perfil/perfil.fxml"));
            Parent raiz = carregar.load();
            PerfilControlador controlador = carregar.getController();
            controlador.setConectado(funcionario);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage();
            controlador.setTela(palco);
            telas.add(palco);
            palco.setScene(cena);
            clicouPerfil = true;
            palco.initStyle(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouPerfil = false;
        } catch (Exception erro) {
            System.out.println("Erro linha 140 PrincipalControlador");
        }
    }

    public void carregarTelaLogin() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/login.fxml"));
            Parent raiz = carregar.load();
            LoginControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setPalco(palco);
            palco.setScene(cena);
            fecharTodasTelas();
            App.adicionarMovimento(palco, cena);
            palco.show();
        } catch (Exception erro) {
            System.out.println("Erro linha 117 PrincipalControlador");
        }
    }

    public void carregarTelaCrudBolo() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/bolos/bolo.fxml"));
            Parent raiz = carregar.load();
            CrudBoloControlador controlador = carregar.getController();
            Scene cena = new Scene(raiz);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            controlador.setTela(palco);
            palco.setScene(cena);
            telas.add(palco);
            clicouBolo = true;
            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            telas.remove(palco);
            clicouBolo = false;
        } catch (Exception erro) {
            System.out.println("Erro linha 146 PrincipalControlador");
        }
    }

    public void fecharTodasTelas() {
        telas.forEach( tela -> {
            if (tela != null) {
                tela.hide();
                tela.close();
            }
        });
    }

    public void setConectado(Funcionario funcionario) {
        if (funcionario != null) {
            conectado = funcionario;
            areaFuncionario.setText(conectado.getNome());
            if (funcionario.getTipo().getDescricao().intern() == "Gerente") {
                botaoAdministradorUsuario.setVisible(true);
            }
        } else {
            throw new RuntimeException("O funcionário não foi conectado a tela PrincipalControlador");
        }
    }

    public void setTela(Stage tela) {
        if (tela != null) {
            this.tela = tela;
            telas.add(tela);
        }
    }

}
