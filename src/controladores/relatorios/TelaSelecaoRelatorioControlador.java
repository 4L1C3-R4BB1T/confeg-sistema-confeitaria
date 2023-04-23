package controladores.relatorios;

import aplicacao.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelaSelecaoRelatorioControlador {

    @FXML
    private Button botaoRelatorio;

    @FXML
    private ComboBox<String> relatorios;

    @FXML
    private HBox areaDeAlerta;

    @FXML
    private StackPane areaDeProgresso;

    @FXML
    private ProgressBar progresso;

    @FXML
    private Label porcentagem;

    private Stage tela;

    private volatile boolean threadProgressoParar = false;
  

    @FXML
    void fecharTelas(MouseEvent event) {
        encerrar();
    }

    @FXML
    void prosseguir(ActionEvent event) {
        switch(getSelecionado()) {
            case "Receita por Mês":
                carregarTelaReceitaMes();
                break;
            case "Total de Pedidos por cliente":
                carregarTelaPedidoCliente();
                break;
            case "Total de Pedidos sabor de bolo":
                carregarTelaPedidoSaborBolo();
                break;
        }
    }

    @FXML
    public void initialize() {
        
        carregarExibicaoRelatorio();

        relatorios.getSelectionModel().selectedItemProperty().addListener((obs, valorAntigo, valorNovo) -> {
                if (valorNovo != null) {
                    threadProgressoParar = true;
                    limparProgresso();
                    exibirProgresso();
                }
        });
    }

    public void exibirProgresso() {
        areaDeProgresso.setVisible(true);
        new Thread( () -> {

          while (!threadProgressoParar) {
                Platform.runLater(() -> {
                    double novoProgresso = progresso.getProgress() + 0.02;
                    double progressoNormal = Math.round(novoProgresso * 100);
                    if (progressoNormal > 100) {
                        threadProgressoParar = true;
                        return;
                    }

                    progresso.setProgress(novoProgresso);
                    if (novoProgresso >= 0.5) {
                        porcentagem.setStyle("-fx-text-fill: #fff !important;");
                    } else {
                        porcentagem.setStyle("-fx-text-fill: #020202b0 !important;");
                    }
                    porcentagem.setText(String.format("%.00f%%", progressoNormal));
                });

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
          }

          Platform.runLater(() -> {
                botaoRelatorio.setVisible(true);
          });

        }).start();
    }

    public void carregarTelaReceitaMes() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/relatorios/receitaMes.fxml"));
            Parent elemento = carregar.load();
            ReceitaMesControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            controlador.setTela(palco);
            App.adicionarMovimento(palco, cena);
            palco.show();
            encerrar();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaPedidoCliente() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/relatorios/pedidosCliente.fxml"));
            Parent elemento = carregar.load();
            TotalPedidoClienteControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            controlador.setTela(palco);
            App.adicionarMovimento(palco, cena);
            palco.show();
            encerrar();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void carregarTelaPedidoSaborBolo() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/relatorios/pedidoBolo.fxml"));
            Parent elemento = carregar.load();
            TotalPedidoSaborBoloControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            controlador.setTela(palco);
            App.adicionarMovimento(palco, cena);
            palco.show();
            encerrar();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void limparProgresso() {
        progresso.setProgress(0);
        porcentagem.setText("0%");
        porcentagem.setStyle("-fx-text-fill: #020202b0 !important;");
        botaoRelatorio.setVisible(false);
        threadProgressoParar = false;
    } 

    public void carregarExibicaoRelatorio() {
        relatorios.getItems().addAll("Receita por Mês", "Total de Pedidos por cliente", "Total de Pedidos sabor de bolo");
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public String getSelecionado() {
        return relatorios.getSelectionModel().getSelectedItem();
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }
}
