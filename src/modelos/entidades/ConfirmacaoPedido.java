package modelos.entidades;

import java.sql.Date;

public class ConfirmacaoPedido {

    private Cliente cliente;
    private Pedido pedido;
    private Date dataConfirmacao;
   
    public ConfirmacaoPedido(Cliente cliente, Pedido pedido, Date dataConfirmacao) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.dataConfirmacao = dataConfirmacao;
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

}
