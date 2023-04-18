package modelos.consultas.entitidades;

import java.sql.Date;

import javafx.scene.Node;
import modelos.entidades.Cliente;

public class PedidoConsulta {

    private Long codigo;
    private Cliente cliente;
    private Double total;
    private Date dataPedido;
    private String status;
    private Node node;

    public PedidoConsulta(Cliente cliente, Double total, Date dataPedido, String status) {
        this.cliente = cliente;
        this.total = total;
        this.dataPedido = dataPedido;
        this.status = status;
    }

    public PedidoConsulta(Long codigo, Cliente cliente, Double total, Date dataPedido, String status) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.total = total;
        this.dataPedido = dataPedido;
        this.status = status;
    }

    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    } 

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }


    @Override
    public String toString() {
        return dataPedido.toString();
    }


}
