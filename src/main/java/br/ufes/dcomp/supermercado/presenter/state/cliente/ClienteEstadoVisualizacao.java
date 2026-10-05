package br.ufes.dcomp.supermercado.presenter.state.cliente;

import br.ufes.dcomp.supermercado.presenter.ClientePresenter;
import br.ufes.dcomp.supermercado.presenter.state.cliente.ClientePresenterState;

public class ClienteEstadoVisualizacao extends ClientePresenterState {

    public ClienteEstadoVisualizacao(ClientePresenter presenter) {
        super(presenter);
    }

    @Override
    protected void inicializarEstado() {
        presenter.getView().getLblModo().setText("Modo: Visualização");
        presenter.habilitarCampos(false);
        
        boolean temSelecao = presenter.getClienteCorrente() != null;
        
        presenter.getView().getBtnNovo().setEnabled(true);
        presenter.getView().getBtnEditar().setEnabled(temSelecao);
        presenter.getView().getBtnExcluir().setEnabled(temSelecao);
        presenter.getView().getBtnSalvar().setEnabled(false);
        presenter.getView().getBtnCancelar().setEnabled(false);
    }

    @Override
    public void novo() {
        presenter.setEstado(new ClienteEstadoInclusao(presenter));
    }

    @Override
    public void editar() {
        presenter.setEstado(new ClienteEstadoEdicao(presenter));
    }

    @Override
    public void excluir() {
        presenter.executarExclusao(); 
        presenter.setEstado(new ClienteEstadoVisualizacao(presenter)); 
    }
}