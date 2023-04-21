package controladores.crudConfirmarPedido;

import java.net.URL;
import java.util.ResourceBundle;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.Cliente;
import modelos.entidades.Pedido;

public class ConfirmarPedidoControlador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Cliente> clientes;

    @FXML
    private ComboBox<Pedido> pedidos;

    @FXML
    private CheckBox pedidoPago;

    @FXML
    private DatePicker dataConfirmacao;

    @FXML
    private Label erroCliente;

    @FXML
    private Label erroPedido;

    @FXML
    private Label erroData;

    private Stage tela;
    
    private ClienteDAO clienteDAO = new ClienteDAO(App.conexao);
    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);

    private boolean sucesso = false;
    private boolean erro = false;

    @FXML
    void cancelar(ActionEvent event) {
        encerrar();
    }

    @FXML
    void fechar(MouseEvent event) {
        encerrar();
        pedidoPago.isSelected();
    }

    @FXML
    void salvar(ActionEvent event) {

    }

    @FXML
    void initialize() {
        carregarClientes();

        clientes.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                pedidos.getItems().setAll(pedidoDAO.buscarPendentesPorCliente(novo));
            }
        });

    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void carregarClientes() {
        clientes.getItems().setAll(clienteDAO.buscarTodosComPedidosPendentes());
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }
}
