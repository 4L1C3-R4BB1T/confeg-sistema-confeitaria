package controladores.crudPedidoConfirmado;


import java.sql.Date;
import java.time.LocalDate;


import aplicacao.App;
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
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.Cliente;
import modelos.entidades.ConfirmacaoPedido;
import modelos.entidades.Pedido;
import modelos.entidades.enums.Status;

public class EditarPedidoConfirmadoControlador {


    @FXML
    private ComboBox<Cliente> clientes;

    @FXML
    private ComboBox<Pedido> pedidos;

    @FXML
    private CheckBox pedidoPago;

    @FXML
    private TextArea observacao;

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
    private CheckBox pedidoCancelado;

    @FXML
    private TextField nomeFuncionario;

    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);
    private ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(App.conexao);

    private ConfirmacaoPedido confirmacaoPedido;

    private Stage tela;

    private LocalDate dataLimite;

    private boolean sucesso = false;
    private boolean fracasso = false;

    @FXML
    public void cancelar(ActionEvent event) {
        encerrar();
    }

    @FXML
    public void clicouCancelado(ActionEvent event) {
        limpar();
        pedidoCancelado.setSelected(true);
    }

    @FXML
    public void clicouPago(ActionEvent event) {
        limpar();
        pedidoPago.setSelected(true);
    }

    @FXML
    public void fechar(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void salvar(ActionEvent event) throws Exception {
        if (podeSalvar()) {
            App.conexao.setAutoCommit(false);
            try {
                confirmacaoPedido.setDataConfirmacao(Date.valueOf(getData()));
                confirmacaoPedido.setPago(getPago());
                confirmacaoPedido.setObservacao(getObservacao());
  
                Pedido pedido = confirmacaoPedido.getPedido();
                pedido.setDesconto(0D);
                Date dataPedido = pedido.getDataPedido();

                // Se tiver pago e a data for igual a data do pedido 2% aplicado
                if (getPago() && Date.valueOf(getData()).equals(dataPedido)) {
                    pedido.setDesconto(20D);
                } else if (getPago()) {
                    pedido.setStatus(Status.CONCLUIDO);
                } else {
                    pedido.setStatus(Status.CANCELADO);
                }

                pedidoDAO.alterarConfirmaco(pedido);
                confirmacaoPedidoDAO.alterar(confirmacaoPedido);
                App.conexao.commit();
                sucesso = true;
                encerrar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                fracasso = true;
            }
        }
    }

    @FXML
    public void initialize() {
      

    }

    public boolean podeSalvar() {
        if (!validarData()) {
            return false;
        } else if (!validarPagamento()) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "STATUS", "Selecione o status: Pago ou Cancelado.");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarData() {
        if (getData() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "DATA DE CONFIRMAÇÃO", "Preencha a Data de Confirmação");
            return false;
        } else if (getData().isBefore(dataLimite)) {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "DATA DE CONFIRMAÇÃO", "Data abaixo da data do pedido");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarPagamento() {
        return (getPago() || getCancelado());
    }

    public void limpar() {
        pedidoPago.setSelected(false);
        pedidoCancelado.setSelected(false);
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public void setPedido(ConfirmacaoPedido pc) {
        this.confirmacaoPedido = pc;
        clientes.setValue(pc.getCliente());
        pedidos.setValue(pc.getPedido());
        dataConfirmacao.setValue(pc.getDataConfirmacao().toLocalDate());
        dataLimite = pc.getDataConfirmacao().toLocalDate();
        if (pc.getPago()) {
            pedidoPago.setSelected(true);
        } else {
            pedidoCancelado.setSelected(true);
        }
        observacao.setText(pc.getObservacao());
        nomeFuncionario.setText(pc.getPedido().getFuncionario().getNome());
    }

    public LocalDate getData() {
        return dataConfirmacao.getValue();
    }

    public boolean getPago() {
        return pedidoPago.isSelected();
    }

    public boolean getCancelado() {
        return pedidoCancelado.isSelected();
    }

    public String getObservacao() {
        return observacao.getText();
    }

    public boolean getSucesso() {
        return sucesso;
    }

    public boolean getFracasso() {
        return fracasso;
    }
    
}
