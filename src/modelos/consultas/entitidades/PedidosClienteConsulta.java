package modelos.consultas.entitidades;

import java.sql.Date;
import java.text.SimpleDateFormat;

import modelos.validacao.ValidaFormulario;

public class PedidosClienteConsulta {

    private String nome;
    private String cpf;
    private String telefone;
    private String ultimoPedido;
    private Long pedidos;
    
    public PedidosClienteConsulta(String nome, String cpf, String telefone, Date ultimoPedido, Long pedidos) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.setUltimoPedido(ultimoPedido);
        this.pedidos = pedidos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        ValidaFormulario vf = new ValidaFormulario();
        return vf.formatarCPF(cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        ValidaFormulario vf = new ValidaFormulario();
        return vf.formatarTelefone(telefone);
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUltimoPedido() {
        return ultimoPedido;
    }

    public void setUltimoPedido(Date ultimoPedido) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.ultimoPedido = sdf.format(ultimoPedido);
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

}
