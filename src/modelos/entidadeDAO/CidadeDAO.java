package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Cidade;

public class CidadeDAO {

    private Connection connection;

    public CidadeDAO(Connection connection) {
        this.connection = connection;
    }

    public Cidade encontrar(Long codigo) {
        String comando = "SELECT * FROM cidade WHERE cod_cidade = ?";
        try (PreparedStatement ps = connection.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                EstadoDAO estadoDAO = new EstadoDAO(connection);
                return new Cidade (
                    resultado.getLong("cod_cidade"),
                    resultado.getString("nome_cidade"),
                   estadoDAO.encontrar(resultado.getLong("cod_estado"))
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    } 


    public List<Cidade> buscarTodos() {
        List<Cidade> cidades = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM cidade")) {
            ResultSet resultado = ps.executeQuery();
            EstadoDAO estadoDAO = new EstadoDAO(connection);
            while (resultado.next()) {
                cidades.add(new Cidade(
                    resultado.getLong("cod_cidade"),
                    resultado.getString("nome_cidade"),
                   estadoDAO.encontrar(resultado.getLong("cod_estado"))
                ));
            }

        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }

        return cidades;
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
