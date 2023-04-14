package modelos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;

import conexoes.FabricarConexao;

public class DAO {

    private Connection conexao;

    public DAO() {
        conexao = new FabricarConexao("jdbc:postgresql://localhost:5432/test", "postgres", "admin")
            .getConexao();
    }
    
   public <E> E consultar(Class<E> classe, String query) {
        try(PreparedStatement ps = conexao.prepareStatement(query)) {
            
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                ResultSetMetaData dados = resultado.getMetaData();
                int qntColuna = dados.getColumnCount();
                Class<?>[] tipos = new Class[qntColuna];
                Object[] valores = new Object[qntColuna];

                for (int i = 0 ; i < qntColuna ; i++) {
                    valores[i] = resultado.getObject(dados.getColumnName(i + 1));
                    tipos[i] = Class.forName(dados.getColumnClassName(i + 1));
                }
            
                return classe.getConstructor(tipos).newInstance(valores);
            }
        } catch (Exception erro) { 
            // Faz nada 
        }
        return null;
   }
}
