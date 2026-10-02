package br.ufes.dcomp.supermercado.validacao.categoria;

import br.ufes.dcomp.supermercado.model.Categoria;

public class ValidadorNomeCategoria extends ValidadorCategoriaHandler {
    @Override
    protected void realizarValidacao(Categoria categoria) throws RuntimeException {
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome da categoria é obrigatório e não deve ser composto apenas por espaços.");
        }
    }
}