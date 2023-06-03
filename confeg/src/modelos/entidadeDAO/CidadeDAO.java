package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Cidade;
import modelos.entidades.Estado;

public class CidadeDAO {

    private Connection conexao;

    public CidadeDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Cidade buscar(Long codigo) {
        String comando = "SELECT * FROM cidade WHERE cod_cidade = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            EstadoDAO estadoDAO = new EstadoDAO(conexao);
            if (resultado.next()) {
                return new Cidade(
                    resultado.getLong("cod_cidade"),
                    resultado.getString("nome_cidade"),
                    estadoDAO.buscarPorCodigo(resultado.getLong("cod_estado"))
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }

        return null;
    }

    public List<Cidade> buscarPorEstado(Estado estado) {
        List<Cidade> cidades = new ArrayList<>();
        String comando = "SELECT * FROM cidade WHERE cod_estado = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, estado.getCodigo());
            ResultSet resultado = ps.executeQuery();
            EstadoDAO estadoDAO = new EstadoDAO(conexao);
            while (resultado.next()) {
                cidades.add(new Cidade(
                    resultado.getLong("cod_cidade"),
                    resultado.getString("nome_cidade"),
                    estadoDAO.buscarPorCodigo(resultado.getLong("cod_estado"))
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return cidades;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
