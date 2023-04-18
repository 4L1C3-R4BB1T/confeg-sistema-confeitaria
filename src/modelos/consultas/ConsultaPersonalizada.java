package modelos.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexoes.FabricarConexao;
import modelos.consultas.entitidades.PedidoConsulta;
import modelos.entidadeDAO.ClienteDAO;

public final class ConsultaPersonalizada {


    private ConsultaPersonalizada() {}

    private static Connection conexao;

    static {
        String url = "jdbc:postgresql://localhost:5432/test";
        String usuario = "postgres";
        String senha = "admin";
        conexao = new FabricarConexao(url, usuario, senha).getConexao();
    }
    
    public static List<PedidoConsulta> obterPedidoConsultas() {
        List<PedidoConsulta> pedidoConsultas = new ArrayList<>();
        String comando = "SELECT p.cod_pedido as \"pedido\", c.cod_cliente as \"cliente\", SUM(b.preco_bolo * pb.quantidade_bolo) as \"total\"," +
            "p.data_pedido as \"data\", p.status_pedido as \"status\"" +
            "FROM pedido_bolo pb " +
            "INNER JOIN pedido p ON pb.cod_pedido = p.cod_pedido " +
            "INNER JOIN bolo b ON pb.cod_bolo = b.cod_bolo " +
            "INNER JOIN cliente c ON c.cod_cliente = p.cod_cliente " +
            "GROUP BY p.data_pedido, p.cod_pedido, c.cod_cliente ORDER BY p.cod_pedido ASC";

        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            while (resultado.next()){
                pedidoConsultas.add(new PedidoConsulta(
                    resultado.getLong("pedido"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cliente")),
                    resultado.getDouble("total"),
                    resultado.getDate("data"),
                    resultado.getString("status")
                ));
            }

        } catch (Exception erro) {
            erro.printStackTrace();
        }

        return pedidoConsultas;
    }
    
}
