package modelos.consultas.entitidades;

public class PedidosBoloConsulta {

    private Long codigo;
    private String sabor;
    private Long pedidos;
    
    public PedidosBoloConsulta(Long codigo, String sabor, Long pedidos) {
        this.codigo = codigo;
        this.sabor = sabor;
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

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

}
