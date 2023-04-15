package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Adicional;

public class AdicionalDAO {

    private Connection connection;
    
    public AdicionalDAO(Connection connection) {
        this.connection = connection;
    }

    public Adicional encontrar(Long codigo) {
        String comando = "SELECT * FROM adicional WHERE cod_adicional = ?";
        try (PreparedStatement ps = connection.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new Adicional(
                    resultado.getLong("cod_adicional"),
                    resultado.getString("descricao_adicional"),
                    resultado.getDouble("preco_adicional")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }
    
    public List<Adicional> buscarTodos() {
        List<Adicional> adicionais = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM adicional")) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                adicionais.add(new Adicional(
                    resultado.getLong("cod_adicional"),
                    resultado.getString("descricao_adicional"),
                    resultado.getDouble("preco_adicional")
                ));
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return adicionais;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
