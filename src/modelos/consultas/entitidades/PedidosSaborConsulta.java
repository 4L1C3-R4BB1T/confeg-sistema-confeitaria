package modelos.consultas.entitidades;

import javafx.scene.control.Label;

public class PedidosSaborConsulta {

    private Long codigo;
    private String sabor;
    private Label total = new Label();
    private Long pedidos;

    public PedidosSaborConsulta(Long codigo, String sabor, Double total, Long pedidos) {
        this.codigo = codigo;
        this.sabor = sabor;
        setTotal(total);
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

    public Label getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total.setStyle("-fx-text-fill: #038700;");
        this.total.setText("R$ " + String.valueOf(total));
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

}
