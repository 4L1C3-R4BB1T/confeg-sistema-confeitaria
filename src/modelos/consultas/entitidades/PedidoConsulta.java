package modelos.consultas.entitidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.Node;
import javafx.scene.control.Label;
import modelos.entidades.Cliente;

public class PedidoConsulta {

    private Long codigo;
    private Cliente cliente;
    private Label total = new Label();
    private String dataPedido;
    private Label status = new Label();
    private Label desconto = new Label();
    private Node node;
    private Double totalDouble;

    public PedidoConsulta(Cliente cliente, Double total, Date dataPedido, String status, Double desconto) {
        this.cliente = cliente;
        this.setTotal(total);
        this.setDataPedido(dataPedido);
        totalDouble = total;
        setStatus(status);
        this.setDesconto(desconto);
    }

    public PedidoConsulta(Long codigo, Cliente cliente, Double total, Date dataPedido, String status, Double desconto) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.setTotal(total);
        this.setDataPedido(dataPedido);
        setStatus(status);
        totalDouble = total;
        this.setDesconto(desconto);
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

    public Label getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total.setStyle("-fx-text-fill: #085D10");
        this.total.setText(String.format("R$ %.2f", total));
    }

    public Double getTotalDouble() {
        return totalDouble;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.dataPedido = sdf.format(dataPedido);
    }

    public Label getStatus() {
        return status;
    }

    public void setStatus(String status) {
        String cor = "";
        if (status.intern() == "PENDENTE") {
            cor = "-fx-text-fill: #C8B400;";
        } else if (status.intern() == "CONCLUIDO") {
            cor = "-fx-text-fill: #038700;";
        } else {
            cor = "-fx-text-fill: #ED0000;";
        }

        this.status.setStyle(cor);
        this.status.setText(status);
    } 

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Label getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        if (desconto > 0) {
            this.desconto.setStyle("-fx-text-fill: #085D10;");
            this.desconto.setText(String.format("+ %.2f%%", desconto));
        } else {
            this.desconto.setStyle("-fx-text-fill: #000;");
            this.desconto.setText(String.format("%.2f%%", desconto));
        }
        
    }
    
}
