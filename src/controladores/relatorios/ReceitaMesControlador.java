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
import modelos.consultas.entitidades.ReceitaMesConsulta;

public class ReceitaMesControlador {

    @FXML
    private TableView<ReceitaMesConsulta> areaDePedidos;

    @FXML
    private TableColumn<ReceitaMesConsulta, Integer> ano;

    @FXML
    private TableColumn<ReceitaMesConsulta, Integer> mes;

    @FXML
    private TableColumn<ReceitaMesConsulta, Long> pedidos;

    @FXML
    private TableColumn<ReceitaMesConsulta, Double> receita;

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
        ano.setCellValueFactory(new PropertyValueFactory<>("ano"));
        mes.setCellValueFactory(new PropertyValueFactory<>("mes"));
        pedidos.setCellValueFactory(new PropertyValueFactory<>("pedidos"));
        receita.setCellValueFactory(new PropertyValueFactory<>("pedidos"));
        carregarReceitaPorMes();
    }

    public void carregarReceitaPorMes() {
        areaDePedidos.getItems().clear();
        ConsultaPersonalizada
            .obterReceitaMesConsultas()
            .forEach( receitaMes -> areaDePedidos.getItems().add(receitaMes));
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
