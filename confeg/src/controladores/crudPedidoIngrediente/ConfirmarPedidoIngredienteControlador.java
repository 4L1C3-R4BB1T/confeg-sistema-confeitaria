package controladores.crudPedidoIngrediente;

import aplicacao.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelos.entidadeDAO.PedidoCompraDAO;
import modelos.entidades.PedidoCompra;
import modelos.entidades.enums.Status;

public class ConfirmarPedidoIngredienteControlador {

    @FXML
    private ComboBox<PedidoCompra> pedidos;

    @FXML
    private HBox areaDeAlerta;

    @FXML
    private CheckBox clicouConcluido;

    @FXML
    private CheckBox clicouCancelado;

    private Stage tela;

    private PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(App.conexao);

    private boolean sucesso = false;
    private boolean fracasso = false;

    @FXML
    public void cancelar(ActionEvent event) {
        encerrar();
    }

    @FXML
    public void confirmar(ActionEvent event) throws Exception {
        if (podeSalvar()) {
            App.conexao.setAutoCommit(false);
            try {
                PedidoCompra pedidoCompra = getPedidoCompra();
                pedidoCompra.setStatus(Status.CANCELADO);

                if (getConcluido()) {
                    pedidoCompra.setStatus(Status.CONCLUIDO);
                } 

                pedidoCompraDAO.alterarStatus(pedidoCompra);
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
    public void fecharTelas(MouseEvent event) {
        encerrar();
    }

    @FXML
    public void initialize() {
        carregarPedidos();

        clicouCancelado.setOnAction( event -> {
            limparConfirmacao();
            clicouCancelado.setSelected(true);
        });

        clicouConcluido.setOnAction( event -> { 
            limparConfirmacao();
            clicouConcluido.setSelected(true);
        });
    }

    public boolean podeSalvar() {
        if (getPedidoCompra() == null) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "CAMPO", "Selecione o Pedido.");
            return false;
        } else if (!(getCancelado() || getConcluido())) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "STATUS", "Marque o status: Concluido ou Cancelado.");
            return false;
        } else {
            return true;
        }
    }

    public void limparConfirmacao() {
        clicouConcluido.setSelected(false);
        clicouCancelado.setSelected(false);
    }

    public void carregarPedidos() {
        pedidos.getItems().setAll(pedidoCompraDAO.buscarPendentes());
    }

    public PedidoCompra getPedidoCompra() {
        return pedidos.getSelectionModel().getSelectedItem();
    }

    public boolean getConcluido() {
        return clicouConcluido.isSelected();
    }

    public boolean getCancelado() {
        return clicouCancelado.isSelected();
    }

    public void encerrar() {
        if (tela != null) {
            tela.close();
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public boolean getSucesso() {
        return sucesso;
    }
    
    public boolean getFracasso() {
        return fracasso;
    }
    
}
