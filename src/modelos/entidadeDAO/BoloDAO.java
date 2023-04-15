package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Bolo;

public class BoloDAO {

    private Connection conexao;

    public BoloDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(Bolo bolo) {

    }

    public boolean alterar(Bolo bolo) {

    }

    public boolean remover(Bolo bolo) {
        String comando = "DELETE FROM bolo WHERE cod_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, bolo.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public Bolo buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM bolo WHERE cod_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            SaborDAO saborDAO = new SaborDAO(conexao);
            if (resultado.next()) {
                return new Bolo(
                    resultado.getLong("cod_bolo"),
                    saborDAO.buscarPorCodigo(resultado.getLong("cod_sabor")),
                    resultado.getString("descricao_bolo"), 
                    resultado.getDouble("peso_bolo"),
                    resultado.getDouble("preco_bolo"),
                    resultado.getDate("data_fabricacao_bolo"),
                    resultado.getDate("data_vencimento_bolo")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public List<Bolo> buscarTodos() {
        String comando = "SELECT * FROM bolo";
        List<Bolo> bolos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            SaborDAO saborDAO = new SaborDAO(conexao);
            while (resultado.next()) {
                bolos.add(new Bolo(
                    resultado.getLong("cod_bolo"),
                    saborDAO.buscarPorCodigo(resultado.getLong("cod_sabor")),
                    resultado.getString("descricao_bolo"), 
                    resultado.getDouble("peso_bolo"),
                    resultado.getDouble("preco_bolo"),
                    resultado.getDate("data_fabricacao_bolo"),
                    resultado.getDate("data_vencimento_bolo")
                ));
            }
            return bolos;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return bolos;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
