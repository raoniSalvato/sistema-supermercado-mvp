package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.PerfilUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Long proximoId = 1L;
    
    static {
        Usuario adminDefault = new Usuario(
            proximoId++, 
            "Administrador do Sistema", 
            "admin@pocdelivery.com", 
            "admin", 
            "admin123", 
            PerfilUsuario.ADMINISTRADOR
        );
        usuarios.add(adminDefault);
    }
    
    public UsuarioRepository() {
    }
    
    public void salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(proximoId++);
            usuarios.add(usuario);
        } else {
            atualizar(usuario);
        }
    }

    private void atualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    public void excluir(Usuario usuario) {
        usuarios.removeIf(u -> u.getId().equals(usuario.getId()));
    }

    public List<Usuario> buscarTodos() {
        return new ArrayList<>(usuarios);
    }
    
    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }
  
    public Usuario buscarPorIdentificacao(String identificacao) {
        for (Usuario u : usuarios) {
            if (u.getNomeUsuario().equalsIgnoreCase(identificacao) || 
                u.getEmail().equalsIgnoreCase(identificacao)) {
                return u;
            }
        }
        return null;
    }
}