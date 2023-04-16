package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelos.entidades.Endereco;

public class EnderecoDAO {

    private Connection conexao;

    public EnderecoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(Endereco endereco) {
        String comando = "INSERT INTO endereco (cep_endereco, cod_estado, cod_cidade, bairro_endereco, rua_endereco, numero_endereco) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, endereco.getCep());
            ps.setLong(2, endereco.getEstado().getCodigo());
            ps.setLong(3, endereco.getCidade().getCodigo());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getRua());
            ps.setInt(6, endereco.getNumero());
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

    public boolean alterar(Endereco endereco) {
        String comando = "UPADTE endereco SET cep_endereco = ?, cod_estado = ?, cod_cidade = ?, bairro_endereco = ?, rua_endereco = ?, numero_endereco = ? WHERE cod_endereco = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, endereco.getCep());
            ps.setLong(2, endereco.getEstado().getCodigo());
            ps.setLong(3, endereco.getCidade().getCodigo());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getRua());
            ps.setInt(6, endereco.getNumero());
            ps.setLong(7, endereco.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public Endereco buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM endereco WHERE cod_endereco = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            EstadoDAO estadoDAO = new EstadoDAO(conexao);
            CidadeDAO cidadeDAO = new CidadeDAO(conexao);
            if (resultado.next()) {
                return new Endereco(
                    resultado.getLong("cod_endereco"),
                    estadoDAO.buscarPorCodigo(resultado.getLong("cod_estado")),
                    cidadeDAO.buscar(resultado.getLong("cod_cidade")),
                    resultado.getString("cep_endereco"),
                    resultado.getString("bairro_endereco"),
                    resultado.getString("rua_endereco"),
                    resultado.getInt("numero_endereco")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
