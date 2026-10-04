
package br.ufes.dcomp.supermercado.servico;

import br.ufes.dcomp.supermercado.model.StatusUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import br.ufes.dcomp.supermercado.validacao.usuario.ValidadorAdministradorProtegido;
import br.ufes.dcomp.supermercado.validacao.usuario.ValidadorAssociacaoCliente;
import br.ufes.dcomp.supermercado.validacao.usuario.ValidadorDadosObrigatoriosUsuario;
import br.ufes.dcomp.supermercado.validacao.usuario.ValidadorNomeUsuarioUnico;
import br.ufes.dcomp.supermercado.validacao.usuario.ValidadorUsuarioHandler;
import java.util.List;

public class UsuarioServico {
    private UsuarioRepository usuarioRepository;

    public UsuarioServico(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario) {
        ValidadorUsuarioHandler validadorDados = new ValidadorDadosObrigatoriosUsuario();
        ValidadorUsuarioHandler validadorUnico = new ValidadorNomeUsuarioUnico(usuarioRepository);
        ValidadorUsuarioHandler validadorAssociacao = new ValidadorAssociacaoCliente();
        
        validadorDados.setProximo(validadorUnico);
        validadorUnico.setProximo(validadorAssociacao);

        validadorDados.validar(usuario);
        
        usuarioRepository.salvar(usuario);
    }

    public void excluir(Usuario usuario) {
        ValidadorUsuarioHandler validadorAdmin = new ValidadorAdministradorProtegido();
        validadorAdmin.validar(usuario);
        
        usuarioRepository.excluir(usuario);
    }
    
    public void alternarStatus(Usuario usuario, StatusUsuario novoStatus) {
        if (novoStatus == StatusUsuario.DESABILITADO) {
            ValidadorUsuarioHandler validadorAdmin = new ValidadorAdministradorProtegido();
            validadorAdmin.validar(usuario);
        }
        
        usuario.setStatus(novoStatus);
        usuarioRepository.salvar(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.buscarTodos();
    }
}
