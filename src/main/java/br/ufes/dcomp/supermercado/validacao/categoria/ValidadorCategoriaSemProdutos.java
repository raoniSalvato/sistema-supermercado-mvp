package br.ufes.dcomp.supermercado.validacao.categoria;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import java.util.List;

public class ValidadorCategoriaSemProdutos extends ValidadorCategoriaHandler {
    
    private ProdutoRepository produtoRepository;

    public ValidadorCategoriaSemProdutos(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    protected void realizarValidacao(Categoria categoria) throws RuntimeException {
        List<Produto> produtos = produtoRepository.buscarTodos();
        for (Produto produto : produtos) {
            if (produto.getCategoria().getId().equals(categoria.getId())) {
                throw new RuntimeException("Não é possível excluir: existem produtos associados a esta categoria.");
            }
        }
    }
}