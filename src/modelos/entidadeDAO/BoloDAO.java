package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public Bolo buscarPorCodigo(Bolo bolo) {

    }

    public List<Bolo> buscarTodos() {

    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
