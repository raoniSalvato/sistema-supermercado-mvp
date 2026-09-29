package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.Produto;
import java.util.ArrayList;
import java.util.List;


public class ProdutoRepository {
    private List<Produto> produtos;
    private Long proximoId;
    
    public ProdutoRepository() {
        this.produtos = new ArrayList<>();
        this.proximoId = 1L;
    }
    
    public void salvar(Produto produto){
        if(produto.getId() == null){
            produto.setId(proximoId);
            this.produtos.add(produto);
            proximoId++;
        }
    }
    
    public List<Produto> buscarTodos(){
        return produtos;
    }
    
    public void excluir(Produto produto){
        this.produtos.remove(produto);
    }    
    
    
    
}
