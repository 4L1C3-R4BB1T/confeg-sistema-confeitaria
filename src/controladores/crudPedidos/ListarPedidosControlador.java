package controladores.crudPedidos;

import aplicacao.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.consultas.ConsultaPersonalizada;
import modelos.consultas.entitidades.PedidoConsulta;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.Pedido;


// Implementar depois

public class ListarPedidosControlador {

    @FXML
    private TableView<PedidoConsulta> areaDePedidos;

    @FXML
    private TableColumn<PedidoConsulta, Long> codigo;

    @FXML
    private TableColumn<PedidoConsulta, String> cliente;

    @FXML
    private TableColumn<PedidoConsulta, Double> total;

    @FXML
    private TableColumn<PedidoConsulta, String> data;

    @FXML
    private TableColumn<PedidoConsulta, String> status;

    @FXML
    private TableColumn<PedidoConsulta, Node> areaBotao;

    private Stage tela;

    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);


    @FXML
    public void fechar(MouseEvent event) {
        fecharTelas();
    }

    @FXML
    public void initialize() {
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        cliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        data.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        areaBotao.setCellValueFactory(new PropertyValueFactory<>("node"));

        carregarPedidos();
    }

    public void carregarPedidos() {
        areaDePedidos.getItems().clear();
        ConsultaPersonalizada.obterPedidoConsultas().forEach( pedidoConsulta -> {
            try {
                FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/pedidos/botoes.fxml"));
                Node botoes = carregar.load();
                PedidoControlador controlador = carregar.getController();
                Pedido pedido = pedidoDAO.buscarPorCodigo(pedidoConsulta.getCodigo());
                controlador.setPedido(pedido);
                pedidoConsulta.setNode(botoes);
                areaDePedidos.getItems().add(pedidoConsulta);
            } catch (Exception erro) {
                erro.printStackTrace();
            }

        });
    }

    public void fecharTelas() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

}
