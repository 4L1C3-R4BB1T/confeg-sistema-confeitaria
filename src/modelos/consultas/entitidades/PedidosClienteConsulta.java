package modelos.consultas.entitidades;

import java.sql.Date;

public class PedidosClienteConsulta {

    private String nome;
    private String cpf;
    private String telefone;
    private Date ultimoPedido;
    private Long pedidos;
    
    public PedidosClienteConsulta(String nome, String cpf, String telefone, Date ultimoPedido, Long pedidos) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.ultimoPedido = ultimoPedido;
        this.pedidos = pedidos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getUltimoPedido() {
        return ultimoPedido;
    }

    public void setUltimoPedido(Date ultimoPedido) {
        this.ultimoPedido = ultimoPedido;
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

}
