package modelos.consultas.entitidades;

import javafx.scene.control.Label;

public class ReceitaMesConsulta {

    private Integer ano;
    private Integer mes;
    private Long concluidos;
    private Label receita = new Label();

    public ReceitaMesConsulta(Integer ano, Integer mes, Long concluidos, Double receita) {
        this.ano = ano;
        this.mes = mes;
        this.concluidos = concluidos; 
        setReceita(receita);
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

    public Long getConcluidos() {
        return concluidos;
    }

    public void setConcluidos(Long concluidos) {
        this.concluidos = concluidos;
    }

    public Label getReceita() {
        return receita;
    }

    public void setReceita(Double receita) {
        this.receita.setStyle("-fx-text-fill: #038700;");
        this.receita.setText("R$ " + String.valueOf(receita));
    }
    
}
