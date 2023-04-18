package modelos.consultas.entitidades;

public class PedidosMesAnoConsulta {
    
    private Integer ano;
    private Integer mes;
    private Long pedidos;
    
    public PedidosMesAnoConsulta(Integer ano, Integer mes, Long pedidos) {
        this.ano = ano;
        this.mes = mes;
        this.pedidos = pedidos;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }
    
}
