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
import modelos.entidadeDAO.PedidoBoloDAO;
import modelos.entidadeDAO.PedidoDAO;
import modelos.entidades.Pedido;
import modelos.entidades.PedidoBolo;
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
        if (!clicouBotaoEditar) {
            carregarEditarPedido();
        }
    }

    @FXML
    public void remover(MouseEvent event) throws Exception {
        App.conexao.setAutoCommit(false);
        try {
            for(PedidoBolo pb: pedidoBoloDAO.buscarPorPedido(pedido)) {
                pedidoBoloDAO.remover(pb);
            }

            pedidoDAO.remover(pedido);
            App.conexao.commit();
            System.out.println(areaDeAlerta == null);
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
