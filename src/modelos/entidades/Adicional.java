package modelos.entidades;

public class Adicional {

    private Long codigo;
    private String descricao;
    private Double preco;

    public Adicional(String descricao, Double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    public Adicional(Long codigo, String descricao, Double preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

}
