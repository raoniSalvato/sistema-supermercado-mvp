
package br.ufes.dcomp.supermercado.validacao.produto;

public class ValidadorNome extends ValidadorProdutoHandler{
    
    @Override
    protected void realizarValidacao(String nome, String precoTexto, int indexCategoria) throws Exception{
        if(nome == null || nome.trim().isEmpty()){
            throw new Exception("O nome do produto é obrigatório.");
        }
    }
    
}
