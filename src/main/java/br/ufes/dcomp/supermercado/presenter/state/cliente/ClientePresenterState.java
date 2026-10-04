package br.ufes.dcomp.supermercado.presenter.state.cliente;

import br.ufes.dcomp.supermercado.presenter.ClientePresenter;

public abstract class ClientePresenterState {
    
    protected ClientePresenter presenter;

    public ClientePresenterState(ClientePresenter presenter) {
        this.presenter = presenter;
        inicializarEstado();
    }

    protected abstract void inicializarEstado();

    public void novo() {
        throw new RuntimeException("Operação 'Novo' não permitida neste estado.");
    }

    public void editar() {
        throw new RuntimeException("Operação 'Editar' não permitida neste estado.");
    }

    public void excluir() {
        throw new RuntimeException("Operação 'Excluir' não permitida neste estado.");
    }

    public void salvar() {
        throw new RuntimeException("Operação 'Salvar' não permitida neste estado.");
    }

    public void cancelar() {
        throw new RuntimeException("Operação 'Cancelar' não permitida neste estado.");
    }
}