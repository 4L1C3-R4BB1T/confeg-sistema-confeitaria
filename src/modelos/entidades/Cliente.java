package modelos.entidades;

public class Cliente extends Pessoa {
    
    public Cliente(Long codigo, String nome, String cpf, String telepone, Endereco endereco) {
        super(codigo, nome, cpf, telepone, endereco);
    }

    public Cliente(String nome, String cpf, String telepone, Endereco endereco) {
        super(nome, cpf, telepone, endereco);
    }
}
