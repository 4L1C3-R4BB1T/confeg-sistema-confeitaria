package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.MetodoPagamento;

public class MetodoPagamentoDAO {

    private Connection conexao;

    public MetodoPagamentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public MetodoPagamento buscarPorCodigo(Long codigo) {
        String comando = "SELECT * FROM metodo_pagamento WHERE cod_metodo_pagamento = ?";
        try (PreparedStatement ps = conexao.prepareStatement(comando)) {
            ps.setLong(1, codigo);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                return new MetodoPagamento(
                    resultado.getLong("cod_metodo_pagamento"),
                    resultado.getString("descricao_metodo_pagamento")
                );
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return null;
    } 

    public List<MetodoPagamento> buscarTodos() {
        List<MetodoPagamento> metodos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM metodo_pagamento")) {
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                metodos.add(new MetodoPagamento(
                    resultado.getLong("cod_metodo_pagamento"),
                    resultado.getString("descricao_metodo_pagamento")
                ));
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return metodos;
    }

    public Connection getConnection() {
        return conexao;
    }

    public void setConnection(Connection conexao) {
        this.conexao = conexao;
    }
    
}
