package modelos.entidades;

public class Cliente extends Pessoa {

    public Cliente(Long codigo, String nome, String cpf, String telefone, Endereco endereco) {
        super(codigo, nome, cpf, telefone, endereco);
    }

    public Cliente(String nome, String cpf, String telefone, Endereco endereco) {
        super(nome, cpf, telefone, endereco);
    }
}
