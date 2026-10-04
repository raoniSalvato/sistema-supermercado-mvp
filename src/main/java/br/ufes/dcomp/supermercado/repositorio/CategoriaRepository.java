package br.ufes.dcomp.supermercado.repositorio;

import java.util.ArrayList;
import java.util.List;
import br.ufes.dcomp.supermercado.model.Categoria;

public class CategoriaRepository {
    
    private static List<Categoria> categorias = new ArrayList<>();
    private static Long proximoId = 1L;
    
    public CategoriaRepository(){
    }
    
    public void salvar(Categoria categoria){
        if(categoria.getId() == null){
            categoria.setId(proximoId);
            categorias.add(categoria); 
            proximoId++;
        }
    }
    
    public List<Categoria> buscarTodas(){
        return categorias;
    }
    
    public void excluir(Categoria categoria){
        categorias.remove(categoria);
    }    
}