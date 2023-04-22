package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.ConfirmacaoPedido;

public class ConfirmacaoPedidoDAO {
    
    private Connection conexao;
    
    public ConfirmacaoPedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(ConfirmacaoPedido confirmacao) {
        String comando = "INSERT INTO confirmacao_pedido (cod_cliente, cod_pedido, data_confirmacao_pedido, pago_confirmacao_pedido, observacao_confirmacao_pedido) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, confirmacao.getCliente().getCodigo());
            ps.setLong(2, confirmacao.getPedido().getCodigo());
            ps.setDate(3, confirmacao.getDataConfirmacao());
            ps.setBoolean(4, confirmacao.getPago());
            ps.setString(5, confirmacao.getObservacao());
            ps.execute();
            ResultSet resultado = ps.getGeneratedKeys();
            if (resultado.next()) {
                return resultado.getLong(1);
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public boolean alterar(ConfirmacaoPedido confirmacao) {
        String comando = "UPDATE confirmacao_pedido SET cod_cliente = ?, cod_pedido = ?, data_confirmacao_pedido = ?, pago_confirmacao_pedido = ?, observacao_confirmacao_pedido = ? WHERE cod_confirmacao = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, confirmacao.getCliente().getCodigo());
            ps.setLong(2, confirmacao.getPedido().getCodigo());
            ps.setDate(3, confirmacao.getDataConfirmacao());
            ps.setBoolean(4, confirmacao.getPago());
            ps.setString(5, confirmacao.getObservacao());
            ps.setLong(6, confirmacao.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public boolean remover(ConfirmacaoPedido confirmacao) {
        String comando = "DELETE FROM confirmacao_pedido WHERE cod_confirmacao = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, confirmacao.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public ConfirmacaoPedido buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM confirmacao_pedido WHERE cod_confirmacao = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            PedidoDAO pedidoDAO = new PedidoDAO(conexao);
            if (resultado.next()) {
                return new ConfirmacaoPedido(
                    resultado.getLong("cod_confirmacao"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cod_cliente")),
                    pedidoDAO.buscarPorCodigo(resultado.getLong("cod_pedido")),
                    resultado.getDate("data_confirmacao_pedido"),
                    resultado.getBoolean("pago_confirmacao_pedido"),
                    resultado.getString("observacao_confirmacao_pedido")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public List<ConfirmacaoPedido> buscarTodos() {
        String comando = "SELECT * FROM confirmacao_pedido";
        List<ConfirmacaoPedido> confirmacoes = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            PedidoDAO pedidoDAO = new PedidoDAO(conexao);
            while (resultado.next()) {
                confirmacoes.add(new ConfirmacaoPedido(
                    resultado.getLong("cod_confirmacao"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cod_cliente")),
                    pedidoDAO.buscarPorCodigo(resultado.getLong("cod_pedido")),
                    resultado.getDate("data_confirmacao_pedido"),
                    resultado.getBoolean("pago_confirmacao_pedido"),
                    resultado.getString("observacao_confirmacao_pedido")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return confirmacoes;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
