package br.ufes.dcomp.supermercado.servico;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.validacao.categoria.ValidadorCategoriaHandler;
import br.ufes.dcomp.supermercado.validacao.categoria.ValidadorCategoriaSemProdutos;
import br.ufes.dcomp.supermercado.validacao.categoria.ValidadorNomeCategoria;
import br.ufes.dcomp.supermercado.validacao.categoria.ValidadorNomeDuplicadoCategoria;
import br.ufes.dcomp.supermercado.validacao.categoria.ValidadorPercentualLucro;

public class CategoriaServico {
    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;
    
    public CategoriaServico(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository){
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }
    
    public void salvar(Categoria categoria) {
        ValidadorCategoriaHandler validadorNome = new ValidadorNomeCategoria();
        ValidadorCategoriaHandler validadorLucro = new ValidadorPercentualLucro();
        ValidadorCategoriaHandler validadorDuplicado = new ValidadorNomeDuplicadoCategoria(categoriaRepository);

        validadorNome.setProximo(validadorLucro);
        validadorLucro.setProximo(validadorDuplicado);
        
        validadorNome.validar(categoria);
        
        categoriaRepository.salvar(categoria);
    }
    
    public void excluir(Categoria categoria) {
        ValidadorCategoriaHandler validadorSemProdutos = new ValidadorCategoriaSemProdutos(produtoRepository);  
        validadorSemProdutos.validar(categoria);
       
        categoriaRepository.excluir(categoria);
    }
}