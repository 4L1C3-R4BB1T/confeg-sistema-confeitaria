package controladores.graficos;

import java.util.Arrays;
import java.util.Map;

import aplicacao.App;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.PedidoDAO;

public class TotalPedidoMesControlador {

    @FXML
    private ComboBox<Integer> ano;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis meses;

    @FXML
    private NumberAxis pedidos;

    @FXML
    private HBox areaDeAlerta;

    @FXML 
    private ImageView carregamento;
    
    private Stage tela;

    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);

    private String[] arrayMeses = {
        "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"
    };

    private ObservableList<String> observableListMeses = FXCollections.observableArrayList(Arrays.asList(arrayMeses));
    private ObservableList<Integer> observableListAnos = FXCollections.observableArrayList();

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        carregarComboBox();
        meses.setCategories(observableListMeses);      
        pedidos.setTickUnit(1); 
        pedidos.setAutoRanging(false);    
        pedidos.setUpperBound(5);  
    }

    public void carregarComboBox() {
        observableListAnos = FXCollections.observableArrayList(pedidoDAO.obterAnosQuePossuemConfirmacoes());
        ano.setItems(observableListAnos);
        ano.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> exibirCarregamento(newValue));
    }

    public void carregarGrafico(int ano) {
        barChart.getData().clear();
        
        Map<Integer, Integer> dados = pedidoDAO.obterQuantidadeDePedidosPorMesAno(ano);
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(String.valueOf(ano));
        
        int maxValue = 0;

        for(Map.Entry<Integer, Integer> item : dados.entrySet()){
            String mes = arrayMeses[((int) item.getKey()) - 1];
            Integer pedidos = item.getValue();
            series.getData().add(new XYChart.Data<>(mes, pedidos));

            if (pedidos > maxValue) maxValue = pedidos;
        }
        
        barChart.getData().add(series);

        // personalizar intervalo do eixo y
        pedidos.setUpperBound(maxValue + 2);
    }

    public void exibirCarregamento(int ano) {
       barChart.setVisible(false);
        new Thread(() -> {
            try {
                Thread.sleep(200);
                Platform.runLater(() -> {
                    carregamento.setVisible(true);
                    carregarGrafico(ano);
                });
                Thread.sleep(400);
                Platform.runLater(() -> {
                    carregamento.setVisible(false);
                    App.adicionaEfeitoSuave(barChart);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

}
