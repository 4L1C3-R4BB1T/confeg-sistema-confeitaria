package modelos.entidades;

import java.io.Serializable;

public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String nome;
    private String sigla;

    public Estado(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public Estado(Long codigo, String nome, String sigla) {
        this.codigo = codigo;
        this.nome = nome;
        this.sigla = sigla;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    } 

    @Override 
    public String toString() {
        return nome;
    }

}
