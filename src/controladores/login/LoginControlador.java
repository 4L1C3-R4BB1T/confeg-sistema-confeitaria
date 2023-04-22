package controladores.login;

import java.util.ArrayList;
import java.util.List;
import aplicacao.App;
import controladores.principal.PrincipalControlador;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidades.Funcionario;

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
    
    @FXML
    private ImageView areaCarregar;

    @FXML
    private Button botaoEntrar;

    private String[] fotos = {
            getClass().getResource("/telas/login/img/background-1.png").toExternalForm(),
            getClass().getResource("/telas/login/img/background-2.png").toExternalForm(),
            getClass().getResource("/telas/login/img/background-3.png").toExternalForm()
    };

    private int fotoAtual = 0;

    private Stage palcoLogin;

    private boolean continuarTrocandoImagem = true;

    private boolean clicouBotaoCadastrar = false;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);

    private Funcionario funcionario;

    private List<Stage> telas = new ArrayList<>();

    private Timeline timeline;

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
        telas.add(palco);
        controlador.setTela(palco);
        palco.setScene(cena);
        App.adicionarMovimento(palco, cena);
        palco.initStyle(StageStyle.UNDECORATED);
        palco.showAndWait();
        controlador.setEncerrarThreadValidacao(true);
        clicouBotaoCadastrar = false; 
        if(controlador.dadosForamSalvos()) {
            App.exibirAlert(areaDeAlerta, "SUCESSO", "CADASTRO", "Operação realizada com sucesso.");
        } else if (controlador.getErro()) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "CADASTRO", "Não foi possível cadastrar.");
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
    public void entrar(ActionEvent event) {
        if (podeEntrar()) {
            logar();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "LOGIN", "Email ou Senha inválidos.");
        }
    }

    @FXML
    public void clicouTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (podeEntrar()) {
                logar();
            } else {
                App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "LOGIN", "Email ou Senha inválidos.");
            }
        }
    }

    public boolean podeEntrar() {
        if (campoUsuario.getText().isEmpty() &&  campoSenha.getText().isEmpty()) {
            return false;
        }

        funcionario = funcionarioDAO.autenticar(campoUsuario.getText(), campoSenha.getText());

        if (funcionario != null) {
            return true;
        }
       return false;
    }

    public void carregarTelaPrincipal(Funcionario conectado) {
        try{
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/principal/principal.fxml"));
            Parent raiz = carregar.load();
            PrincipalControlador controlador = carregar.getController();
            controlador.setConectado(conectado);
            Scene cena = new Scene(raiz);
            Stage palco = new Stage();
            controlador.setTela(palco);
            palco.initStyle(StageStyle.TRANSPARENT);
            palco.setScene(cena);
            encerrarTelas();
            App.adicionarMovimento(palco, cena);
            palco.show();
        } catch (Exception erro) {
            erro.printStackTrace();
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
                } catch (Exception erro) {}
            }
        }).start();
    }

    public void logar() {
        new Thread(() -> {
            Platform.runLater(() -> adicionarEfeitoCarregar());
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    if (timeline != null) {
                        timeline.stop();
                    }
                    botaoEntrar.setText("Usuário encontrado");
                });
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> carregarTelaPrincipal(funcionario));
            
        }).start();
    }

    public void adicionarEfeitoCarregar() {
        Image gif = new Image(getClass().getResource("/telas/login/img/carregando.gif").toExternalForm());
        areaCarregar.setImage(gif);
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        KeyValue keyValue = new KeyValue(areaCarregar.imageProperty(), gif);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
        tl.getKeyFrames().add(keyFrame);
        areaCarregar.setFitWidth(32);
        areaCarregar.setFitWidth(32);
        botaoEntrar.setText("Carregando...");
        timeline = tl;
        tl.play();
    }

    public void encerrarTelas() {
        if (palcoLogin != null) {
            continuarTrocandoImagem = false;
            telas.forEach( tela -> {
                if (tela != null) {
                    tela.close();
                }
            });
            palcoLogin.close();
        }
    }
    
}
