package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Pedido;
import modelos.entidades.PedidoBolo;

public class PedidoBoloDAO {
    
    private Connection conexao;
    
    public PedidoBoloDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean inserir(PedidoBolo pedido) {
        String comando = "INSERT INTO pedido_bolo (cod_pedido, cod_bolo, quantidade_bolo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getPedido().getCodigo());
            ps.setLong(2, pedido.getBolo().getCodigo());
            ps.setLong(3, pedido.getQuantidade());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public boolean alterar(PedidoBolo pedido) {
        String comando = "UPDATE pedido_bolo SET cod_pedido = ?, cod_bolo = ?, quantidade_bolo = ? WHERE cod_pedido_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getPedido().getCodigo());
            ps.setLong(2, pedido.getBolo().getCodigo());
            ps.setLong(3, pedido.getQuantidade());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public boolean remover(PedidoBolo pedido) {
        String comando = "DELETE FROM pedido_bolo WHERE cod_pedido_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return false;
    }

    public PedidoBolo buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM pedido_bolo WHERE cod_pedido_bolo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            PedidoDAO pedidoDAO = new PedidoDAO(conexao);
            BoloDAO boloDAO = new BoloDAO(conexao);
            if (resultado.next()) {
                return new PedidoBolo(
                    resultado.getLong("cod_pedido_bolo"),
                    pedidoDAO.buscarPorCodigo(resultado.getLong("cod_pedido")),
                    boloDAO.buscarPorCodigo(resultado.getLong("cod_bolo")),
                    resultado.getLong("quantidade_bolo")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    }

    public List<PedidoBolo> buscarPorPedido(Pedido pedido) {
        String comando = "SELECT * FROM pedido_bolo WHERE cod_pedido = ?";
        List<PedidoBolo> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getCodigo());
            ResultSet resultado = ps.executeQuery();
            PedidoDAO pedidoDAO = new PedidoDAO(conexao);
            BoloDAO boloDAO = new BoloDAO(conexao);
            if (resultado.next()) {
                pedidos.add(new PedidoBolo(
                    resultado.getLong("cod_pedido_bolo"),
                    pedidoDAO.buscarPorCodigo(resultado.getLong("cod_pedido")),
                    boloDAO.buscarPorCodigo(resultado.getLong("cod_bolo")),
                    resultado.getLong("quantidade_bolo")
                ));
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return pedidos;
    }

    public List<PedidoBolo> buscarTodos() {
        String comando = "SELECT * FROM pedido_bolo";
        List<PedidoBolo> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            PedidoDAO pedidoDAO = new PedidoDAO(conexao);
            BoloDAO boloDAO = new BoloDAO(conexao);
            if (resultado.next()) {
                pedidos.add(new PedidoBolo(
                    resultado.getLong("cod_pedido_bolo"),
                    pedidoDAO.buscarPorCodigo(resultado.getLong("cod_pedido")),
                    boloDAO.buscarPorCodigo(resultado.getLong("cod_bolo")),
                    resultado.getLong("quantidade_bolo")
                ));
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return pedidos;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }

}
