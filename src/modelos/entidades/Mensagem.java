package modelos.entidades;
import java.io.Serializable;

public class Mensagem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Funcionario funcionario;
    private String conteudo;

    public Mensagem(Funcionario funcionario, String conteudo) {
        this.funcionario = funcionario;
        this.conteudo = conteudo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
}
