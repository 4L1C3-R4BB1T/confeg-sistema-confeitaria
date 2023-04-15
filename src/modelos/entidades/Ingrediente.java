package modelos.entidades;

public class Ingrediente {

    private Long codigo;
    private String descricao;

    public Ingrediente(String descricao) {
        this.descricao = descricao;
    }

    public Ingrediente(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
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

    @Override 
    public String toString() {
        return descricao;
    }
    
}
