package controladores.graficos;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TotalPedidoMesControlador {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis meses;

    @FXML
    private NumberAxis pedidos;

    @FXML
    private HBox areaDeAlerta;
    
    private Stage tela;

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        
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
