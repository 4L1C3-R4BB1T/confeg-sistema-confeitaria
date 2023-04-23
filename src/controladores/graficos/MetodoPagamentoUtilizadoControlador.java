package controladores.graficos;

import aplicacao.App;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.PedidoDAO;

public class MetodoPagamentoUtilizadoControlador {

    @FXML
    private PieChart pieChart;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;

    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        pieChart.setStyle("-fx-pie-label-visible: true;");
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setLegendVisible(false);
        carregar();
    }

    public void carregar() {
        pieChart.getData().clear();
        pedidoDAO
            .obterQuantidadeDePedidosPorPagamento()
            .forEach((metodo, pedido) -> {
                pieChart.getData().add(new PieChart.Data(metodo, pedido));
            });

        pieChart.setLabelLineLength(20);

        for (final PieChart.Data data : pieChart.getData()) {
            String porcentagem = String.format("%.0f%%", (data.getPieValue() / pieChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum()) * 100);
            data.setName(data.getName() + " " + porcentagem);
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
