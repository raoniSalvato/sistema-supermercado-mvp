
package br.ufes.dcomp.supermercado.model;

public class Produto {
    private Long id;
    private String nome;
    private Double precoCusto;
    private Categoria categoria;
    private Double margemLucroAtual;
    private Double precoVendaAtual;

    public Produto(Long id, String nome, Double precoCusto, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
        this.margemLucroAtual = 0.0;
        this.precoVendaAtual = 0.0;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Double getMargemLucroAtual() {
        return margemLucroAtual;
    }

    public Double getPrecoVendaAtual() {
        return precoVendaAtual;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setMargemLucroAtual(Double margemLucroAtual) {
        this.margemLucroAtual = margemLucroAtual;
    }

    public void setPrecoVendaAtual(Double precoVendaAtual) {
        this.precoVendaAtual = precoVendaAtual;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
   
}
