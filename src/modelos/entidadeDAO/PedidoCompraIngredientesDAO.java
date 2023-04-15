package modelos.entidadeDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.PedidoCompra;
import modelos.entidades.PedidoCompraIngrediente;

public class PedidoCompraIngredientesDAO {

    private Connection conexao;
    
    public PedidoCompraIngredientesDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    /* IMPLEMENTAR */
    public boolean inserir(PedidoCompraIngrediente pedido) {
        return false;
    }

    /* IMPLEMENTAR */
    public boolean alterar(PedidoCompraIngrediente pedido) {
        return false;
    }

    /* IMPLEMENTAR */
    public boolean remover(PedidoCompraIngrediente pedido) {
        return false;
    }

    /* IMPLEMENTAR */
    public PedidoCompraIngrediente buscarPorCodigoLong(Long codigo) {
        return null;
    }

    /* IMPLEMENTAR */
    public List<PedidoCompraIngrediente> buscarPorPedidoCompra(PedidoCompra pedidoCompra) {
        List<PedidoCompraIngrediente> pedidos = new ArrayList<>();
        return pedidos;
    }

    /* IMPLEMENTAR */
    public List<PedidoCompraIngrediente> buscarTodos() {
        List<PedidoCompraIngrediente> pedidos = new ArrayList<>();
        return pedidos;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
