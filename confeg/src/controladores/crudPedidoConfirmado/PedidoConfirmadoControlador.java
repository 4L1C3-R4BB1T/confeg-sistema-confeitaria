package controladores.crudPedidoConfirmado;

import aplicacao.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.consultas.entitidades.PedidoConfirmado;
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.PedidoBoloDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.ConfirmacaoPedido;
import modelos.entidades.Pedido;
import modelos.interfaces.AproveitarFuncao;

public class PedidoConfirmadoControlador {

    private Node areaDeAlerta;
    private PedidoDAO pedidoDAO = new PedidoDAO(App.conexao);
    private ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(App.conexao);
    private ConfirmacaoPedido confirmacaoPedido;

    private boolean clicouBotaoEditar = false;

    private AproveitarFuncao atualizarPedidosConfirmados;

    @FXML
    public void editar(MouseEvent event) {
        if(!clicouBotaoEditar) {
            carregarTelaEditar();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "EDIÇÃO", "Edição já aberta para Confirmação de Pedido.");
        }
    }

    @FXML
    public void remover(MouseEvent event) throws Exception {
        if (confirmacaoPedido != null) {
            App.conexao.setAutoCommit(false);
            try {
                Pedido pedido = confirmacaoPedido.getPedido();
                
                // remover confirmação de pedido
                confirmacaoPedidoDAO.remover(confirmacaoPedido);

                // remover pedidobolo do pedido
                PedidoBoloDAO pedidoBoloDAO = new PedidoBoloDAO(App.conexao);
                pedidoBoloDAO.buscarPorPedido(pedido).forEach(pedidoBolo ->
                    pedidoBoloDAO.remover(pedidoBolo)
                );  

                // remover pedido
                pedidoDAO.remover(pedido);

                App.conexao.commit();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "DELEÇÃO", "A Confirmação de Pedido com ID: " + confirmacaoPedido.getCodigo() + " foi removida.");
                atualizarPedidosConfirmados.usar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                App.exibirAlert(areaDeAlerta, "FRACASSO", "DELEÇÃO", "Não foi possível remover.");
            }
        } else {
            App.exibirAlert(areaDeAlerta, "FRACASSO", "INTERNO", "Confirmação de Pedido não selecionada.");
        }
    }

    @FXML
    public void initialize() {}

    public void carregarTelaEditar() {
        try {
            clicouBotaoEditar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/confirmacao/edicao/editarConfirmacao.fxml"));
            Parent elemento = carregar.load();
            EditarPedidoConfirmadoControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            App.adicionarMovimento(palco, cena);
            palco.setScene(cena);
            controlador.setTela(palco);
            controlador.setPedido(confirmacaoPedido);
            palco.showAndWait();
            if (controlador.getSucesso()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "EDIÇÃO", "Confirmação de Pedido alterada com sucesso.");
            } else if (controlador.getFracasso()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "EDIÇÃO", "Não foi possível realizar a edição.");
            }
            clicouBotaoEditar = false;
            atualizarPedidosConfirmados.usar();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void setAreaDeAlerta(Node areaDeAlerta) {
        this.areaDeAlerta = areaDeAlerta;
    }

    public void setPedidoConfirmado(PedidoConfirmado pc) {
        this.confirmacaoPedido = confirmacaoPedidoDAO.buscarPorCodigo(pc.getCodigo());
    }

    public void setAtualizarPedidosConfirmados(AproveitarFuncao atualizarPedidosConfirmados) {
        this.atualizarPedidosConfirmados = atualizarPedidosConfirmados;
    }
    
}
 