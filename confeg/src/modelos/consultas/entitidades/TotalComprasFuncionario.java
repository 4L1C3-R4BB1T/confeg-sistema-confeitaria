package modelos.consultas.entitidades;

public class TotalComprasFuncionario {

    private Integer ano;
    private Integer mes;
    private Long funcionario;
    private Double total;

    public TotalComprasFuncionario(Integer ano, Integer mes, Long funcionario, Double total) {
        this.ano = ano;
        this.mes = mes;
        this.funcionario = funcionario;
        this.total = total;
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

    public Long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Long funcionario) {
        this.funcionario = funcionario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
