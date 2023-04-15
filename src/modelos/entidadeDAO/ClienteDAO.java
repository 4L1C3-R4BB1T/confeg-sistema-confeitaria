package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            System.out.println("Erro: " + erro.getMessage());
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
            System.out.println("Erro: " + erro.getMessage());
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
            System.out.println("Erro: " + erro.getMessage());
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
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public List<Cliente> buscarTodos() {
        String comando = "SELECT * FROM cliente";
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
                    )
                );
            }
            return clientes;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return clientes;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}