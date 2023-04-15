package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Cidade;
import modelos.entidades.Estado;

public class CidadeDAO {

    private Connection connection;

    public CidadeDAO(Connection connection) {
        this.connection = connection;
    }

<<<<<<< HEAD
    public List<Cidade> buscarPorEstado(Estado estado) {
=======
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
>>>>>>> e111689d7a32f85901e8995deac1cd77fae3140e
        List<Cidade> cidades = new ArrayList<>();
        String comando = "SELECT * FROM cidade WHERE cod_estado = ?";
        try (PreparedStatement ps = connection.prepareStatement(comando)) {
            ps.setLong(1, estado.getCodigo());
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
