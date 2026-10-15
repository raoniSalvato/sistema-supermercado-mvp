package br.ufes.dcomp.supermercado.validacao.produto;

public class ValidadorCategoria extends ValidadorProdutoHandler {
    @Override
    protected void realizarValidacao(String nome, String precoTexto, int indexCategoria) throws Exception {
        if (indexCategoria < 0) {
            throw new Exception("Você deve selecionar uma categoria válida.");
        }
    }
    
    @Override
    protected boolean seAplica(String nome, String precoTexto, int indexCategoria) {
        return true; // A validação de categoria sempre deve ser executada
    }
}