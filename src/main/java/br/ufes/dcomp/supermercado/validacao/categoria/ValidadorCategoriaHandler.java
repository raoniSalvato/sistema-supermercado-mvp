package br.ufes.dcomp.supermercado.validacao.categoria;

import br.ufes.dcomp.supermercado.model.Categoria;

public abstract class ValidadorCategoriaHandler {
    
    protected ValidadorCategoriaHandler proximo;

    public void setProximo(ValidadorCategoriaHandler proximo) {
        this.proximo = proximo;
    }

    public void validar(Categoria categoria) {
        realizarValidacao(categoria);
        
        if (proximo != null) {
            proximo.validar(categoria);
        }
    }

    // Usamos RuntimeException para manter a compatibilidade com o seu Presenter atual
    protected abstract void realizarValidacao(Categoria categoria) throws RuntimeException;
}