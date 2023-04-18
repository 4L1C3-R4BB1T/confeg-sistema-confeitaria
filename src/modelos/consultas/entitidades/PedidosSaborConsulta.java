package modelos.consultas.entitidades;

public class PedidosSaborConsulta {

    private Long codigo;
    private String sabor;
    private Double total;
    private Long pedidos;

    public PedidosSaborConsulta(Long codigo, String sabor, Double total, Long pedidos) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.total = total;
        this.pedidos = pedidos;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

}
