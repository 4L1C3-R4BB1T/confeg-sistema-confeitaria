package modelos.entidadeDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PedidoBoloDAO {
    
    private Connection conexao;
    
    public PedidoBoloDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /* IMPLEMENTAR */
    public boolean inserir(PedidoBoloDAO pedido) {
        return false;
    }

    /* IMPLEMENTAR */
    public boolean alterar(PedidoBoloDAO pedido) {
        return false;
    }

    /* IMPLEMENTAR */
    public boolean remover(PedidoBoloDAO pedido) {
        return false;
    }

    /* IMPLEMENTAR */
    public PedidoBoloDAO buscarPorCodigo(Long codigo) {
        return null;
    }

    /* IMPLEMENTAR */
    public List<PedidoBoloDAO> buscarPorPedido(PedidoDAO pedido) {
        List<PedidoBoloDAO> pedidos = new ArrayList<>();
        return pedidos;
    }

    /* IMPLEMENTAR */
    public List<PedidoBoloDAO> buscarTodos() {
        List<PedidoBoloDAO> pedidos = new ArrayList<>();
        return pedidos;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
