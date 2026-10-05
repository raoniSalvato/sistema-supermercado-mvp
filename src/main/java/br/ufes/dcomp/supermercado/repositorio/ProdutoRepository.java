package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    
    private static List<Produto> produtos = new ArrayList<>();
    private static Long proximoId = 1L;
    
    public ProdutoRepository() {
    }
    
    public void salvar(Produto produto){
        if(produto.getId() == null){
            produto.setId(proximoId);
            produtos.add(produto);
            proximoId++;
        }
    }
    
    public List<Produto> buscarTodos(){
        return produtos;
    }
    
    public void excluir(Produto produto){
        produtos.remove(produto);
    }    
}