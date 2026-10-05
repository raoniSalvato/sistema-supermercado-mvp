
package br.ufes.dcomp.supermercado.validacao.cliente;

import br.ufes.dcomp.supermercado.model.Cliente;

public class ValidadorDadosObrigatoriosCliente extends ValidadorClienteHandler{
    @Override
    protected void realizarValidacao(Cliente cliente) throws RuntimeException{
        if(nuloOuVazio(cliente.getNome())){
            throw new RuntimeException("O nome do cliente é obrigatório.");
        }
        if(nuloOuVazio(cliente.getLogradouro())){
            throw new RuntimeException("O logradouro é obrigatório.");
        }
        if(nuloOuVazio(cliente.getBairro())){
            throw new RuntimeException("O bairro é obrigatório.");
        }
        if(nuloOuVazio(cliente.getCidade())){
            throw new RuntimeException("A cidade é obrigatório.");
        }
        if(nuloOuVazio(cliente.getUf())){
            throw new RuntimeException("A UF é obrigatório.");
        }
    }

    private boolean nuloOuVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
    
}
