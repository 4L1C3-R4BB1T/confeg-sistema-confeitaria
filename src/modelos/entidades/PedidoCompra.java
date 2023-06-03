package modelos.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import modelos.entidades.enums.Status;

public class PedidoCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private Funcionario funcionario;
    private Date dataPedido;
    private Status status;
    private String observacao;

    public PedidoCompra(){}
    
    public PedidoCompra(Long codigo, Funcionario funcionario, Date dataPedido, 
        Status status, String observacao) {
        this.codigo = codigo;
        this.funcionario = funcionario;
        this.dataPedido = dataPedido;
        this.status = status;
        this.observacao = observacao;
    }

    public PedidoCompra(Long codigo, Funcionario funcionario, Date dataPedido, Status status) {
        this.codigo = codigo;
        this.funcionario = funcionario;
        this.dataPedido = dataPedido;
        this.status = status;   
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    @Override 
    public String toString() {
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Cod: %d - %s - Funcionário: %s", codigo, formatar.format(getDataPedido()), funcionario.getNome());
    }

}
