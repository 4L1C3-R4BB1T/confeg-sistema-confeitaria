package modelos.consultas.entitidades;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PedidoConfirmado {

    private Long codigo;
    private String nome;
    private Long codigoPedido;
    private String data;
    private Label pago = new Label();
    private HBox botoes;

    public PedidoConfirmado() {}

    public PedidoConfirmado(Long codigo, String nome, Long codigoPedido, Date data, Boolean pago) {
        this.codigo = codigo;
        this.nome = nome;
        this.codigoPedido = codigoPedido;
        this.setData(data);
        this.setPago(pago);
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(Long codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getData() {
        return data;
    }

    public void setData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.data = sdf.format(data);
    }

    public Label getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        String cor = "-fx-text-fill: #038700;";
        this.pago.setText("SIM");
        if (!pago) {
            this.pago.setText("NÃO");
            cor = "-fx-text-fill: #ED0000;";
        } 
        this.pago.setStyle(cor);
    }

    public void setBotoes(HBox botoes) {
        this.botoes = botoes;
    }

    public HBox getBotoes() {
        return botoes;
    }
    
}
