package modelos.entidades;

public class Ingrediente {

    private Long codigo;
    private String descricao;
    private Double preco;

    public Ingrediente(String descricao) {
        this.descricao = descricao;
    }

    public Ingrediente(Long codigo, String descricao, Double preco) {
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

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPreco() {
        return preco;
    }

    @Override 
    public String toString() {
        return String.format("%s - R$ %.2f", descricao, preco);
    }
    
}
