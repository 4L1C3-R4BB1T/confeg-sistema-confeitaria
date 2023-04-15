package modelos.entidades;

public class PedidoAdicional {

    private Long codigo;
    private PedidoBolo pedidoBolo;
    private Adicional adicional;
    private Long quantidade;

    public PedidoAdicional(Long codigo, PedidoBolo pedidoBolo, Adicional adicional, Long quantidade) {
        this.codigo = codigo;
        this.pedidoBolo = pedidoBolo;
        this.adicional = adicional;
        this.quantidade = quantidade;
    }

    public PedidoAdicional(PedidoBolo pedidoBolo, Adicional adicional, Long quantidade) {
        this.pedidoBolo = pedidoBolo;
        this.adicional = adicional;
        this.quantidade = quantidade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public PedidoBolo getPedidoBolo() {
        return pedidoBolo;
    }

    public void setPedidoBolo(PedidoBolo pedidoBolo) {
        this.pedidoBolo = pedidoBolo;
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
