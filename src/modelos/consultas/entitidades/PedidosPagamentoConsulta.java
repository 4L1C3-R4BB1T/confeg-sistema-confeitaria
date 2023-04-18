package modelos.consultas.entitidades;

public class PedidosPagamentoConsulta {

    private Long codigo;
    private String metodo;
    private Long pedidos;
    
    public PedidosPagamentoConsulta(Long codigo, String metodo, Long pedidos) {
        this.codigo = codigo;
        this.metodo = metodo;
        this.pedidos = pedidos;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

}
