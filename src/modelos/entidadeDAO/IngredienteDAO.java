package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Ingrediente;

public class IngredienteDAO {

    private Connection conexao;
    
    public IngredienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Ingrediente buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM ingrediente WHERE cod_ingrediente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new Ingrediente(
                    resultado.getLong("cod_ingrediente"),
                    resultado.getString("descricao_ingrediente")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }
    
    public List<Ingrediente> buscarTodos() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM ingrediente")) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                ingredientes.add(new Ingrediente(
                    resultado.getLong("cod_ingrediente"),
                    resultado.getString("descricao_ingrediente")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return ingredientes;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
