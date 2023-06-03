package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricarConexao {

    private Connection conexao;

    public FabricarConexao() {
        try {
            Properties propriedades = new Properties();
            propriedades.load(getClass().getResourceAsStream("/configuracoes.properties"));
            conexao = DriverManager.getConnection(
                propriedades.getProperty("db_url"),
                propriedades.getProperty("db_user"),
                propriedades.getProperty("db_password")
            );
        } catch (Exception erro) {
            Logger registro = Logger.getLogger("FabricarConexao");
            registro.log(Level.SEVERE, "Não foi possível se conectar ao banco.", erro);
        }
    }

    public Connection getConexao() {
        return conexao;
    }
    
}
