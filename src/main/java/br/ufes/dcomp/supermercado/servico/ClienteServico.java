
package br.ufes.dcomp.supermercado.servico;

import br.ufes.dcomp.supermercado.model.Cliente;
import br.ufes.dcomp.supermercado.repositorio.ClienteRepository;
import br.ufes.dcomp.supermercado.validacao.cliente.ValidadorClienteHandler;
import br.ufes.dcomp.supermercado.validacao.cliente.ValidadorDadosObrigatoriosCliente;
import java.util.List;

public class ClienteServico {
    private ClienteRepository clienteRepository;
    
    public ClienteServico(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }
    
    public void salvar(Cliente cliente){
        ValidadorClienteHandler validadorDadosObrigatorios = new ValidadorDadosObrigatoriosCliente();
        
        validadorDadosObrigatorios.validar(cliente);
        
        clienteRepository.salvar(cliente);
    }
    
    public void excluir(Cliente cliente){
        clienteRepository.excluir(cliente);
    }
    
    public List<Cliente> buscarTodos(){
        return clienteRepository.buscarTodos();
    }
    
}
