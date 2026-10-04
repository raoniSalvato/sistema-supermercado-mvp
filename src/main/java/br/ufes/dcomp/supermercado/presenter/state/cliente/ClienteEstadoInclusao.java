package br.ufes.dcomp.supermercado.presenter.state.cliente;

import br.ufes.dcomp.supermercado.presenter.ClientePresenter;
import br.ufes.dcomp.supermercado.model.TipoCliente;
public class ClienteEstadoInclusao extends ClientePresenterState {

    public ClienteEstadoInclusao(ClientePresenter presenter) {
        super(presenter);
    }

    @Override
    protected void inicializarEstado() {
        presenter.getView().getLblModo().setText("Modo: Inclusão");
        presenter.setClienteCorrente(null);
        
        presenter.limparCampos();
        presenter.getView().getTextTipoCliente().setText(TipoCliente.PRATA.name());
        presenter.getView().getTextTotalCompras().setText("0.0");
        presenter.getView().getTblClientes().clearSelection();
        
        presenter.habilitarCampos(true);
        
        presenter.getView().getBtnNovo().setEnabled(false);
        presenter.getView().getBtnEditar().setEnabled(false);
        presenter.getView().getBtnExcluir().setEnabled(false);
        presenter.getView().getBtnSalvar().setEnabled(true);
        presenter.getView().getBtnCancelar().setEnabled(true);
    }

    @Override
    public void salvar() {
        presenter.executarSalvamento(false);
        presenter.setEstado(new ClienteEstadoVisualizacao(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new ClienteEstadoVisualizacao(presenter));
    }
}