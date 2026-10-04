package br.ufes.dcomp.supermercado.presenter.state.cliente;

import br.ufes.dcomp.supermercado.presenter.ClientePresenter;

public class ClienteEstadoEdicao extends ClientePresenterState {

    public ClienteEstadoEdicao(ClientePresenter presenter) {
        super(presenter);
    }

    @Override
    protected void inicializarEstado() {
        presenter.getView().getLblModo().setText("Modo: Edição");
        presenter.habilitarCampos(true);
        
        presenter.getView().getBtnNovo().setEnabled(false);
        presenter.getView().getBtnEditar().setEnabled(false);
        presenter.getView().getBtnExcluir().setEnabled(false);
        presenter.getView().getBtnSalvar().setEnabled(true);
        presenter.getView().getBtnCancelar().setEnabled(true);
    }

    @Override
    public void salvar() {
        presenter.executarSalvamento(true); // true = modo de edição
        presenter.setEstado(new ClienteEstadoVisualizacao(presenter));
    }

    @Override
    public void cancelar() {
        presenter.preencherFormularioComSelecionado(); // Restaura os dados originais
        presenter.setEstado(new ClienteEstadoVisualizacao(presenter));
    }
}