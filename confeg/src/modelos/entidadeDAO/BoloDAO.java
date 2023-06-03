package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Bolo;

public class BoloDAO {

    private Connection conexao;

    public BoloDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(Bolo bolo) {
        String comando = "INSERT INTO bolo (cod_sabor, descricao_bolo, peso_bolo, preco_bolo, data_fabricacao_bolo, data_vencimento_bolo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, bolo.getSabor().getCodigo());
            ps.setString(2, bolo.getDescricao());
            ps.setDouble(3, bolo.getPeso());
            ps.setDouble(4, bolo.getPreco());
            ps.setDate(5, bolo.getFabricacao());
            ps.setDate(6, bolo.getVencimento());
            ps.execute();
            ResultSet resultado = ps.getGeneratedKeys();
            if (resultado.next()) {
                return resultado.getLong(1);
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }

    public boolean alterar(Bolo bolo) {
        String comando = "UPDATE bolo SET cod_sabor = ?, descricao_bolo = ?, peso_bolo = ?, preco_bolo = ? WHERE cod_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, bolo.getSabor().getCodigo());
            ps.setString(2, bolo.getDescricao());
            ps.setDouble(3, bolo.getPeso());
            ps.setDouble(4, bolo.getPreco());
            ps.setLong(5, bolo.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return false;
    }

    public boolean remover(Bolo bolo) {
        String comando = "DELETE FROM bolo WHERE cod_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, bolo.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
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
            erro.printStackTrace();
        }
        return null;
    }

    public List<Bolo> buscarTodos() {
        String comando = "SELECT * FROM bolo ORDER BY cod_bolo ASC";
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
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return bolos;
    }

    public List<Bolo> pesquisar(String conteudo) {
        List<Bolo> bolos = new ArrayList<>();
        String comando = "SELECT b.* " +
               "FROM bolo b " +
               "INNER JOIN sabor s " +
               "ON s.cod_sabor = b.cod_sabor " +
               "WHERE LOWER(b.descricao_bolo) LIKE LOWER('%" + conteudo + "%') " +
               "OR LOWER(s.descricao_sabor) LIKE LOWER('%" + conteudo + "%')";
        try  {
            SaborDAO saborDAO = new SaborDAO(conexao);
            Statement ps = conexao.createStatement();
            ResultSet rs = ps.executeQuery(comando);
            while (rs.next()) {
                bolos.add(new Bolo(
                    rs.getLong("cod_bolo"),
                    saborDAO.buscarPorCodigo(rs.getLong("cod_sabor")),
                    rs.getString("descricao_bolo"), 
                    rs.getDouble("peso_bolo"),
                    rs.getDouble("preco_bolo"),
                    rs.getDate("data_fabricacao_bolo"),
                    rs.getDate("data_vencimento_bolo")
                ));
            }
        } catch (Exception erro) {
           erro.printStackTrace();
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
