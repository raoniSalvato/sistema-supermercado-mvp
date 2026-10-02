
package br.ufes.dcomp.supermercado.validacao.produto;

public class ValidadorPreco extends ValidadorProdutoHandler {
    @Override
    protected void realizarValidacao(String nome, String precoTexto, int indexCategoria) throws Exception {
        if (precoTexto == null || precoTexto.trim().isEmpty()) {
            throw new Exception("O preço de custo é obrigatório.");
        }
        try {
            double preco = Double.parseDouble(precoTexto.replace(",", "."));
            if (preco <= 0) {
                throw new Exception("O preço de custo deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Informe um preço de custo numérico válido.");
        }
    }
}
