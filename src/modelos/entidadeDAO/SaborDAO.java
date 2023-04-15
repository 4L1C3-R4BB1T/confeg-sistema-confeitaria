package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Sabor;

public class SaborDAO {

    private Connection conexao;

    public SaborDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Sabor buscarPorCodigo(long codigo) {
        String query = "SELECT * FROM sabor WHERE codigo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(query)) {
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
        try (PreparedStatement ps = conexao.prepareStatement(query)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
               sabores.add(new Sabor(
                    resultado.getLong("cod_sabor"),
                    resultado.getString("descricao_sabor")
                ));
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return sabores;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
