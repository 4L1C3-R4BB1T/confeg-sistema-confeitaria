package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Funcionario;
import modelos.entidades.Pedido;

public class FuncionarioDAO {

    private Connection conexao;

    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Funcionario autenticar(String email, String senha) {
        String comando = "SELECT * FROM funcionario WHERE email = ? AND senha = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet resultado = ps.executeQuery();
            TipoFuncionarioDAO tipoDAO = new TipoFuncionarioDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            if (resultado.next()) {
                return new Funcionario(
                    resultado.getLong("cod_funcionario"),
                    resultado.getString("nome_funcionario"), 
                    resultado.getString("cpf_funcionario"),
                    tipoDAO.buscarPorCodigo(resultado.getLong("cod_tipo_funcionario")),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco")),
                    resultado.getString("email"),
                    resultado.getString("senha")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }
    
    public Long inserir(Funcionario funcionario) {
        String comando = "INSERT INTO funcionario (nome_funcionario, cpf_funcionario, cod_tipo_funcionario, cod_endereco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setLong(3, funcionario.getTipo().getCodigo());
            ps.setLong(4, funcionario.getEndereco().getCodigo());
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

    public boolean alterar(Funcionario funcionario) {
        String comando = "UPDATE funcionario SET nome_funcionario = ?, cpf_funcionario = ?, cod_tipo_funcionario = ?, cod_endereco = ?, email = ?, senha = ? WHERE cod_funcionario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setLong(3, funcionario.getTipo().getCodigo());
            ps.setLong(4, funcionario.getEndereco().getCodigo());
            ps.setString(5, funcionario.getEmail());
            ps.setString(6, funcionario.getSenha());
            ps.setLong(7, funcionario.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
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
            erro.printStackTrace();
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
                    resultado.getLong("cod_funcionario"),
                    resultado.getString("nome_funcionario"), 
                    resultado.getString("cpf_funcionario"),
                    tipoDAO.buscarPorCodigo(resultado.getLong("cod_tipo_funcionario")),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco")),
                    resultado.getString("email"),
                    resultado.getString("senha")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public Funcionario buscarPorPedido(Pedido pedido) {
        String comando = "SELECT * FROM funcionario WHERE cod_funcionario = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getFuncionario().getCodigo());
            ResultSet resultado = ps.executeQuery();
            TipoFuncionarioDAO tipoDAO = new TipoFuncionarioDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            if (resultado.next()) {
                return new Funcionario(
                    resultado.getLong("cod_funcionario"),
                    resultado.getString("nome_funcionario"), 
                    resultado.getString("cpf_funcionario"),
                    tipoDAO.buscarPorCodigo(resultado.getLong("cod_tipo_funcionario")),
                    enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco")),
                    resultado.getString("email"),
                    resultado.getString("senha")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public List<Funcionario> buscarTodos() {
        String comando = "SELECT * FROM funcionario ORDER BY cod_funcionario ASC";
        List<Funcionario> funcionarios = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            TipoFuncionarioDAO tipoDAO = new TipoFuncionarioDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            while (resultado.next()) {
                funcionarios.add(new Funcionario(
                        resultado.getLong("cod_funcionario"),
                        resultado.getString("nome_funcionario"), 
                        resultado.getString("cpf_funcionario"),
                        tipoDAO.buscarPorCodigo(resultado.getLong("cod_tipo_funcionario")),
                        enderecoDAO.buscarPorCodigo(resultado.getLong("cod_endereco")),
                        resultado.getString("email"),
                        resultado.getString("senha")
                    )
                );
            }
            return funcionarios;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return funcionarios;
    }

    public List<Funcionario> filtar(String valor) {
        String comando = "SELECT * FROM funcionario WHERE unaccent(LOWER(nome_funcionario)) LIKE unaccent(LOWER('%"+ valor +"%'))";
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            Statement stm = conexao.createStatement();
            ResultSet rs = stm.executeQuery(comando);
            TipoFuncionarioDAO tipoDAO = new TipoFuncionarioDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            while(rs.next()) {
                funcionarios.add(new Funcionario(
                    rs.getLong("cod_funcionario"),
                    rs.getString("nome_funcionario"), 
                    rs.getString("cpf_funcionario"),
                    tipoDAO.buscarPorCodigo(rs.getLong("cod_tipo_funcionario")),
                    enderecoDAO.buscarPorCodigo(rs.getLong("cod_endereco")),
                    rs.getString("email"),
                    rs.getString("senha")
                )
            );
            }

        } catch (Exception erro) {
            erro.printStackTrace();
        }

        return funcionarios;
    }

    public String gerarEmail(Funcionario funcionario) {
        String tipo = funcionario.getTipo().getDescricao().equals("Funcionário") ? "funcionario" : "gerente";
        return String.format("%s%d@confeg.com", tipo, funcionario.getCodigo()); 
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}