package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Adicional;

public class AdicionalDAO {

    private Connection conexao;
    
    public AdicionalDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Adicional encontrar(Long codigo) {
        String comando = "SELECT * FROM adicional WHERE cod_adicional = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
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
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM adicional")) {
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
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
