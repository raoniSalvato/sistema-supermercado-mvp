
package br.ufes.dcomp.supermercado.model;

public class Usuario {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String nomeUsuario;
    private String senha;
    private PerfilUsuario perfil;
    private StatusUsuario status;
    private Cliente clienteAssociado;
    
    public Usuario(Long id, String nomeCompleto, String email, String nomeUsuario, String senha, PerfilUsuario perfil) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.perfil = perfil;
        this.status = StatusUsuario.HABILITADO; 
    }

    public Long getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public Cliente getClienteAssociado() {
        return clienteAssociado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }

    public void setClienteAssociado(Cliente clienteAssociado) {
        this.clienteAssociado = clienteAssociado;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nomeCompleto=" + nomeCompleto + '}';
    }
    
    
    
}
