package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelos.entidades.Funcionario;

public class ClienteDAO {

    private Connection conexao;

    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public Long inserir(Funcionario funcionario) {
        String comando = "INSERT INTO funcionario (nome_funcionario, cpf_funcionario, telefone_funcionario, cod_tipo_funcionario, cod_endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setString(3, funcionario.getTelefone());
            ps.setLong(4, funcionario.getTipo().getCodigo());
            ps.setLong(5, funcionario.getEndereco().getCodigo());
            ps.execute();
            ResultSet resultado = ps.getGeneratedKeys();
            if (resultado.next()) {
                Long codigo = resultado.getLong(1);
                inserirEmaileSenha(codigo);
                return codigo;
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public boolean alterar(Funcionario funcionario) {
        String comando = "UPDATE funcionario SET nome_funcionario = ?, cpf_funcionario = ?, telefone_funcionario = ?, cod_tipo_funcionario = ?, cod_endereco = ? WHERE cod_funcionario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setString(3, funcionario.getTelefone());
            ps.setLong(4, funcionario.getTipo().getCodigo());
            ps.setLong(5, funcionario.getEndereco().getCodigo());
            ps.setLong(6, funcionario.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public boolean remover(Funcionario funcionario) {
        String comando = "DELETE FROM funcionario WHERE cod_funcionario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, funcionario.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public Funcionario buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM funcionario WHERE cod_funcionario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            TipoFuncionarioDAO tipoDAO = new TipoFuncionarioDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            if (resultado.next()) {
                return new Funcionario(
                    resultado.getString("nome_funcionario"), 
                    resultado.getString("cpf_funcionario"),
                    resultado.getString("telefone_funcionario"),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco")),
                    tipoDAO.buscarPorCodigo(resultado.getLong("cod_tipo_funcionario")),
                    resultado.getString("email"),
                    resultado.getString("senha")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public List<Funcionario> buscarTodos() {
        String comando = "SELECT * FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            TipoFuncionarioDAO tipoDAO = new TipoFuncionarioDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            while (resultado.next()) {
                funcionarios.add(new Funcionario(
                        resultado.getString("nome_funcionario"), 
                        resultado.getString("cpf_funcionario"),
                        resultado.getString("telefone_funcionario"),
                        enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco")),
                        tipoDAO.buscarPorCodigo(resultado.getLong("cod_tipo_funcionario")),
                        resultado.getString("email"),
                        resultado.getString("senha")
                    )
                );
            }
            return funcionarios;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return funcionarios;
    }

    // GERAR EMAIL AUTOMATICO
    public String gerarEmail(Funcionario funcionario) {
        return String.format("%s%d@.confeg.com", funcionario.getTipo().getDescricao(), funcionario.getCodigo()); 
    }

    // SETAR O EMAIL GERADO NO FUNCIONARIO
    public boolean inserirEmaileSenha(Long codigo) {
        String comando = "UPDATE funcionario SET email = ?, senha = ? WHERE cod_funcionario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setString(1, gerarEmail(buscarPorCodigo(codigo)));
            ps.setString(2, "confeg123");
            ps.setLong(3, codigo);
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
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