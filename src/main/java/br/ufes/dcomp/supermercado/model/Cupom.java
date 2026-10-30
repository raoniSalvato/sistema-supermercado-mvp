
package br.ufes.dcomp.supermercado.model;

import java.time.LocalDateTime;

public class Cupom {

    private String codigo;
    private double percentualFixoDesconto;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public Cupom(String codigo, double percentualFixoDesconto, LocalDateTime dataInicio, LocalDateTime dataFim){
        this.codigo = codigo;
        this.percentualFixoDesconto = percentualFixoDesconto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getCodigo(){
        return codigo;
    }

    public double getPercentualFixoDesconto(){
        return percentualFixoDesconto;
    }

    public boolean estaValido(LocalDateTime dataAtual){
        return !dataAtual.isBefore(dataInicio) && !dataAtual.isAfter(dataFim);
    }
}
