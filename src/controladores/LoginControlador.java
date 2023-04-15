package controladores;

import aplicacao.App;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// TELA DE LOGIN
public class LoginControlador {

    @FXML
    private TextField campoUsuario;

    @FXML
    private TextField campoSenha;

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

    @FXML 
    public void cadastrar(MouseEvent event) throws Exception {
        if (clicouBotaoCadastrar) {
            return; /// Não pode clicar no botão cadastrar e exibir a tela várias vezes, impede isso.
        }
        clicouBotaoCadastrar = true;
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/cadastro/cadastro.fxml"));
        Parent raiz = carregar.load();
        Scene cena = new Scene(raiz);
        Stage palco = new Stage();
        palcoCadastro = palco;
        palco.setScene(cena);
        App.adicionarMovimento(palco, cena);
        palco.initStyle(StageStyle.UNDECORATED);
        palco.showAndWait();
        clicouBotaoCadastrar = false;
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
        }
    }

    @FXML
    public void clicouTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (podeEntrar()) {
                carregarTelaPrincipal();
            }
        }
    }

    public boolean podeEntrar() {
        if (campoUsuario.getText().equals("test") && campoSenha.getText().equals("test")) {
            return true;
        }
        return false;
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

}
