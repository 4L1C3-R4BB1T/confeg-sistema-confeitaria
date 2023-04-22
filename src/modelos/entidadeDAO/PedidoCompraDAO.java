package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Funcionario;
import modelos.entidades.PedidoCompra;
import modelos.entidades.enums.Status;

public class PedidoCompraDAO {

    private Connection conexao;
    
    public PedidoCompraDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(PedidoCompra pedido) {
        String comando = "INSERT INTO pedido_compra (cod_funcionario, data_pedido_compra, status_pedido_compra, observacao_pedido_compra) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, pedido.getFuncionario().getCodigo());
            ps.setDate(2, pedido.getDataPedido());
            ps.setString(3, Status.PENDENTE.getDescricao());
            ps.setString(4, pedido.getObservacao());
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
   
    public boolean alterar(PedidoCompra pedido) {
        String comando = "UPDATE pedido_compra SET cod_funcionario = ?, data_pedido_compra = ?, status_pedido_compra = ?, observacao_pedido_compra = ? WHERE cod_pedido_compra = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, pedido.getFuncionario().getCodigo());
            ps.setDate(2, pedido.getDataPedido());
            ps.setString(3, Status.PENDENTE.getDescricao());
            ps.setString(4, pedido.getObservacao());
            ps.setLong(5, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public boolean alterarStatus(PedidoCompra pedido) {
        String comando = "UPDATE pedido_compra SET status_pedido_compra = ? WHERE cod_pedido_compra = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {          
            ps.setString(1, pedido.getStatus().getDescricao());
            ps.setLong(2, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public boolean remover(PedidoCompra pedido) {
        String comando = "DELETE FROM pedido_compra WHERE cod_pedido_compra = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public PedidoCompra buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM pedido_compra WHERE cod_pedido_compra = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            if (resultado.next()) {
                return new PedidoCompra(
                    resultado.getLong("cod_pedido_compra"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido_compra"),
                    Status.valueOf(resultado.getString("status_pedido_compra")),
                    resultado.getString("observacao_pedido_compra")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public List<PedidoCompra> buscarPorFuncionario(Funcionario funcionario) {
        String comando = "SELECT * FROM pedido_compra WHERE cod_funcionario = ?";
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, funcionario.getCodigo());
            ResultSet resultado = ps.executeQuery();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompra(
                    resultado.getLong("cod_pedido_compra"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido_compra"),
                    Status.valueOf(resultado.getString("status_pedido_compra")),
                    resultado.getString("observacao_pedido_compra")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<PedidoCompra> buscarPendentesPorFuncionario(Funcionario funcionario) {
        String comando = "SELECT * FROM pedido_compra WHERE cod_funcionario = ? AND status_pedido_compra = 'PENDENTE'";
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, funcionario.getCodigo());
            ResultSet resultado = ps.executeQuery(); 
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompra(
                    resultado.getLong("cod_pedido_compra"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido_compra"),
                    Status.valueOf(resultado.getString("status_pedido_compra")),
                    resultado.getString("observacao_pedido_compra")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<PedidoCompra> buscarPorFuncionarioMes(Funcionario funcionario, int mes) {
        String comando = "SELECT * FROM pedido_compra WHERE cod_funcionario = ? AND EXTRACT(MONTH FROM data_pedido_compra) = ?";
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, funcionario.getCodigo());
            ps.setInt(2, mes);
            ResultSet resultado = ps.executeQuery();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompra(
                    resultado.getLong("cod_pedido_compra"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido_compra"),
                    Status.valueOf(resultado.getString("status_pedido_compra")),
                    resultado.getString("observacao_pedido_compra")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<PedidoCompra> buscarPendentes() {
        String comando = "SELECT * FROM pedido_compra WHERE status_pedido_compra = 'PENDENTE'";
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompra(
                    resultado.getLong("cod_pedido_compra"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido_compra"),
                    Status.valueOf(resultado.getString("status_pedido_compra")),
                    resultado.getString("observacao_pedido_compra")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<PedidoCompra> buscarTodos() {
        String comando = "SELECT * FROM pedido_compra";
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompra(
                    resultado.getLong("cod_pedido_compra"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido_compra"),
                    Status.valueOf(resultado.getString("status_pedido_compra")),
                    resultado.getString("observacao_pedido_compra")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
