package controladores.graficos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import aplicacao.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.PedidoDAO;

public class TotalPedidoMesControlador {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis meses;

    @FXML
    private NumberAxis pedidos;

    @FXML
    private HBox areaDeAlerta;
    
    private Stage tela;

    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);

    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        carregar();
    }

    public void carregar() {
        barChart.getData().clear();
        
        String[] arrayMeses = {
            "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", 
            "Jul", "Ago", "Set", "Out", "Nov", "Dez"
        };
        
        observableListMeses.addAll(Arrays.asList(arrayMeses));
        meses.setCategories(observableListMeses);
        
        Map<Integer, ArrayList<Integer>> dados = pedidoDAO.obterQuantidadeDePedidosPorMesAno();
        
        for (Map.Entry<Integer, ArrayList<Integer>> item : dados.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(item.getKey().toString());
            for (int i = 0; i < item.getValue().size(); i = i + 2) {
                String mes = arrayMeses[((int) item.getValue().get(i)) - 1];
                Integer quantidade = (Integer) item.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            barChart.getData().add(series);
        }
       
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
