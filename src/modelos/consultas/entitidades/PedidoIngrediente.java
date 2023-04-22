package modelos.consultas.entitidades;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PedidoIngrediente {
    
    private Long codigo;
    private String nome;
    private String data;
    private Label status = new Label();
    private HBox botoes;

    public PedidoIngrediente(String nome, String data, Label status) {
        this.nome = nome;
        this.data = data;
        this.status = status;
    }

    public PedidoIngrediente(Long codigo, String nome, Date data, String status) {
        this.codigo = codigo;
        this.nome = nome;
        this.setData(data);
        this.setStatus(status);
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.data = sdf.format(data);
    }

    public String getData() {
        return data;
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

    public Label getStatus() {
        return status;
    }

    public void setBotoes(HBox botoes) {
        this.botoes = botoes;
    }

    public HBox getBotoes() {
        return botoes;
    }
}
