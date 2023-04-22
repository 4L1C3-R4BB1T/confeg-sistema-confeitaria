package modelos.entidades;

public class PedidoCompraIngrediente {

    private Long codigo;
    private PedidoCompra pedidoCompra;
    private Ingrediente ingrediente;
    private Long quantidade;
    

    public PedidoCompraIngrediente() {}
    
    public PedidoCompraIngrediente(Long codigo, PedidoCompra pedidoCompra, Ingrediente ingrediente, Long quantidade) {
        this.codigo = codigo;
        this.pedidoCompra = pedidoCompra;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public PedidoCompraIngrediente(PedidoCompra pedidoCompra, Ingrediente ingrediente, Long quantidade) {
        this.pedidoCompra = pedidoCompra;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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
