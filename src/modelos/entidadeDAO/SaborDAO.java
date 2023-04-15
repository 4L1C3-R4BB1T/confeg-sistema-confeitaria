package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Sabor;

public class SaborDAO {

    private Connection connection;


    public SaborDAO(Connection connection) {
        this.connection = connection;
    }

    public Sabor encontrar(long codigo) {
        String query = "SELECT * FROM sabor WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new Sabor(
                    resultado.getLong("cod_sabor"),
                    resultado.getString("descricao_sabor")
                ); 
            }
    
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public List<Sabor> buscarTodos() {
        List<Sabor> sabores = new ArrayList<>();
        String query = "SELECT * FROM sabor";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
               sabores.add(new Sabor(
                    resultado.getLong("cod_sabor"),
                    resultado.getString("descricao_sabor")
                )
               );
            }
    
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return sabores;
    }



    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    
}
