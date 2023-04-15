package modelos.entidades;

public class Cliente extends Pessoa {

    private String telefone;

    public Cliente(Long codigo, String nome, String cpf, String telefone, Endereco endereco) {
        super(codigo, nome, cpf, endereco);
        this.telefone = telefone;
    }

    public Cliente(String nome, String cpf, String telefone, Endereco endereco) {
        super(nome, cpf, endereco);
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
