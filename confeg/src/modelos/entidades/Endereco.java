package modelos.entidades;

import java.io.Serializable;

public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private Estado estado;
    private Cidade cidade;
    private String cep;
    private String bairro;
    private String rua;
    private Integer numero;

    public Endereco(Estado estado, Cidade cidade, String cep, String bairro, String rua, Integer numero) {
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    public Endereco(Long codigo, Estado estado, Cidade cidade, String cep, String bairro, String rua, Integer numero) {
        this.codigo = codigo;
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}
