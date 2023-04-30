package controladores.crudPedidos;

import aplicacao.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.PedidoBoloDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.ConfirmacaoPedido;
import modelos.entidades.Pedido;
import modelos.interfaces.AproveitarFuncao;

public class PedidoControlador {

    @FXML 
    private HBox areaDeAlerta;

    private Pedido pedido;

    private boolean clicouBotaoEditar = false;

    private PedidoBoloDAO pedidoBoloDAO = new PedidoBoloDAO(App.conexao);
    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);

    private AproveitarFuncao atualizarPedidos;

    @FXML
    public void editar(MouseEvent event) {
        if (!verificarStatus()) {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "EDITAR", "Não é possível editar pedido com esse status");
        } else if (!clicouBotaoEditar) {
            carregarEditarPedido();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "EDITAR", "A janela já está aberta.");
        }
    }

    public boolean verificarStatus() {
        if (pedido.getStatus().getDescricao().equals("CANCELADO")) {
            return false;
        } else if (pedido.getStatus().getDescricao().equals("CONCLUIDO")){
            return false;
        }
        return true;
    }

    @FXML
    public void remover(MouseEvent event) throws Exception {
        App.conexao.setAutoCommit(false);
        try {
            // remover confirmação do pedido
            ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(App.conexao);
            ConfirmacaoPedido confirmacaoPedido = confirmacaoPedidoDAO.buscarPorPedido(pedido);
            confirmacaoPedidoDAO.remover(confirmacaoPedido);

            // remover pedidobolo do pedido
            PedidoBoloDAO pedidoBoloDAO = new PedidoBoloDAO(App.conexao);
            pedidoBoloDAO.buscarPorPedido(pedido).forEach(pedidoBolo ->
                pedidoBoloDAO.remover(pedidoBolo)
            );  

            // remover pedido
            pedidoDAO.remover(pedido);

            App.conexao.commit();
            App.exibirAlert(areaDeAlerta, "SUCESSO", "REMOÇÃO", "O Pedido com ID: " + pedido.getCodigo() + " foi deletado.");
            atualizarPedidos.usar();
        } catch (Exception erro) {
            App.conexao.rollback();
            erro.printStackTrace();
        }
    }
    
    public void carregarEditarPedido() {
        try {
            clicouBotaoEditar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/pedidos/edicao/editarPedido.fxml"));
            Parent editar = carregar.load();
            EditarPedidoControlador controlador = carregar.getController();
            Scene cena = new Scene(editar);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);

            controlador.setTela(palco);
            controlador.setPedido(pedido);
            controlador.setCliente(pedido.getCliente());
            controlador.setFuncionario(pedido.getFuncionario());
            controlador.setData(pedido.getDataPedido());
            controlador.setMetodoPagamento(pedido.getMetodo());
            controlador.setObservacao(pedido.getObservacao());
            controlador.setPedidoBolo(pedidoBoloDAO.buscarPorPedido(pedido));
            App.adicionarMovimento(palco, cena);

            palco.showAndWait();

            if (controlador.getRegistrouPedido()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "EDIÇÃO", "Saiu da edição.");
            } else if (controlador.getErro()) {
                App.exibirAlert(areaDeAlerta, "ERRO", "EDITAR", "O Pedido com ID: " + pedido.getCodigo() + " não foi editado.");
            }
            clicouBotaoEditar = false;
            atualizarPedidos.usar();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setAreaDeAlerta(HBox areaDeAlerta) {
        this.areaDeAlerta = areaDeAlerta;
    }

    public void setFuncaoAtualizarPedidos(AproveitarFuncao funcao) {
        atualizarPedidos = funcao;
    }
    
}
