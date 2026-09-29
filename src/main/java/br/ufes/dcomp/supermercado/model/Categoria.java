package br.ufes.dcomp.supermercado.model;

public class Categoria {
    private Long id;
    private String nome;
    private Double percentualLucro;

    public Categoria(Long id, String nome, Double percentualLucro) {
        this.id = id;
        this.nome = nome;
        this.percentualLucro = percentualLucro;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPercentualLucro() {
        return percentualLucro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPercentualLucro(Double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
    
    
}



