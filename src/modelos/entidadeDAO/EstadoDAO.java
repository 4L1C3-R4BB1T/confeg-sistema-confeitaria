package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Estado;

public class EstadoDAO {

    private Connection connection;

    public EstadoDAO(Connection connection) {
        this.connection = connection;
    }

    public Estado encontrar(Long codigo) {
        String comando = "SELECT * FROM estado WHERE cod_estado = ?";
        try (PreparedStatement ps = connection.prepareStatement(comando)) {
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
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    } 

    public List<Estado> buscarTodos() {
        List<Estado> estados = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM estado")) {

            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                estados.add(new Estado(
                    resultado.getLong("cod_estado"),
                    resultado.getString("nome_estado"),
                    resultado.getString("sigla_estado")
                ));
            }

        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }

        return estados;
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
