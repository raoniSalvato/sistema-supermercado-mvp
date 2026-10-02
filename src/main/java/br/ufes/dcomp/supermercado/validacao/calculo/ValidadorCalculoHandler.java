package br.ufes.dcomp.supermercado.validacao.calculo;

import java.time.LocalDate;

public abstract class ValidadorCalculoHandler {
    
    protected ValidadorCalculoHandler proximo;

    public void setProximo(ValidadorCalculoHandler proximo) {
        this.proximo = proximo;
    }

    public void validar(LocalDate dataAtual, LocalDate dataUltimoCalculo) {
        realizarValidacao(dataAtual, dataUltimoCalculo);
        
        if (proximo != null) {
            proximo.validar(dataAtual, dataUltimoCalculo);
        }
    }

    protected abstract void realizarValidacao(LocalDate dataAtual, LocalDate dataUltimoCalculo) throws RuntimeException;
}