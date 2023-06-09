package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Cliente;

public class ClienteDAO {

    private Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public Long inserir(Cliente cliente) {
        String comando = "INSERT INTO cliente (nome_cliente, cpf_cliente, telefone_cliente, cod_endereco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setLong(4, cliente.getEndereco().getCodigo());
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

    public boolean alterar(Cliente cliente) {
        String comando = "UPDATE cliente SET nome_cliente = ?, cpf_cliente = ?, telefone_cliente = ?, cod_endereco = ? WHERE cod_cliente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setLong(4, cliente.getEndereco().getCodigo());
            ps.setLong(5, cliente.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public boolean remover(Cliente cliente) {
        String comando = "DELETE FROM cliente WHERE cod_cliente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, cliente.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public Cliente buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM cliente WHERE cod_cliente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            if (resultado.next()) {
                return new Cliente(
                    resultado.getLong("cod_cliente"),
                    resultado.getString("nome_cliente"), 
                    resultado.getString("cpf_cliente"),
                    resultado.getString("telefone_cliente"),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco"))
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public List<Cliente> filtrar(String nome) {
        String comando = "SELECT * FROM cliente WHERE unaccent(LOWER(nome_cliente)) LIKE unaccent(LOWER('%"+ nome +"%'))";

        List<Cliente> clientes = new ArrayList<Cliente>();
        try {
            Statement stm = conexao.createStatement();
            ResultSet rs = stm.executeQuery(comando);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getLong("cod_cliente"),
                    rs.getString("nome_cliente"), 
                    rs.getString("cpf_cliente"),
                    rs.getString("telefone_cliente"),
                    enderecoDAO.buscarPorCodigo(rs.getLong("cod_endereco"))
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return clientes;
    }

    public List<Cliente> buscarTodosComPedidosPendentes() {
        String comando = "SELECT DISTINCT c.* " +
            "FROM cliente c " +
            "INNER JOIN pedido p " +
            "ON c.cod_cliente = p.cod_cliente " +
            "WHERE p.status_pedido = 'PENDENTE'";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            while (resultado.next()) {
                clientes.add(new Cliente(
                    resultado.getLong("cod_cliente"),
                    resultado.getString("nome_cliente"), 
                    resultado.getString("cpf_cliente"),
                    resultado.getString("telefone_cliente"),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco"))
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return clientes;
    }

    public List<Cliente> buscarTodos() {
        String comando = "SELECT * FROM cliente ORDER BY cod_cliente ASC";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            while (resultado.next()) {
                clientes.add(new Cliente(
                    resultado.getLong("cod_cliente"),
                    resultado.getString("nome_cliente"), 
                    resultado.getString("cpf_cliente"),
                    resultado.getString("telefone_cliente"),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco"))
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return clientes;
    }

    public boolean buscarPorCPF(String valor) {
        final String sql = String.format("SELECT * FROM cliente WHERE cpf_cliente = '%s';", valor);
        try {
            Statement statement = conexao.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch(Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}