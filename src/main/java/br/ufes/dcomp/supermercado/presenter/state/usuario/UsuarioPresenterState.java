package br.ufes.dcomp.supermercado.presenter.state.usuario;

import br.ufes.dcomp.supermercado.presenter.UsuarioPresenter;

public abstract class UsuarioPresenterState {
    
    protected UsuarioPresenter presenter;

    public UsuarioPresenterState(UsuarioPresenter presenter) {
        this.presenter = presenter;
        inicializarEstado();
    }

    protected abstract void inicializarEstado();

    public void novo() { throw new RuntimeException("Operação não permitida neste estado."); }
    public void editar() { throw new RuntimeException("Operação não permitida neste estado."); }
    public void salvar() { throw new RuntimeException("Operação não permitida neste estado."); }
    public void cancelar() { throw new RuntimeException("Operação não permitida neste estado."); }
    public void excluir() { throw new RuntimeException("Operação não permitida neste estado."); }
    public void habilitar() { throw new RuntimeException("Operação não permitida neste estado."); }
    public void desabilitar() { throw new RuntimeException("Operação não permitida neste estado."); }
}