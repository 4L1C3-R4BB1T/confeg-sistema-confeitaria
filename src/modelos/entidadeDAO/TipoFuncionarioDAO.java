package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.TipoFuncionario;

public class TipoFuncionarioDAO {
    
    private Connection connection;

    public TipoFuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public TipoFuncionario encontrar(Long codigo) {
        String comando = "SELECT * FROM tipo_funcionario WHERE cod_tipo_funcionario = ?";
        try (PreparedStatement ps = connection.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new TipoFuncionario(
                    resultado.getLong("cod_tipo_funcionario"),
                    resultado.getString("descricao_tipo_funcionario")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public List<TipoFuncionario> obterTodos() {
        List<TipoFuncionario> tipos = new ArrayList<>();
        String query = "SELECT * FROM tipo_funcionario";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                tipos.add(new TipoFuncionario(
                    resultado.getLong("cod_tipo_funcionario"),
                    resultado.getString("descricao_tipo_funcionario")
                ));
            }
    
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return tipos;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
