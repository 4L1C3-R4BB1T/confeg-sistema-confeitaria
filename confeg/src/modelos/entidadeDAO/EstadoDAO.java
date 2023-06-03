package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Estado;

public class EstadoDAO {

    private Connection conexao;

    public EstadoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Estado buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM estado WHERE cod_estado = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new Estado(
                    resultado.getLong("cod_estado"),
                    resultado.getString("nome_estado"),
                    resultado.getString("sigla_estado")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    } 

    public List<Estado> buscarTodos() {
        List<Estado> estados = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM estado")) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                estados.add(new Estado(
                    resultado.getLong("cod_estado"),
                    resultado.getString("nome_estado"),
                    resultado.getString("sigla_estado")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return estados;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
