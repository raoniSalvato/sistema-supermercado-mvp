package br.ufes.dcomp.supermercado.model;

import java.time.LocalDate;

public class HistoricoPreco {
    private Long id;
    private LocalDate dataCalculo;
    private Double percentualLucro;
    private Double precoVendaResultante;
    private Produto produto;
    
    public HistoricoPreco(LocalDate dataCalculo, Double percentualLucro, Double precoVendaResultante, Produto produto) {
        this.dataCalculo = dataCalculo;
        this.percentualLucro = percentualLucro;
        this.precoVendaResultante = precoVendaResultante;
        this.produto = produto;
    }

    public LocalDate getDataCalculo() {
        return dataCalculo;
    }

    public Double getPercentualLucro() {
        return percentualLucro;
    }

    public Double getPrecoVendaResultante() {
        return precoVendaResultante;
    }

    public Produto getProduto() {
        return produto;
    }

    public Long getId() {
        return id;
    }


    
    
}
