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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Funcionario other = (Funcionario) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (senha == null) {
            if (other.senha != null)
                return false;
        } else if (!senha.equals(other.senha))
            return false;
        return true;
    }


    @Override 
    public String toString() {
        return nome;
    }
}
