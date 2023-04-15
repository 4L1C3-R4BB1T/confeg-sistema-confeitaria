package modelos.entidades;

public class PedidoBolo {

    private Long codigo;
    private Pedido pedido;
    private Bolo bolo;
    private Long quantidade;

    public PedidoBolo(Long codigo, Pedido pedido, Bolo bolo, Long quantidade) {
        this.codigo = codigo;
        this.pedido = pedido;
        this.bolo = bolo;
        this.quantidade = quantidade;
    }
    
    public PedidoBolo(Pedido pedido, Bolo bolo, Long quantidade) {
        this.pedido = pedido;
        this.bolo = bolo;
        this.quantidade = quantidade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Bolo getBolo() {
        return bolo;
    }

    public void setBolo(Bolo bolo) {
        this.bolo = bolo;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

}
