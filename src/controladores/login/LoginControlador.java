package controladores.login;

import java.sql.Connection;

import aplicacao.App;
import controladores.login.utilitarios.AlertaFracasso;
import controladores.login.utilitarios.AlertaInformacao;
import controladores.login.utilitarios.AlertaSucesso;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelos.entidadeDAO.FuncionarioDAO;

// TELA DE LOGIN
public class LoginControlador {

    @FXML
    private TextField campoUsuario;

    @FXML
    private TextField campoSenha;

    @FXML // Implementa isso, para exibir alertas
    private VBox areaDeAlerta;

    @FXML
    private StackPane areaDeFoto;

    private String[] fotos = {
            getClass().getResource("/telas/login/images/background-1.png").toExternalForm(),
            getClass().getResource("/telas/login/images/background-2.png").toExternalForm(),
            getClass().getResource("/telas/login/images/background-3.png").toExternalForm()
    };

    private int fotoAtual = 0;

    private Stage palcoLogin;
    private Stage palcoCadastro;

    private boolean continuarTrocandoImagem = true;

    private boolean clicouBotaoCadastrar = false;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);

    @FXML 
    public void cadastrar(MouseEvent event) throws Exception {
        if (clicouBotaoCadastrar) {
            return; /// Não pode clicar no botão cadastrar e exibir a tela várias vezes, impede isso.
        }
        clicouBotaoCadastrar = true;
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/cadastro/cadastro.fxml"));
        Parent raiz = carregar.load();
        CadastroControlador controlador = carregar.getController();
        Scene cena = new Scene(raiz);
        Stage palco = new Stage();
        palcoCadastro = palco;
        palco.setScene(cena);
        App.adicionarMovimento(palco, cena);
        palco.initStyle(StageStyle.UNDECORATED);
        palco.showAndWait();
        controlador.setEncerrarThreadValidacao(true);
        clicouBotaoCadastrar = false; 
        if(controlador.dadosForamSalvos()) {
            exibirAlertDeSucesso("Cadastro", "Operação realizada com sucesso");
        } else {
            exibirAlertDeFracasso("Cadastro", "Falha ou Cancelamento da solicitação de cadastro.");
        }
    }

    @FXML // Se a tela de cadastro estiver aberta e a tela de login for fechada, será fechado a tela cadastro tambem.
    public void fecharTela(MouseEvent event) {
        encerrarTelas();
    }

    @FXML 
    public void minimizarTela(MouseEvent event) {
        if (palcoLogin != null) {
            palcoLogin.setIconified(true);
        }
    }

    @FXML
    public void entrar(ActionEvent event) throws Exception {
        if (podeEntrar()) {
            carregarTelaPrincipal();
        } else {
            exibirAlertDeInformacao("Login", "Email ou senha inválidos");
        }
    }

    @FXML
    public void clicouTeclado(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            if (podeEntrar()) {
                carregarTelaPrincipal();
            } else {
                exibirAlertDeInformacao("Login", "Email ou senha inválidos");
            }
        }
    }

    public boolean podeEntrar() {
       // return funcionarioDAO.autenticar(campoUsuario.getText(), campoSenha.getText());
       return true;
    }

    public void carregarTelaPrincipal() {
        try {
            Parent raiz = FXMLLoader.load(getClass().getResource("/telas/principal/principal.fxml"));
            Scene cena = new Scene(raiz);
            Stage palco = new Stage();
            palco.initStyle(StageStyle.TRANSPARENT);
            palco.setScene(cena);
            encerrarTelas();
            App.adicionarMovimento(palco, cena);
            palco.show();
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
    }

    public void setPalco(Stage palcoLogin) {
        this.palcoLogin = palcoLogin;
    }

    @FXML
    public void initialize() {
        new Thread(() -> {
            while (continuarTrocandoImagem) {
                try {
                    Platform.runLater(() -> {
                        areaDeFoto.setStyle(String.format("-fx-background-image: url('%s') !important;",
                                fotos[fotoAtual++ % fotos.length]));
                    });
                    Thread.sleep(4000);

                } catch (Exception erro) {
                }
            }
        }).start();
    }

    public void encerrarTelas() {
        if (palcoLogin != null) {
            continuarTrocandoImagem = false;
            if (palcoCadastro != null) { 
                palcoCadastro.close();
            }
            palcoLogin.close();
        }
    }

    public void exibirAlertDeSucesso(String titulo, String descricao) throws Exception {
        areaDeAlerta.setVisible(false);
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/alertas/sucesso.fxml"));
        Parent raiz = carregar.load();
        AlertaSucesso controlador = carregar.getController();
        controlador.setTitulo(titulo);
        controlador.setDescricao(descricao);
        limparAreaDeAlerta();
        areaDeAlerta.getChildren().add(raiz);
        adicionaEfeitoSuave(areaDeAlerta);
        new Thread(() -> {

            try {
                Thread.sleep(1500);
                Platform.runLater(() -> removerEfeitoSuave(areaDeAlerta));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void exibirAlertDeFracasso(String titulo, String descricao) throws Exception {
        areaDeAlerta.setVisible(false);
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/alertas/fracasso.fxml"));
        Parent raiz = carregar.load();
        AlertaFracasso controlador = carregar.getController();
        controlador.setTitulo(titulo);
        controlador.setDescricao(descricao);
        limparAreaDeAlerta();
        areaDeAlerta.getChildren().add(raiz);
        adicionaEfeitoSuave(areaDeAlerta);
        new Thread(() -> {

            try {
                Thread.sleep(1500);
                Platform.runLater(() -> removerEfeitoSuave(areaDeAlerta));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void exibirAlertDeInformacao(String titulo, String descricao) throws Exception {
        areaDeAlerta.setVisible(false);
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/alertas/informacao.fxml"));
        Parent raiz = carregar.load();
        AlertaInformacao controlador = carregar.getController();
        controlador.setTitulo(titulo);
        controlador.setDescricao(descricao);
        limparAreaDeAlerta();
        areaDeAlerta.getChildren().add(raiz);
        adicionaEfeitoSuave(areaDeAlerta);
        new Thread(() -> {

            try {
                Thread.sleep(1500);
                Platform.runLater(() -> removerEfeitoSuave(areaDeAlerta));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void adicionaEfeitoSuave(VBox box) {
        FadeTransition transicao = new FadeTransition(Duration.millis(500), box);
        transicao.setFromValue(0);
        transicao.setToValue(1);
        transicao.setOnFinished( e -> box.setVisible(true));
        transicao.play();
    }

    public void removerEfeitoSuave(VBox box) {
        FadeTransition transicao = new FadeTransition(Duration.millis(1000), box);
        transicao.setFromValue(1);
        transicao.setToValue(0);
        transicao.setOnFinished( e -> box.setVisible(false));
        transicao.play();
    }

    public void limparAreaDeAlerta() {
        areaDeAlerta.getChildren().clear();
    }   

}
