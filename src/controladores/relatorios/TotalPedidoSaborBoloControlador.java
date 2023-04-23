package controladores.relatorios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.consultas.ConsultaPersonalizada;
import modelos.consultas.entitidades.PedidosSaborConsulta;

public class TotalPedidoSaborBoloControlador {

    @FXML
    private TableView<PedidosSaborConsulta> areaDePedidos;

    @FXML
    private TableColumn<PedidosSaborConsulta, Long> codigo;

    @FXML
    private TableColumn<PedidosSaborConsulta, String> sabor;

    @FXML
    private TableColumn<PedidosSaborConsulta, Double> receita;

    @FXML
    private TableColumn<PedidosSaborConsulta, Long> pedidos;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void imprimir(ActionEvent event) {
        System.out.println("Clicou em imprimir");
    }

    @FXML
    public void initialize() {
       codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
       sabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
       receita.setCellValueFactory(new PropertyValueFactory<>("total"));
       pedidos.setCellValueFactory(new PropertyValueFactory<>("pedidos"));
       carregarTotalPedidoSaborBolo();

    }

    public void carregarTotalPedidoSaborBolo() {
        areaDePedidos.getItems().clear();
        ConsultaPersonalizada 
            .obterQuantidadeDePedidosPorSabor()
            .forEach( totalPedidoSaborBolo -> areaDePedidos.getItems().add(totalPedidoSaborBolo));
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
