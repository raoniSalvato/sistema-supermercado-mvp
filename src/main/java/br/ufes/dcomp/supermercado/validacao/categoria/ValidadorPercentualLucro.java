package br.ufes.dcomp.supermercado.validacao.categoria;

import br.ufes.dcomp.supermercado.model.Categoria;

public class ValidadorPercentualLucro extends ValidadorCategoriaHandler {
    @Override
    protected void realizarValidacao(Categoria categoria) throws RuntimeException {
        if (categoria.getPercentualLucro() == null || categoria.getPercentualLucro() < 0) {
            throw new RuntimeException("O percentual de lucro da categoria é obrigatório e deve ser maior ou igual a zero.");
        }
    }
}