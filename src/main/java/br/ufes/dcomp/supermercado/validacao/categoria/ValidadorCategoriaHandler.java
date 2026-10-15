package br.ufes.dcomp.supermercado.validacao.categoria;

import br.ufes.dcomp.supermercado.model.Categoria;

public abstract class ValidadorCategoriaHandler {
    
    protected ValidadorCategoriaHandler proximo;

    public void setProximo(ValidadorCategoriaHandler proximo) {
        this.proximo = proximo;
    }

    public void validar(Categoria categoria) {
        if(this.seAplica(categoria)){
            this.realizarValidacao(categoria);
        }
        
        if (proximo != null) {
            proximo.validar(categoria);
        }
    }

    protected abstract void realizarValidacao(Categoria categoria) throws RuntimeException;

    protected abstract boolean seAplica(Categoria categoria) throws RuntimeException;
}