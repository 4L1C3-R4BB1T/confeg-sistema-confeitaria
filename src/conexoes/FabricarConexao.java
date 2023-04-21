package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricarConexao {

    private Connection conexao;

    public FabricarConexao(String url, String usuario, String senha) {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException erro) {
            Logger registro = Logger.getLogger("FabricarConexao");
            registro.log(Level.SEVERE, "Não foi possível se conectar ao banco.", erro);
        }
    }

    public Connection getConexao() {
        return conexao;
    }
    
}
