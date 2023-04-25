package modelos.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import aplicacao.App;

import java.util.Date;

import modelos.consultas.entitidades.PedidoConfirmado;
import modelos.consultas.entitidades.PedidoConsulta;
import modelos.consultas.entitidades.PedidoIngrediente;
import modelos.consultas.entitidades.PedidosClienteConsulta;
import modelos.consultas.entitidades.PedidosSaborConsulta;
import modelos.consultas.entitidades.ReceitaMesConsulta;
import modelos.consultas.entitidades.TotalComprasFuncionario;
import modelos.entidadeDAO.ClienteDAO;
import modelos.entidadeDAO.ConfirmacaoPedidoDAO;
import modelos.entidadeDAO.FuncionarioDAO;
import modelos.entidades.ConfirmacaoPedido;
import modelos.entidades.Funcionario;

public final class ConsultaPersonalizada {

    private Connection conexao;

    public ConsultaPersonalizada(Connection conexao) {
        this.conexao = conexao;
    }

    
    public List<PedidoConsulta> obterPedidoConsultas() {
        List<PedidoConsulta> pedidoConsultas = new ArrayList<>();
        String comando = "SELECT p.cod_pedido as \"pedido\", " + 
            "c.cod_cliente as \"cliente\", " + 
            "SUM(b.preco_bolo * pb.quantidade_bolo) as \"total\"," +
            "p.data_pedido as \"data\", p.status_pedido as \"status\"," +
            "p.desconto_pedido as \"desconto\"" +
            "FROM pedido_bolo pb " +
            "INNER JOIN pedido p ON pb.cod_pedido = p.cod_pedido " +
            "INNER JOIN bolo b ON pb.cod_bolo = b.cod_bolo " +
            "INNER JOIN cliente c ON c.cod_cliente = p.cod_cliente " +
            "GROUP BY pedido, data, cliente, status, desconto " +
            "ORDER BY pedido ASC";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            while (resultado.next()){
                pedidoConsultas.add(new PedidoConsulta(
                    resultado.getLong("pedido"),
                    clienteDAO.buscarPorCodigo(resultado.getLong("cliente")),
                    resultado.getDouble("total"),
                    new Date(resultado.getDate("data").getTime()),
                    resultado.getString("status"),
                    resultado.getDouble("desconto")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidoConsultas;
    }

    public  List<ReceitaMesConsulta> obterReceitaMesConsultas() {
        List<ReceitaMesConsulta> receitaMesConsultas = new ArrayList<>();
        String comando = "SELECT EXTRACT(year FROM cp.data_confirmacao_pedido) AS \"ano\", " +
            "EXTRACT(month FROM cp.data_confirmacao_pedido) AS \"mes\", " +
            "COUNT(DISTINCT cod_confirmacao) AS \"pedidos_concluidos\", " +
            "SUM(b.preco_bolo * pb.quantidade_bolo) AS \"receita\" " +
            "FROM confirmacao_pedido cp " +
            "INNER JOIN pedido p ON cp.cod_pedido = p.cod_pedido " +
            "INNER JOIN pedido_bolo pb ON pb.cod_pedido = p.cod_pedido " +
            "INNER JOIN bolo b ON pb.cod_bolo = b.cod_bolo " +
            "WHERE p.status_pedido = 'CONCLUIDO' " +
            "GROUP BY ano, mes " +
            "ORDER BY ano, mes";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                receitaMesConsultas.add(new ReceitaMesConsulta(
                    resultado.getInt("ano"),
                    resultado.getInt("mes"),
                    resultado.getLong("pedidos_concluidos"),
                    resultado.getDouble("receita")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return receitaMesConsultas;
    }

    public List<PedidosClienteConsulta> obterQuantidadeDePedidosPorCliente() {
        List<PedidosClienteConsulta> pedidosCliente = new ArrayList<>();
        String comando = "SELECT c.nome_cliente AS \"nome\", " +
            "c.cpf_cliente AS \"cpf\", " +
            "c.telefone_cliente AS \"telefone\", " +
            "MAX(p.data_pedido) AS \"ultimo_pedido\", " +
            "COUNT(p.cod_pedido) AS \"quantidade_pedidos\" " +
            "FROM cliente c " +
            "INNER JOIN pedido p ON p.cod_cliente = c.cod_cliente " +
            "GROUP BY nome, cpf, telefone " +
            "ORDER BY quantidade_pedidos DESC, nome";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                pedidosCliente.add(new PedidosClienteConsulta(
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    resultado.getString("telefone"),
                    resultado.getDate("ultimo_pedido"),    
                    resultado.getLong("quantidade_pedidos")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidosCliente;
    }

    public List<PedidosSaborConsulta> obterQuantidadeDePedidosPorSabor() {
        List<PedidosSaborConsulta> pedidosSabor = new ArrayList<>();
        String comando = "SELECT s.cod_sabor AS \"codigo\", " +
            "s.descricao_sabor AS \"sabor\", " +
            "SUM(b.preco_bolo * quantidade_bolo) AS \"total\", " +
            "COUNT(s.cod_sabor) AS \"pedidos\" " +
            "FROM pedido p " +
            "INNER JOIN pedido_bolo pb ON p.cod_pedido = pb.cod_pedido " +
            "INNER JOIN bolo b ON b.cod_bolo = pb.cod_bolo " +
            "INNER JOIN sabor s ON s.cod_sabor = b.cod_sabor " +
            "GROUP BY codigo " +
            "ORDER BY pedidos DESC, total DESC";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                pedidosSabor.add(new PedidosSaborConsulta(
                    resultado.getLong("codigo"),
                    resultado.getString("sabor"),
                    resultado.getDouble("total"),
                    resultado.getLong("pedidos")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidosSabor;
    }

    public List<PedidoIngrediente> obterPedidosDeIngrediente() {
        List<PedidoIngrediente> pedidos = new ArrayList<>();
        String comando = "SELECT pc.cod_pedido_compra as codigo, " +
            "f.cod_funcionario as funcionario, " +
            "pc.data_pedido_compra as \"data\", " +
            "pc.status_pedido_compra as \"status\" " +
            "FROM pedido_compra as pc, " +
            "funcionario as f " +
            "WHERE pc.cod_funcionario = f.cod_funcionario " +
            "ORDER BY data DESC";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ResultSet resultado = ps.executeQuery();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(App.conexao);
            while (resultado.next()) {
                pedidos.add(new PedidoIngrediente(
                    resultado.getLong("codigo"),
                    funcionarioDAO.buscarPorCodigo(resultado.getLong("funcionario")),
                    resultado.getDate("data"),
                    resultado.getString("status")
                ));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return pedidos;
    }

    public List<PedidoConfirmado> obterPedidosConfirmados() {
        List<PedidoConfirmado> pedidos = new ArrayList<>();
        ConfirmacaoPedidoDAO confirmacaoPedidoDAO = new ConfirmacaoPedidoDAO(conexao);
        for (ConfirmacaoPedido cp: confirmacaoPedidoDAO.buscarTodos()) {
            pedidos.add(new PedidoConfirmado(
                cp.getCodigo(),
                cp.getCliente().getNome(),
                cp.getPedido().getCodigo(),
                cp.getDataConfirmacao(),
                cp.getPago()
            ));
        }
        return pedidos;
    }

    public TotalComprasFuncionario totalComprasFuncionarioMes(int ano, int mes, Funcionario funcionario) {
        String comando = "SELECT EXTRACT(YEAR FROM pc.data_pedido_compra) AS \"ano\", " +
            "EXTRACT(MONTH FROM pc.data_pedido_compra) AS \"mes\", " +
            "pc.cod_funcionario AS \"funcionario\", " +
            "SUM(i.preco_ingrediente * pci.quantidade_ingrediente) AS \"total\" " +
            "FROM ingrediente i " +
            "INNER JOIN pedido_compra_ingrediente pci " +
            "ON pci.cod_ingrediente = i.cod_ingrediente " +
            "INNER JOIN pedido_compra pc " +
            "ON pc.cod_pedido_compra = pci.cod_pedido_compra " +
            "WHERE EXTRACT(YEAR FROM pc.data_pedido_compra) = ? " +
            "AND EXTRACT(MONTH FROM pc.data_pedido_compra) = ? " +
            "AND pc.cod_funcionario = ? " +
            "GROUP BY ano, mes, funcionario";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, ano);
            ps.setLong(2, mes);
            ps.setLong(3, funcionario.getCodigo());
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new TotalComprasFuncionario(
                    resultado.getInt("ano"),
                    resultado.getInt("mes"),
                    resultado.getLong("funcionario"),
                    resultado.getDouble("total")
                );
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return null;
    }
    
}
