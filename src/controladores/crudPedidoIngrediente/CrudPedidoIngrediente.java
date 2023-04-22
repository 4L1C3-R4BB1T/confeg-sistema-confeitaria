package controladores.crudPedidoIngrediente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.consultas.ConsultaPersonalizada;
import modelos.consultas.entitidades.PedidoIngrediente;

public class CrudPedidoIngrediente {

    @FXML
    private TableView<PedidoIngrediente> areaDePedidos;

    @FXML
    private TableColumn<PedidoIngrediente, Long> codigo;

    @FXML
    private TableColumn<PedidoIngrediente, String> funcionario;

    @FXML
    private TableColumn<PedidoIngrediente, String> dataPedido;

    @FXML
    private TableColumn<PedidoIngrediente, Label> status;

    @FXML
    private TableColumn<PedidoIngrediente, HBox> areaBotao;

    @FXML
    private HBox areaDeAlerta;

    private Stage tela;

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }


    public void initialize() {
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        funcionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataPedido.setCellValueFactory(new PropertyValueFactory<>("data"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        areaBotao.setCellValueFactory(new PropertyValueFactory<>("botoes"));
        carregarPedidoIngredientes();
    }

    public void carregarPedidoIngredientes() {
        areaDePedidos.getItems().clear();
        ConsultaPersonalizada
            .obterPedidosDeIngrediente()
            .forEach( pedido -> {
                   try {

                        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/ingredientes/botoes.fxml"));
                        Node elemento = carregar.load();
                        BotoesPedidoIngredienteControlador controlador = carregar.getController();
                        HBox botoes = new HBox();
                        botoes.getChildren().add(elemento);
                        pedido.setBotoes(botoes);
                        controlador.setConteudo(pedido);
                        controlador.setAreaDeAlerta(areaDeAlerta);
                        controlador.setAtualizarAreaConteudo(this::carregarPedidoIngredientes);
                        areaDePedidos.getItems().add(pedido);
                   } catch (Exception erro) {
                        erro.printStackTrace();
                   }
            });
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
