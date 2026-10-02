
package br.ufes.dcomp.supermercado.validacao.produto;

public abstract class ValidadorProdutoHandler {
    protected ValidadorProdutoHandler proximo;
    
    public void setProximo(ValidadorProdutoHandler proximo){
        this.proximo = proximo;
    }
    
    public void validar(String nome, String precoTexto, int indexCategoria) throws Exception{
        realizarValidacao(nome, precoTexto, indexCategoria);
        
        if(proximo != null){
            proximo.validar(nome, precoTexto, indexCategoria);
        }
    }
    
    protected abstract void realizarValidacao(String nome, String precoTexto, int indexCategoria) throws Exception;
}
