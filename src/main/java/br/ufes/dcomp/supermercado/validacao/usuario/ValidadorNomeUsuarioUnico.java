
package br.ufes.dcomp.supermercado.validacao.usuario;

import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import java.util.List;

public class ValidadorNomeUsuarioUnico extends ValidadorUsuarioHandler{
    private UsuarioRepository repository;

    public ValidadorNomeUsuarioUnico(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void realizarValidacao(Usuario usuario) throws RuntimeException {
        List<Usuario> existentes = repository.buscarTodos();
        for (Usuario u : existentes) {
            if (!u.getId().equals(usuario.getId()) && u.getNomeUsuario().equalsIgnoreCase(usuario.getNomeUsuario())) {
                throw new RuntimeException("O nome de usuário informado já está a ser utilizado por outro registo.");
            }
        }
    }
}
