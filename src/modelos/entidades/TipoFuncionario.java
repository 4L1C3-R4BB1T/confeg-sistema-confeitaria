package modelos.entidades;

public class TipoFuncionario {

    private Long codigo;
    private String descricao;

    public TipoFuncionario(String descricao) {
        this.descricao = descricao;
    }

    public TipoFuncionario(Long codigo, String descricao) {
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
}
