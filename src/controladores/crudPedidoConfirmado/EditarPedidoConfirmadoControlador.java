package controladores.crudPedidoConfirmado;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

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

public class EditarPedidoConfirmadoControlador {

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

    private ConfirmacaoPedido pedido;

    private Stage tela;

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
                pedido.setDataConfirmacao(Date.valueOf(getData()));
                pedido.setPago(getPago());
                pedido.setObservacao(getObservacao());
                confirmacaoPedidoDAO.alterar(pedido);
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
        } else if (getData().isBefore(LocalDate.now())) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "DATA DE CONFIRMAÇÃO", "Data abaixo da data de hoje.");
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
        this.pedido = pc;
        clientes.setValue(pc.getCliente());
        pedidos.setValue(pc.getPedido());
        dataConfirmacao.setValue(pc.getDataConfirmacao().toLocalDate());
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
