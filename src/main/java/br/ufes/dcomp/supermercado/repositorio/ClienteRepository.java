package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    
    private static List<Cliente> clientes = new ArrayList<>();
    private static Long proximoId = 1L;
    
    public ClienteRepository() {
    }
    
    public void salvar(Cliente cliente){
        if(cliente.getId() == null){
            cliente.setId(proximoId++);
            clientes.add(cliente);
        }else{
            atualizar(cliente);
        }
    }

    private void atualizar(Cliente cliente) {
        for(int i=0; i< clientes.size(); i++){
            if(clientes.get(i).getId().equals(cliente.getId())){
                clientes.set(i, cliente);
                break;
            }
        }
    }
    
    public void excluir(Cliente cliente){
        for(int i=0; i< clientes.size(); i++){
            if(clientes.get(i).getId().equals(cliente.getId())){
                clientes.remove(cliente);
                break;
            }
        }
    }
    
    public List<Cliente> buscarTodos(){
        return new ArrayList<>(clientes);
    }
    
    public Cliente buscarPorId(Long id){
        for(Cliente cliente: clientes){
            if(cliente.getId().equals(id)){
                return cliente;
            }
        }
        return null;
    }
}