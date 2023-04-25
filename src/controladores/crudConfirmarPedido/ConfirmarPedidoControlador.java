package controladores.crudConfirmarPedido;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;

import aplicacao.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.FuncionarioDAO;
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
    
    @FXML 
    private TextField nomeFuncionario;

    private Stage tela;
    
    private ClienteDAO clienteDAO = new ClienteDAO(App.conexao);
    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);
    private ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(App.conexao);
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);

    private ValidaFormulario vf = new ValidaFormulario();

    private boolean sucesso = false;
    private boolean erro = false;
    private boolean desconto = false;
    private volatile boolean threadValidarCampoAtivo = true;
    private volatile boolean threadPodeValidarCampos = false;

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
            if (!validarStatus()) return;
            
            App.conexao.setAutoCommit(false);
            try {
                Pedido pedido = getPedido();
                ConfirmacaoPedido confirmacaoPedido = new ConfirmacaoPedido(getCliente(), pedido, Date.valueOf(getDataConfirmacao()), null, getDescricao());

                if (getPago() && getDataConfirmacao().equals(pedido.getDataPedido().toLocalDate())) {
                    App.exibirAlert(areaDeAlerta, "SUCESSO", "DESCONTO", "Desconto de 20% aplicado.");
                    pedido.setDesconto(20D);
                    pedido.setStatus(Status.CONCLUIDO);
                    this.desconto = true;
                } else if (getPago()) {
                    pedido.setStatus(Status.CONCLUIDO);
                } else {
                    pedido.setStatus(Status.CANCELADO);
                }

                pedidoDAO.alterarConfirmaco(pedido);
                confirmacaoPedido.setPago(getPago());
                confirmacaoPedidoDAO.inserir(confirmacaoPedido);
                App.conexao.commit();
                this.sucesso = true;
                encerrar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                this.erro = true;
                encerrar();
            }
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Preencha os campos necessários.");
        }

        threadPodeValidarCampos = true;
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
        clientes.getSelectionModel().selectedItemProperty().addListener((obs, antigo, cliente) -> {
            pedidos.setValue(null);
            if (cliente != null) {
                pedidos.getItems().setAll(pedidoDAO.buscarPendentesPorCliente(cliente));
            }
        });

        pedidos.getSelectionModel().selectedItemProperty().addListener((obs, antigo, pedido) -> {
            if (pedido != null) {
                nomeFuncionario.setText(funcionarioDAO.buscarPorPedido(pedido).getNome());
            }
        });

        new Thread(() -> {

            while (threadValidarCampoAtivo) {

                if (threadPodeValidarCampos) {
                    try {

                        Platform.runLater(() -> validarCampos());
                        Thread.sleep(300);
                    } catch (Exception erro) {
                        erro.printStackTrace();
                    }
                }
                
            }

        }).start();
    }

    public void limparCheckbox() {
        pedidoPago.setSelected(false);
        pedidoCancelado.setSelected(false);
    }

    public boolean validarCampos()  {
        boolean estaOk = Stream.of(
            vf.validarComboBox(erroCliente, getCliente(), "Selecione o Cliente"),
            vf.validarComboBox(erroPedido, getPedido(), "Selecione o Pedido")
        ).noneMatch(campo -> campo == false);

        return estaOk && validarDatas();
    }

    public boolean validarStatus() {
        if (!(getPago() || getCancelado())) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "INFORMAÇÃO", "Preencha o status: Pago ou Cancelado");
            return false;
        }
        return true;
    }

    public boolean validarDatas() {
        erroData.setStyle("-fx-text-fill: red");
        erroData.setText("");
        if (getDataConfirmacao() == null){
            erroData.setText("Preencha a Data de Confirmação");
            return false;
        } else if (getDataConfirmacao().isBefore(LocalDate.now())) {
            erroData.setText("Data abaixo da data de hoje");
            return false;
        } else {
            return true;
        }
    }

    public void encerrar() {
        threadValidarCampoAtivo = false;
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

    public boolean getSucesso() {
        return sucesso;
    }

    public boolean getErro() {
        return erro;
    }

    public boolean getDesconto() {
        return desconto;
    }
}
