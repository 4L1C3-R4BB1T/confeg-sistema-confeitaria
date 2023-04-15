package modelos.entidades;

public class PedidoAdicional {

    private Pedido pedido;
    private Adicional adicional;
    private Long quantidade;
    
    public PedidoAdicional(Pedido pedido, Adicional adicional, Long quantidade) {
        this.pedido = pedido;
        this.adicional = adicional;
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Adicional getAdicional() {
        return adicional;
    }

    public void setAdicional(Adicional adicional) {
        this.adicional = adicional;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

}
