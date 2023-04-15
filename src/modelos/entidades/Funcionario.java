package modelos.entidades;

public class Funcionario extends Pessoa {

    private TipoFuncionario tipo;
    private String email;
    private String senha;

    public Funcionario(String nome, String cpf, TipoFuncionario tipo, Endereco endereco, String email,
            String senha) {
        super(nome, cpf, endereco);
        this.tipo = tipo;
        this.email = email;
        this.senha = senha;
    }

    public Funcionario(Long codigo, String nome, String cpf, TipoFuncionario tipo, Endereco endereco,
            String email, String senha) {
        super(codigo, nome, cpf, endereco);
        this.tipo = tipo;
        this.email = email;
        this.senha = senha;
    }

    public TipoFuncionario getTipo() {
        return tipo;
    }

    public void setTipo(TipoFuncionario tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
