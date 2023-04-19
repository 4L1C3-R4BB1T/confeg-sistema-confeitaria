package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Cliente;
import modelos.entidades.Pedido;
import modelos.entidades.enums.Status;

public class PedidoDAO {

    private Connection conexao;
    
    public PedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(Pedido pedido) {
        String comando = "INSERT INTO pedido (cod_cliente, cod_funcionario, data_pedido, cod_metodo_pagamento, status_pedido, observacao_pedido) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, pedido.getCliente().getCodigo());
            ps.setLong(2, pedido.getFuncionario().getCodigo());
            ps.setDate(3, pedido.getDataPedido());
            ps.setLong(4, pedido.getMetodo().getCodigo());
            ps.setString(5, Status.PENDENTE.getDescricao());
            ps.setString(6, pedido.getObservacao());
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

    public boolean alterar(Pedido pedido) {
        String comando = "UPDATE pedido SET cod_cliente = ?, cod_funcionario = ?, data_pedido = ?, cod_metodo_pagamento = ?, observacao_pedido = ? WHERE cod_pedido = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, pedido.getCliente().getCodigo());
            ps.setLong(2, pedido.getFuncionario().getCodigo());
            ps.setDate(3, pedido.getDataPedido());
            ps.setLong(4, pedido.getMetodo().getCodigo());
            ps.setString(5, pedido.getObservacao());
            ps.setLong(6, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public boolean remover(Pedido pedido) {
        String comando = "DELETE FROM pedido WHERE cod_pedido = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public Pedido buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM pedido WHERE cod_pedido = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO(conexao); 
            if (resultado.next()) {
                return new Pedido(
                    resultado.getLong("cod_pedido"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cod_cliente")),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido"),
                    metodoPagamentoDAO.buscarPorCodigo(resultado.getLong("cod_metodo_pagamento")),
                    Status.valueOf(resultado.getString("status_pedido")),
                    resultado.getString("observacao_pedido")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public List<Pedido> buscarPorCliente(Cliente cliente) {
        String comando = "SELECT * FROM pedido WHERE cod_cliente = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, cliente.getCodigo());
            ResultSet resultado = ps.executeQuery(); 
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new Pedido(
                    resultado.getLong("cod_pedido"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cod_cliente")),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido"),
                    metodoPagamentoDAO.buscarPorCodigo(resultado.getLong("cod_metodo_pagamento")),
                    Status.valueOf(resultado.getString("status_pedido")),
                    resultado.getString("observacao_pedido")
                ));
            }
            return pedidos;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<Pedido> buscarTodos() {
        String comando = "SELECT * FROM pedido";
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery(); 
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new Pedido(
                    resultado.getLong("cod_pedido"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cod_cliente")),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("cod_funcionario")),
                    resultado.getDate("data_pedido"),
                    metodoPagamentoDAO.buscarPorCodigo(resultado.getLong("cod_metodo_pagamento")),
                    Status.valueOf(resultado.getString("status_pedido")),
                    resultado.getString("observacao_pedido")
                ));
            }
            return pedidos;
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
