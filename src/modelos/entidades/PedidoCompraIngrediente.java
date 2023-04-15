package modelos.entidades;

public class PedidoCompraIngrediente {

    private PedidoCompra pedidoCompra;
    private Ingrediente ingrediente;
    private Long quantidade;
    
    public PedidoCompraIngrediente(PedidoCompra pedidoCompra, Ingrediente ingrediente, Long quantidade) {
        this.pedidoCompra = pedidoCompra;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public PedidoCompra getPedidoCompra() {
        return pedidoCompra;
    }

    public void setPedidoCompra(PedidoCompra pedidoCompra) {
        this.pedidoCompra = pedidoCompra;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

}
