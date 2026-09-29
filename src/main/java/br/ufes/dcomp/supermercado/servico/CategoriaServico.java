package br.ufes.dcomp.supermercado.servico;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import java.util.List;

public class CategoriaServico {
    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;
    
    public CategoriaServico(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository){
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }
    
    public void salvar(Categoria categoria){
        validarDadosObrigatorios(categoria);
        validarNomeDuplicado(categoria);
        
        categoriaRepository.salvar(categoria);
    }
    
    public void excluir(Categoria categoria){
        List<Produto> produtos = produtoRepository.buscarTodos();
        
        for (Produto produto : produtos) {
            if (produto.getCategoria().getId().equals(categoria.getId())) {
                throw new RuntimeException("Não é possível excluir: existem produtos associados a esta categoria.");
            }
        }
        
        categoriaRepository.excluir(categoria);
    }

    private void validarDadosObrigatorios(Categoria categoria) {
        if(categoria.getNome() == null || categoria.getNome().trim().isEmpty()){
            throw new RuntimeException("O nome da categoria é obrigatório e não deve ser composto apenas por espaços.");
        }
        if(categoria.getPercentualLucro() == null || categoria.getPercentualLucro() < 0){
            throw new RuntimeException("O percentual de lucro da categoria é obrigatório e deve ser maior ou igual a zero.");
        }
    }

    private void validarNomeDuplicado(Categoria categoria) {
        List<Categoria> categoriasExistentes = categoriaRepository.buscarTodas();
        
        for(Categoria categoriaExistente: categoriasExistentes){
            if(!categoriaExistente.getId().equals(categoria.getId())){
                if(categoriaExistente.getNome().equalsIgnoreCase(categoria.getNome())){
                    throw new RuntimeException("Já existe uma categoria cadastrada com este nome.");
                }
            }
        }
    }
    
}
