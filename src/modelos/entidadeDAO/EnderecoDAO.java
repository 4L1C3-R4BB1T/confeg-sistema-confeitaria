package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modelos.entidades.Endereco;

public class EnderecoDAO {

    private Connection connection;

    public EnderecoDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean adicionar(Endereco endereco) {
        String comando = "INSERT INTO endereco (cep_endereco, cod_estado, cod_cidade, bairro_endereco, rua_endereco, numero_endereco) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(comando)) {
            ps.setString(1, endereco.getCep());
            ps.setLong(2, endereco.getEstado().getCodigo());
            ps.setLong(3, endereco.getCidade().getCodigo());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getRua());
            ps.setInt(6, endereco.getNumero());
            return ps.execute();
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    
}
