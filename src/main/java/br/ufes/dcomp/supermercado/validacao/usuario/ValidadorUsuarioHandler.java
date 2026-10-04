
package br.ufes.dcomp.supermercado.validacao.usuario;

import br.ufes.dcomp.supermercado.model.Usuario;

public abstract class ValidadorUsuarioHandler {
    protected ValidadorUsuarioHandler proximo;
    
    public void setProximo(ValidadorUsuarioHandler proximo){
        this.proximo = proximo;
    }
    
    public void validar(Usuario usuario) throws RuntimeException{
        realizarValidacao(usuario);
        
        if(proximo != null){
            proximo.validar(usuario);
        }
    }

    protected abstract void realizarValidacao(Usuario usuario) throws RuntimeException;
    
    
}
