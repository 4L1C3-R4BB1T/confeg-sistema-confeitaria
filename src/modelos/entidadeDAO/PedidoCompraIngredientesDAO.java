package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.PedidoCompra;
import modelos.entidades.PedidoCompraIngrediente;

public class PedidoCompraIngredientesDAO {

    private Connection conexao;
    
    public PedidoCompraIngredientesDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public boolean inserir(PedidoCompraIngrediente pedido) {
        String comando = "INSERT INTO pedido_compra_ingrediente (cod_pedido_compra, cod_ingrediente, quantidade_ingrediente) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getPedidoCompra().getCodigo());
            ps.setLong(2, pedido.getIngrediente().getCodigo());
            ps.setLong(3, pedido.getQuantidade());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro em PedidoCompraIngredientesDAO: " + erro.getMessage());
        }
        return false;
    }

    public boolean alterar(PedidoCompraIngrediente pedido) {
        String comando = "UPDATE pedido_compra_ingrediente SET  cod_pedido_compra = ?, cod_ingrediente = ?, quantidade_ingrediente = ? WHERE cod_pedido_compra_ingrediente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getPedidoCompra().getCodigo());
            ps.setLong(2, pedido.getIngrediente().getCodigo());
            ps.setLong(3, pedido.getQuantidade());
            ps.setLong(4, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro em PedidoCompraIngredientesDAO: " + erro.getMessage());
        }
        return false;
    }

    public boolean remover(PedidoCompraIngrediente pedido) {
        String comando = "DELETE FROM pedido_compra_ingrediente WHERE cod_pedido_compra_ingrediente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedido.getCodigo());
            ps.execute();
            return true;
        } catch (Exception erro) {
            System.out.println("Erro em PedidoCompraIngredientesDAO: " + erro.getMessage());
        }
        return false;
    }

    public PedidoCompraIngrediente buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM pedido_compra_ingrediente WHERE cod_pedido_compra_ingrediente = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(conexao);
            IngredienteDAO ingredienteDAO = new IngredienteDAO(conexao);
            if (resultado.next()) {
                return new PedidoCompraIngrediente(
                    resultado.getLong("cod_pedido_compra_ingrediente"),
                    pedidoCompraDAO.buscarPorCodigo(resultado.getLong("cod_pedido_compra")),
                    ingredienteDAO.buscarPorCodigo(resultado.getLong("cod_ingrediente")),
                    resultado.getLong("quantidade_ingrediente")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro em PedidoCompraIngredientesDAO: " + erro.getMessage());
        }
        return null;
    }

    public List<PedidoCompraIngrediente> buscarPorPedidoCompra(PedidoCompra pedidoCompra) {
        String comando = "SELECT * FROM pedido_compra_ingrediente WHERE cod_pedido_compra = ?";
        List<PedidoCompraIngrediente> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, pedidoCompra.getCodigo());
            ResultSet resultado = ps.executeQuery();
            PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(conexao);
            IngredienteDAO ingredienteDAO = new IngredienteDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompraIngrediente(
                    resultado.getLong("cod_pedido_compra_ingrediente"),
                    pedidoCompraDAO.buscarPorCodigo(resultado.getLong("cod_pedido_compra")),
                    ingredienteDAO.buscarPorCodigo(resultado.getLong("cod_ingrediente")),
                    resultado.getLong("quantidade_ingrediente")
                ));
            }
            return pedidos;
        } catch (Exception erro) {
            System.out.println("Erro em PedidoCompraIngredientesDAO: " + erro.getMessage());
        }    
        return pedidos;
    }

    public List<PedidoCompraIngrediente> buscarTodos() {
        String comando = "SELECT * FROM pedido_compra_ingrediente";
        List<PedidoCompraIngrediente> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            PedidoCompraDAO pedidoCompraDAO = new PedidoCompraDAO(conexao);
            IngredienteDAO ingredienteDAO = new IngredienteDAO(conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoCompraIngrediente(
                    resultado.getLong("cod_pedido_compra_ingrediente"),
                    pedidoCompraDAO.buscarPorCodigo(resultado.getLong("cod_pedido_compra")),
                    ingredienteDAO.buscarPorCodigo(resultado.getLong("cod_ingrediente")),
                    resultado.getLong("quantidade_ingrediente")
                ));
            }
            return pedidos;
        } catch (Exception erro) {
            System.out.println("Erro em PedidoCompraIngredientesDAO: " + erro.getMessage());
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
