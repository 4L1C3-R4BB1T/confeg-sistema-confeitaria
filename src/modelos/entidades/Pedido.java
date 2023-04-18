package modelos.entidades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.enums.Status;

public class Pedido {

    private Long codigo;
    private Cliente cliente;
    private Funcionario funcionario;
    private Date dataPedido;
    private MetodoPagamento metodo;
    private Status status;
    private String observacao;
    private List<Bolo> bolos = new ArrayList<>();

    public Pedido(Cliente cliente, Funcionario funcionario, Date dataPedido, MetodoPagamento metodo, Status status,
        String observacao) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.dataPedido = dataPedido;
        this.metodo = metodo;
        this.status = status;
        this.observacao = observacao;
    }

    public Pedido(Long codigo, Cliente cliente, Funcionario funcionario, Date dataPedido, MetodoPagamento metodo,
    Status status, String observacao) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.dataPedido = dataPedido;
        this.metodo = metodo;
        this.status = status;
        this.observacao = observacao;
    }
    
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public Date getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public MetodoPagamento getMetodo() {
        return metodo;
    }
    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public List<Bolo> getBolos() {
        return bolos;
    }
    public void setBolos(List<Bolo> bolos) {
        this.bolos = bolos;
    }


}
