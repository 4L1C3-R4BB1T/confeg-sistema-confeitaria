package modelos.entidades;

import java.io.Serializable;
import java.sql.Date;

public class ConfirmacaoPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private Cliente cliente;
    private Pedido pedido;
    private Date dataConfirmacao;
    private Boolean pago;
    private String observacao;
   
    public ConfirmacaoPedido(Long codigo, Cliente cliente, Pedido pedido, Date dataConfirmacao, Boolean pago,
            String observacao) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.pedido = pedido;
        this.dataConfirmacao = dataConfirmacao;
        this.pago = pago;
        this.observacao = observacao;
    }

    public ConfirmacaoPedido(Cliente cliente, Pedido pedido, Date dataConfirmacao, Boolean pago, String observacao) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.dataConfirmacao = dataConfirmacao;
        this.pago = pago;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Date getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(Date dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
