package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelos.entidades.Bolo;
import modelos.entidades.Cliente;
import modelos.entidades.Funcionario;
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

    public boolean alterarConfirmacao(Pedido pedido) {
        String comando = "UPDATE pedido SET status_pedido = ?, desconto_pedido = ? WHERE cod_pedido = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pedido.getStatus().getDescricao());
            ps.setDouble(2, pedido.getDesconto());
            ps.setLong(3, pedido.getCodigo());
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
                    resultado.getString("observacao_pedido"),
                    resultado.getDouble("desconto_pedido")
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
                    resultado.getString("observacao_pedido"),
                    resultado.getDouble("desconto_pedido")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<Pedido> buscarPendentesPorCliente(Cliente cliente) {
        String comando = "SELECT * FROM pedido WHERE cod_cliente = ? AND status_pedido = 'PENDENTE'";
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
                    resultado.getString("observacao_pedido"),
                    resultado.getDouble("desconto_pedido")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<Pedido> buscarPorFuncionario(Funcionario funcionario) {
        String comando = "SELECT * FROM pedido WHERE cod_funcionario = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, funcionario.getCodigo());
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
                    resultado.getString("observacao_pedido"),
                    resultado.getDouble("desconto_pedido")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<Pedido> buscarPorBolo(Bolo bolo) {
        String comando = "SELECT p.* " +
            "FROM pedido p " +
            "INNER JOIN pedido_bolo pb " +
            "ON p.cod_pedido = pb.cod_pedido " +
            "INNER JOIN bolo b " +
            "ON b.cod_bolo = pb.cod_bolo " +
            "WHERE pb.cod_bolo = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, bolo.getCodigo());
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
                    resultado.getString("observacao_pedido"),
                    resultado.getDouble("desconto_pedido")
                ));
            }
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
                    resultado.getString("observacao_pedido"),
                    resultado.getDouble("desconto_pedido")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public Map<String, Integer> obterQuantidadeDePedidosPorBolo() {
        Map<String, Integer> pedidosBolo = new HashMap<>();
        String comando = "SELECT s.cod_sabor AS \"codigo\", " +
            "s.descricao_sabor AS \"sabor\", " +
            "COUNT(s.cod_sabor) AS \"pedidos\" " +
            "FROM pedido p " +
            "INNER JOIN pedido_bolo pb ON p.cod_pedido = pb.cod_pedido " +
            "INNER JOIN bolo b ON b.cod_bolo = pb.cod_bolo " +
            "INNER JOIN sabor s ON s.cod_sabor = b.cod_sabor " +
            "GROUP BY codigo";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                pedidosBolo.put(resultado.getString("sabor"), resultado.getInt("pedidos"));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidosBolo;
    }

    public Map<String, Integer> obterQuantidadeDePedidosPorPagamento() {
        Map<String, Integer> pedidosMetodo = new HashMap<>();
        String comando = "SELECT mp.cod_metodo_pagamento AS \"codigo\", " +
            "mp.descricao_metodo_pagamento AS \"metodo\", " +
            "COUNT(p.cod_pedido) AS \"pedidos\" " +
            "FROM pedido p " +
            "INNER JOIN metodo_pagamento mp " + 
            "ON mp.cod_metodo_pagamento = p.cod_metodo_pagamento " +
            "GROUP BY codigo";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                pedidosMetodo.put(resultado.getString("metodo"), resultado.getInt("pedidos"));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidosMetodo;
    }

    public List<Integer> obterAnosQuePossuemConfirmacoes() {
        List<Integer> anos = new ArrayList<>();
        String comando = "SELECT DISTINCT EXTRACT(YEAR from data_confirmacao_pedido) AS ano " +
            "FROM confirmacao_pedido GROUP BY ano ORDER BY ano";
        try (PreparedStatement ps = conexao.prepareStatement(comando);) {           
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                anos.add(resultado.getInt("ano"));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return anos;
    }

    public Map<Integer, Integer> obterQuantidadeDePedidosPorMesAno(int ano) {
        Map<Integer, Integer> pedidosMes = new HashMap<>();
        String comando = "SELECT EXTRACT(MONTH FROM cp.data_confirmacao_pedido) AS \"mes\", " +
            "COUNT(DISTINCT cod_confirmacao) AS \"pedidos\" " +
            "FROM confirmacao_pedido cp " +
            "INNER JOIN pedido p ON cp.cod_pedido = p.cod_pedido " +
            "WHERE p.status_pedido = 'CONCLUIDO' " +
            "AND EXTRACT(YEAR FROM cp.data_confirmacao_pedido) = ? " +
            "GROUP BY mes";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, ano);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                pedidosMes.put(resultado.getInt("mes"), resultado.getInt("pedidos"));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidosMes;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
