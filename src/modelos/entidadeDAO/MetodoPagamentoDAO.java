package modelos.entidadeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelos.entidades.MetodoPagamento;

public class MetodoPagamentoDAO {

    private Connection conexao;

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
    
}
