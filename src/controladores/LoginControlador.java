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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.dao.DAO;
import modelos.entidades.Usuario;

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




    @FXML
    public void entrar(ActionEvent event) throws Exception {
        if (podeEntrar()) {
            System.out.println("sasas");
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
        if (!campoUsuario.getText().isEmpty() && !campoSenha.getText().isEmpty()) {
            DAO dao = new DAO();
            String consulta = String.format("SELECT * FROM usuarios u WHERE u.email = '%s' AND u.senha = '%s'", campoUsuario.getText(), campoSenha.getText());
            Usuario usuario = dao.consultar(Usuario.class, consulta);
            if (usuario != null) {
               return true;
            }
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
            if (palcoLogin != null) {
                palcoLogin.close();
            }
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
            while (true) {
                try {
                    Platform.runLater(() -> {
                        areaDeFoto.setStyle(String.format("-fx-background-image: url('%s') !important;", fotos[fotoAtual++ % fotos.length]));
                    });
                    Thread.sleep(5000);

                } catch (Exception erro) {}
            }
        }).start();
        
    }

}
