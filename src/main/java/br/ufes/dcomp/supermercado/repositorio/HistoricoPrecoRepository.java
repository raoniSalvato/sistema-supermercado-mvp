
package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.HistoricoPreco;
import br.ufes.dcomp.supermercado.model.Produto;
import java.util.ArrayList;
import java.util.List;


public class HistoricoPrecoRepository {
    private List<HistoricoPreco> historicos;
    
    public HistoricoPrecoRepository(){
        this.historicos = new ArrayList<>();
    }
    
    public void salvar(HistoricoPreco historicoPreco){
        this.historicos.add(historicoPreco);
    }
    
    public List<HistoricoPreco> buscarPorProduto(Produto produto){
        List<HistoricoPreco> historicosDoProduto = new ArrayList<>();
        
        for(HistoricoPreco historicoPreco: this.historicos){
            if(historicoPreco.getProduto().getId().equals(produto.getId())){
                historicosDoProduto.add(historicoPreco);
            }
        }
        
        return historicosDoProduto;
    }
}
