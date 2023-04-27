package modelos.entidades;

import java.io.Serializable;

public class MetodoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String descricao;

    public MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public MetodoPagamento(Long codigo, String descricao) {
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
