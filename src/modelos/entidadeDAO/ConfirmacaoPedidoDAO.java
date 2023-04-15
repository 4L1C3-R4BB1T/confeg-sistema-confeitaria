package modelos.entidadeDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.ConfirmacaoPedido;

public class ConfirmacaoPedidoDAO {
    
    private Connection conexao;
    
    public ConfirmacaoPedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /* IMPLEMENTAR */
    public boolean inserir(ConfirmacaoPedido confirmacao) {
        return false;
    }

    /* IMPLEMENTAR */
    public boolean alterar(ConfirmacaoPedido confirmacao) {
        return false;
    }

    /* IMPLEMENTAR */
    public boolean remover(ConfirmacaoPedido confirmacao) {
        return false;
    }

    /* IMPLEMENTAR */
    public ConfirmacaoPedido buscarPorCodigo(Long codigo) {
        return null;
    }

    /* IMPLEMENTAR */
    public List<ConfirmacaoPedido> buscarTodos() {
        List<ConfirmacaoPedido> confirmacoes = new ArrayList<>();
        return confirmacoes;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
