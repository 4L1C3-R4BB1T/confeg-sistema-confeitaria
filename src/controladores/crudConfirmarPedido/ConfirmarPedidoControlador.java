package controladores.crudConfirmarPedido;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.Cliente;
import modelos.entidades.ConfirmacaoPedido;
import modelos.entidades.Pedido;
import modelos.entidades.enums.Status;
import modelos.validacao.ValidaFormulario;

public class ConfirmarPedidoControlador {


    @FXML
    private ComboBox<Cliente> clientes;

    @FXML
    private ComboBox<Pedido> pedidos;

    @FXML
    private CheckBox pedidoPago;
    
    @FXML
    private CheckBox pedidoCancelado;

    @FXML
    private DatePicker dataConfirmacao;

    @FXML
    private Label erroCliente;

    @FXML
    private Label erroPedido;

    @FXML
    private Label erroData;
    
    @FXML
    private HBox areaDeAlerta;

    @FXML
    private TextArea observacao;

    private Stage tela;
    
    private ClienteDAO clienteDAO = new ClienteDAO(App.conexao);
    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);
    private ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(App.conexao);

    private ValidaFormulario vf = new ValidaFormulario();

    // private boolean sucesso = false;
    // private boolean erro = false;

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
    void salvar(ActionEvent event) throws Exception {

        //Ao ser confirmada a entrega, o pedido deverá ter seu status alterado para CONCLUIDO
       // Caso a data da confirmação do pedido seja mesma do pedido, 
       // o cliente ganhará 2% de desconto sobre o valor total do pedido
        if (validarCampos()) {
            App.conexao.setAutoCommit(false);
            try {
                
                Pedido pedido = getPedido();
                ConfirmacaoPedido confirmacaoPedido = new ConfirmacaoPedido(getCliente(), pedido, Date.valueOf(getDataConfirmacao()), null, getDescricao());

                if (getPago() && getDataConfirmacao().equals(pedido.getDataPedido().toLocalDate())) {
                    App.exibirAlert(areaDeAlerta, "SUCESSO", "DESCONTO", "Desconto de 2% aplicado.");
                    pedido.setDesconto(2D);
                    pedido.setStatus(Status.CONCLUIDO);
                } else if (getPago()) {
                    pedido.setStatus(Status.CONCLUIDO);
                } else {
                    pedido.setStatus(Status.CANCELADO);
                }

                pedidoDAO.alterar(pedido);
                confirmacaoPedido.setPago(getPago());
                confirmacaoPedidoDAO.inserir(confirmacaoPedido);
                App.conexao.commit();
                this.sucesso = true;
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                this.erro = true;
            }
        } 
    }

    @FXML
    public void clicouPago(ActionEvent event) {
        limparCheckbox();
        pedidoPago.setSelected(true);
    }

    @FXML
    public void clicouCancelado(ActionEvent event) {
        limparCheckbox();
        pedidoCancelado.setSelected(true);
    }

    @FXML
    void initialize() {
        carregarClientes();
        clientes.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            pedidos.setValue(null);
            if (novo != null) {
                pedidos.getItems().setAll(pedidoDAO.buscarPendentesPorCliente(novo));
            }
        });
    }

    public void limparCheckbox() {
        pedidoPago.setSelected(false);
        pedidoCancelado.setSelected(false);
    }

    public boolean validarCampos() throws Exception {
        boolean estaoOk = Stream.of(
            vf.validarComboBox(erroCliente, getClass(), "Selecione o Cliente"),
            vf.validarComboBox(erroPedido, getPedido(), "Selecione o Pedido"),
            vf.validarComboBox(erroData, getDataConfirmacao(), "Selecione o Data")
        ).noneMatch(campo -> campo == false);

        if (estaoOk)  {
            if((getPago() || getCancelado())) {
                return true;
            } 
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "STATUS", "Selecione o Status");
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Preencha os campos necessários.");
        }

        return false;

    }

    public void aplicarDesconto() {
        Pedido pedido = getPedido();
     
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

    public Cliente getCliente() {
        return clientes.getSelectionModel().getSelectedItem();
    }

    public Pedido getPedido() {
        return pedidos.getSelectionModel().getSelectedItem();
    }

    public LocalDate getDataConfirmacao() {
        return dataConfirmacao.getValue();
    }

    public boolean getPago() {
        return pedidoPago.isSelected();
    }

    public boolean getCancelado() {
        return pedidoCancelado.isSelected();
    }

    public String getDescricao() {
        return observacao.getText();
    }
}
