
package br.ufes.dcomp.supermercado.validacao.usuario;

import br.ufes.dcomp.supermercado.model.PerfilUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;

public class ValidadorAssociacaoCliente extends ValidadorUsuarioHandler {
    @Override
    protected void realizarValidacao(Usuario usuario) throws RuntimeException {
        if (usuario.getPerfil() == PerfilUsuario.CLIENTE && usuario.getClienteAssociado() == null) {
            throw new RuntimeException("Para o perfil Cliente, é obrigatório selecionar um Cliente associado.");
        }
    }
}
