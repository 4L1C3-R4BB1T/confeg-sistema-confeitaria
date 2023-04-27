package controladores.graficos;

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

public class TelaSelecaoGraficoControlador {

    @FXML
    private Button botaoGrafico;

    @FXML
    private ComboBox<String> graficos;

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
            case "Bolo mais Pedidos":
                carregarTelaBoloMaisPedido();
                break;
            case "Métodos de Pagamento mais Utilizados":
                carregarTelaMetodoPagamentoMaisUtilizado();
                break;
            case "Total Pedidos por Mês":
                carregarTelaTotalPedidoMes();
                break;
        }
    }

    @FXML
    public void initialize() {
        
        carregarExibicaoRelatorio();

        graficos.getSelectionModel().selectedItemProperty().addListener((obs, valorAntigo, valorNovo) -> {
                if (valorNovo != null) {
                    threadProgressoParar = true;
                    limparProgresso();
                    exibirProgresso();
                }
        });
    }

    public void carregarTelaMetodoPagamentoMaisUtilizado() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/graficos/metodosPagamento.fxml"));
            Parent elemento = carregar.load();
            MetodoPagamentoUtilizadoControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.show();
            encerrar();
        } catch (Exception erro) { 
            erro.printStackTrace();
        }
    }

    public void carregarTelaBoloMaisPedido() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/graficos/bolos.fxml"));
            Parent elemento = carregar.load();
            BoloMaisPedidoControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.show();
            encerrar();
        } catch (Exception erro) { 
            erro.printStackTrace();
        }
    }

    public void carregarTelaTotalPedidoMes() {
        try {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/graficos/pedidosMes.fxml"));
            Parent elemento = carregar.load();
            TotalPedidoMesControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            App.adicionarMovimento(palco, cena);
            controlador.setTela(palco);
            palco.show();
            encerrar();
        } catch (Exception erro) { 
            erro.printStackTrace();
        }
    }

    public void exibirProgresso() {
        areaDeProgresso.setVisible(true);
        new Thread( () -> {

          while (!threadProgressoParar) {
                Platform.runLater(() -> {
                    double novoProgresso = progresso.getProgress() + 0.05;
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
                botaoGrafico.setVisible(true);
          });

        }).start();
    }


    public void limparProgresso() {
        progresso.setProgress(0);
        porcentagem.setText("0%");
        porcentagem.setStyle("-fx-text-fill: #020202b0 !important;");
        botaoGrafico.setVisible(false);
        threadProgressoParar = false;
    } 

    public void carregarExibicaoRelatorio() {
        graficos.getItems().addAll("Bolo mais Pedidos", "Métodos de Pagamento mais Utilizados", "Total Pedidos por Mês");
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public String getSelecionado() {
        return graficos.getSelectionModel().getSelectedItem();
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }
}
