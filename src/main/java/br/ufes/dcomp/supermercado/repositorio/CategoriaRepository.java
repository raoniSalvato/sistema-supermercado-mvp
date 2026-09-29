
package br.ufes.dcomp.supermercado.repositorio;

import java.util.ArrayList;
import java.util.List;
import br.ufes.dcomp.supermercado.model.Categoria;

public class CategoriaRepository {
    private List<Categoria> categorias;
    private Long proximoId;
    
    public CategoriaRepository(){
        this.categorias = new ArrayList<>();
        this.proximoId = 1L;
    }
    
    public void salvar(Categoria categoria){
        if(categoria.getId() == null){
            categoria.setId(proximoId);
            this.categorias.add(categoria);
            proximoId++;
        }
    }
    
    public List<Categoria> buscarTodas(){
        return categorias;
    }
    
    public void excluir(Categoria categoria){
        this.categorias.remove(categoria);
    }    
    
}
