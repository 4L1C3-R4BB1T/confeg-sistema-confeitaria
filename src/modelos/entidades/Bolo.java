package modelos.entidades;

import java.sql.Date;

public class Bolo {

    private Long codigo;
    private Sabor sabor;
    private String descricao;
    private Double peso;
    private Double preco;
    private Date fabricacao;
    private Date vencimento;

    public Bolo(Sabor sabor, String descricao, Double peso, Double preco, Date fabricacao, Date vencimento) {
        this.sabor = sabor;
        this.descricao = descricao;
        this.peso = peso;
        this.preco = preco;
        this.fabricacao = fabricacao;
        this.vencimento = vencimento;
    }

    public Bolo(Long codigo, Sabor sabor, String descricao, Double peso, Double preco, Date fabricacao,
            Date vencimento) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.descricao = descricao;
        this.peso = peso;
        this.preco = preco;
        this.fabricacao = fabricacao;
        this.vencimento = vencimento;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Date getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(Date fabricacao) {
        this.fabricacao = fabricacao;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

}
