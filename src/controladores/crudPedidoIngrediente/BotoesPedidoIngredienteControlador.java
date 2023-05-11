package controladores.crudPedidoIngrediente;

import java.util.List;

import aplicacao.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.consultas.entitidades.PedidoIngrediente;
import modelos.entidadeDAO.PedidoCompraDAO;
import modelos.entidadeDAO.PedidoCompraIngredientesDAO;
import modelos.entidades.Funcionario;
import modelos.entidades.PedidoCompra;
import modelos.entidades.PedidoCompraIngrediente;
import modelos.interfaces.AproveitarFuncao;

public class BotoesPedidoIngredienteControlador {

    private PedidoCompra pedidoCompra;
    private Funcionario funcionario;
    private PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(App.conexao);
    private PedidoCompraIngredientesDAO pedidoCompraIngredientesDAO = new PedidoCompraIngredientesDAO(App.conexao);

    private boolean clicouBotaoEditar = false;

    private Node areaDeAlerta;

    private AproveitarFuncao atualizarAreaConteudo;
    
    @FXML
    public void editar(MouseEvent event) {
        if (pedidoCompra.getStatus().getDescricao() == "CONCLUIDO" || pedidoCompra.getStatus().getDescricao() == "CANCELADO"){
            App.exibirAlert(areaDeAlerta, "FRACASSO", "EDIÇÃO", "Não é possível editar esse Pedido.");
            return;
        }
        if (!clicouBotaoEditar) {
            carregarTelaEditar();
        } else {
            App.exibirAlert(areaDeAlerta, "INFORMAÇÃO", "EDIÇÃO", "Edição já aberta para Pedido de Ingrediente.");
        }
    }

    @FXML
    public void remover(MouseEvent event) throws Exception {
        if (pedidoCompra != null) {
            App.conexao.setAutoCommit(false);
            try {
                // remover pedidocompraingrediente do pedidocompra
                pedidoCompraIngredientesDAO.buscarPorPedidoCompra(pedidoCompra).forEach(pedidoCompraIngrediente ->
                    pedidoCompraIngredientesDAO.remover(pedidoCompraIngrediente)
                );

                pedidoCompraDAO.remover(pedidoCompra);
                App.conexao.commit();
                App.exibirAlert(areaDeAlerta, "SUCESSO", "DELEÇÃO", "O Pedido de Compra com ID: " + pedidoCompra.getCodigo() + " foi removido.");
                atualizarAreaConteudo.usar();
            } catch (Exception erro) {
                erro.printStackTrace();
                App.conexao.rollback();
                App.exibirAlert(areaDeAlerta, "FRACASSO", "DELEÇÃO", "Erro interno.");
            }
        }

    }

    @FXML
    public void initialize() {}

    public void carregarTelaEditar() {
        try {
            clicouBotaoEditar = true;
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/telas/ingredientes/edicao/edicao.fxml"));
            Parent elemento = carregar.load();
            EditarPedidoIngredienteControlador controlador = carregar.getController();
            Scene cena = new Scene(elemento);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.setScene(cena);
            

            List<PedidoCompraIngrediente> carrinho = pedidoCompraIngredientesDAO.buscarPorPedidoCompra(pedidoCompra);

            controlador.setItemNaTabela(carrinho);
            controlador.setTela(palco);
            controlador.setFuncionario(funcionario);
            controlador.setPedidoCompra(pedidoCompra);

            App.adicionarMovimento(palco, cena);
            palco.showAndWait();
            if (controlador.getSucesso()) {
                App.exibirAlert(areaDeAlerta, "SUCESSO", "EDIÇÃO", "Pedido de Ingrediente alterado com sucesso.");
            } else if (controlador.getFracasso()) {
                App.exibirAlert(areaDeAlerta, "FRACASSO", "EDIÇÃO", "Não foi possível editar o Pedido de Ingrediente.");
            }
            clicouBotaoEditar = false;
            atualizarAreaConteudo.usar();
        } catch (Exception erro) {
           erro.printStackTrace();
        }
    }

    public void setConteudo(PedidoIngrediente pedidoIngrediente) {
        PedidoCompra pc = pedidoCompraDAO.buscarPorCodigo(pedidoIngrediente.getCodigo());
        this.pedidoCompra = pc;
        this.funcionario = pc.getFuncionario();
    }

    public void setAreaDeAlerta(Node alerta) {
        this.areaDeAlerta = alerta;
    } 

    public void setAtualizarAreaConteudo(AproveitarFuncao atualizarAreaConteudo) {
        this.atualizarAreaConteudo = atualizarAreaConteudo;
    }
    
}
