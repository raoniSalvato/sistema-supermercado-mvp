
package br.ufes.dcomp.supermercado.validacao.usuario;

import br.ufes.dcomp.supermercado.model.Usuario;

public class ValidadorDadosObrigatoriosUsuario extends ValidadorUsuarioHandler{
    @Override
    protected void realizarValidacao(Usuario usuario) throws RuntimeException {
        if (nuloOuVazio(usuario.getNomeCompleto()) || nuloOuVazio(usuario.getEmail()) || 
            nuloOuVazio(usuario.getNomeUsuario()) || nuloOuVazio(usuario.getSenha()) || 
            usuario.getPerfil() == null) {
            throw new RuntimeException("Todos os dados principais (Nome, E-mail, Usuário, Senha e Perfil) são obrigatórios.");
        }
    }

    private boolean nuloOuVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
       
}
