package controladores.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import aplicacao.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelos.entidades.Funcionario;
import servidor.entidades.Conectados;
import servidor.entidades.Mensagem;

public class ChatControlador {

    @FXML
    private TextField textFieldComentario;

    @FXML
    private VBox areaComentario;

    @FXML 
    private FlowPane areaConectados;

    @FXML 
    private Label quantidadeConexao;

    @FXML 
    private ScrollPane scrollPaneComentario;

    private volatile Socket soquete;

    private Funcionario conectado;

    private Stage tela;

    private volatile boolean conexaoAberta = false;
    private volatile boolean conectou = false;

    @FXML 
    public void apertouEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!getComentario().isEmpty()) {
                enviarObjeto(getComentario());
                setComentario("");
            }
        }
    }

    @FXML
    public void botaoEnviar(MouseEvent event) {
        if (!getComentario().isEmpty()) {
            enviarObjeto(getComentario());
            setComentario("");
        }
    }

    @FXML 
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML 
    public void minimizar(MouseEvent event) {
        if (tela != null) {
            tela.setIconified(true);
        }
    }

    public void aguardando() {
        conexaoAberta = false;
        areaComentario.getChildren().clear();
        areaComentario.getChildren().add(App.obterTelaErroChat());
        areaConectados.getChildren().clear();
        new Thread(() -> {
            while(!conectou) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException erro) {
                    erro.printStackTrace();
                }
            }
            Platform.runLater(() -> areaComentario.getChildren().clear());
        }).start();
    }

    public void tentandoConectar() {
        new Thread(() -> {
            while (!conectou) {
                try {
                    Thread.yield();
                    iniciarConexao();
                    Thread.sleep(1000);
                } catch (InterruptedException erro) {
                    erro.printStackTrace();
                }
            }
            enviarObjeto(conectado);
            conexaoAberta = true;
            new Thread(this::obterDados).start();
       }).start();
    }

    public void iniciarConexao() {
        try {
            soquete = new Socket("localhost", 3000);
            conectou = true;
        } catch (Exception erro) {}
    }

    public void enviarObjeto(Object obj) {
        try {
            ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
            saida.writeObject(obj);
        } catch (Exception erro) {
            conectou = false;
            aguardando();
            tentandoConectar();
        }
    }

    public void encerrar() {
        if (tela != null) {
            conexaoAberta = false;
            try {
                if (soquete != null) {
                    soquete.close();
                }
            } catch (IOException erro) {
                erro.printStackTrace();
            }
            tela.close();
        }
    }
 
    public String getComentario() {
        return textFieldComentario.getText();
    }

    public void setComentario(String comentario) {
        textFieldComentario.setText(comentario);
    }

    public Funcionario getConectado() {
        return conectado;
    }

    public void setConectado(Funcionario conectado) {
        this.conectado = conectado;
        aguardando();
        tentandoConectar();
    }

    public void adicionarComentarioNaTela(Funcionario funcionario, String comentario) {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/chat/comentario.fxml"));
            Parent conteudo = carregar.load();
            ComentarioControlador controlador = carregar.getController();
            controlador.setNome(funcionario.getNome());
            controlador.setMensagem(comentario);
            areaComentario.getChildren().add(conteudo);
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void adicionarConectadoNaTela(Funcionario funcionario) {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/chat/conectado.fxml"));
            Parent conteudo = carregar.load();
            ConectadoControlador controlador = carregar.getController();
            controlador.setNome(funcionario.getNome());
            controlador.setTipo(funcionario.getTipo().getDescricao());
            areaConectados.getChildren().add(conteudo);
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void obterDados() {
        try {
            while (conexaoAberta) {
                ObjectInputStream entrada = new ObjectInputStream(soquete.getInputStream());
                Object obj = entrada.readObject();
    
                if (obj instanceof Mensagem) {
                    Mensagem mensagem = (Mensagem) obj;
                    Platform.runLater(() -> adicionarComentarioNaTela(mensagem.getFuncionario(), mensagem.getConteudo()));
                }
                
                if (obj instanceof Conectados) {
                    Conectados conexao = (Conectados) obj;
                    Platform.runLater(() -> {
                        quantidadeConexao.setText(String.valueOf(conexao.getConectados().size()));
                        areaConectados.getChildren().clear();
                        conexao.getConectados().forEach( c -> adicionarConectadoNaTela(c));
                    });
                }
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

}
