package aplicacao;

import java.net.URL;
import java.sql.Connection;

import conexoes.FabricarConexao;
import controladores.login.LoginControlador;
import controladores.login.utilitarios.AlertaFracasso;
import controladores.login.utilitarios.AlertaInformacao;
import controladores.login.utilitarios.AlertaSucesso;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.layout.Pane;

/**
 *
 * @author Gabriel, Lívia e Marcelo
*/

public class App extends Application {

    public static Connection conexao;

    static {
        String url = "jdbc:postgresql://localhost:5432/test";
        String usuario = "postgres";
        String senha = "admin";
        conexao = new FabricarConexao(url, usuario, senha).getConexao();
    }

    @Override 
    public void stop() {
        try {
            if (App.conexao != null) {
                App.conexao.close();
                System.out.println("A aplicação foi finalizada e os recursos encerrados.");
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    @Override
    public void start(Stage palco) throws Exception {
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/login/login.fxml"));
        Parent raiz = carregar.load();
        LoginControlador controlador = carregar.getController();
        controlador.setPalco(palco);
        Scene cena = new Scene(raiz);
        App.adicionarMovimento(palco, cena);
        palco.setScene(cena);
        palco.initStyle(StageStyle.UNDECORATED);
        palco.show();
    }

    public static void adicionarMovimento(Stage stage, Scene scene) {
        final double[] eixos = new double[2];
        scene.setOnMousePressed(event -> {
            eixos[0] = event.getSceneX();
            eixos[1] = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - eixos[0]);
            stage.setY(event.getScreenY() - eixos[1]);
        });
    }

    public static void adicionaEfeitoSuave(Node node) {
        FadeTransition transicao = new FadeTransition(Duration.millis(500), node);
        transicao.setFromValue(0);
        transicao.setToValue(1);
        transicao.setOnFinished( e -> node.setVisible(true));
        transicao.play();
    }

    public static void removerEfeitoSuave(Node node) {
        FadeTransition transicao = new FadeTransition(Duration.millis(1000), node);
        transicao.setFromValue(1);
        transicao.setToValue(0);
        transicao.setOnFinished( e -> node.setVisible(false));
        transicao.play();
    }

    public static void exibirAlert(Node area, String tipo, String titulo, String descricao) {
        try {
            URL localizacao = null;

            if (tipo.equals("INFORMAÇÃO")) {
                localizacao = App.class.getResource("/telas/alertas/informacao.fxml");
            } else if (tipo.equals("FRACASSO")) {
                localizacao = App.class.getResource("/telas/alertas/fracasso.fxml");
            } else if (tipo.equals("SUCESSO")) {
                localizacao = App.class.getResource("/telas/alertas/sucesso.fxml");
            } else {
                throw new RuntimeException("Adicione o tipo de alerta - INFORMAÇÃO, FRACASSO, SUCESSO.");
            }

            FXMLLoader carregar = new FXMLLoader(localizacao);
            Node alerta = carregar.load();
            Object controlador = carregar.getController();

            if (tipo.equals("INFORMAÇÃO")) {
                ((AlertaInformacao) controlador).setTitulo(titulo);
                ((AlertaInformacao) controlador).setDescricao(descricao);
            } else if (tipo.equals("FRACASSO")) {
                ((AlertaFracasso) controlador).setTitulo(titulo);
                ((AlertaFracasso) controlador).setDescricao(descricao);
            } else {
                ((AlertaSucesso) controlador).setTitulo(titulo);
                ((AlertaSucesso) controlador).setDescricao(descricao);
            }

            Pane pane = ((Pane) area);
            pane.getChildren().clear();
            pane.getChildren().add(alerta);
            adicionaEfeitoSuave(pane);
            new Thread(() -> {

                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> removerEfeitoSuave(pane));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        } catch (Exception erro) {
            throw new RuntimeException("Não foi possível gerar o alerta.");
        }
    }

    public static Node obterTelaVazia() {
        try {
            FXMLLoader carregar = new FXMLLoader(App.class.getResource("/telas/principal/bolo/vazio.fxml"));
            Node raiz = carregar.load();
            return raiz;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public static Node obterTelaVaziaCrud() {
        try {
            FXMLLoader carregar = new FXMLLoader(App.class.getResource("/telas/vazio/vazio.fxml"));
            Node raiz = carregar.load();
            return raiz;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public static Node obterTelaCarregamento() {
        try {
            FXMLLoader carregar = new FXMLLoader(App.class.getResource("/telas/principal/carregar.fxml"));
            Node raiz = carregar.load();
            return raiz;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public static Node obterTelaErroChat() {
        try {
            FXMLLoader carregar = new FXMLLoader(App.class.getResource("/telas/chat/erro.fxml"));
            Node raiz = carregar.load();
            return raiz;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public static Object[] carregarTela(String caminho) {
        try {
            FXMLLoader carregador = new FXMLLoader(App.class.getResource("/telas" + caminho));
            Parent elemento = carregador.load();
            Object controlador = carregador.getController();
            Scene cena = new Scene(elemento);
            Stage tela = new Stage(StageStyle.UNDECORATED);
            tela.setScene(cena);
            App.adicionarMovimento(tela, cena);
            return new Object[] {tela, controlador};
        } catch (Exception erro) {
           throw new RuntimeException("Não foi possível carregar a Tela. " + caminho);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}