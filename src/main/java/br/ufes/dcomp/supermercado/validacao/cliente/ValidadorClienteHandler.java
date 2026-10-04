
package br.ufes.dcomp.supermercado.validacao.cliente;

import br.ufes.dcomp.supermercado.model.Cliente;

public abstract class ValidadorClienteHandler {
    protected ValidadorClienteHandler proximo;
    
    public void setProximo(ValidadorClienteHandler proximo){
        this.proximo = proximo;
    }
    
    public void validar(Cliente cliente) throws RuntimeException{
        realizarValidacao(cliente);
        
        if(proximo != null){
            proximo.validar(cliente);
        }
    }

    protected abstract void realizarValidacao(Cliente cliente) throws RuntimeException;
}
