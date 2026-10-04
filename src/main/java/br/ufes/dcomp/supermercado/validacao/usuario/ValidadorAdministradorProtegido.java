package br.ufes.dcomp.supermercado.validacao.usuario;

import br.ufes.dcomp.supermercado.model.PerfilUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;

public class ValidadorAdministradorProtegido extends ValidadorUsuarioHandler {
    @Override
    protected void realizarValidacao(Usuario usuario) throws RuntimeException {
        if (usuario.getPerfil() == PerfilUsuario.ADMINISTRADOR) {
            throw new RuntimeException("O utilizador Administrador não pode ser excluído nem desabilitado.");
        }
    }
}