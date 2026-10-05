package br.ufes.dcomp.supermercado.presenter.state.usuario;

import br.ufes.dcomp.supermercado.presenter.UsuarioPresenter;

public class UsuarioEstadoEdicao extends UsuarioPresenterState {

    public UsuarioEstadoEdicao(UsuarioPresenter presenter) {
        super(presenter);
    }

    @Override
    protected void inicializarEstado() {
        presenter.getView().getLblModo().setText("Modo: Edição");
        presenter.habilitarCampos(true);
        presenter.validarRegraComboBoxCliente();
        
        presenter.getView().getBtnNovo().setEnabled(false);
        presenter.getView().getBtnEditar().setEnabled(false);
        presenter.getView().getBtnExcluir().setEnabled(false);
        presenter.getView().getBtnHabilitar().setEnabled(false);
        presenter.getView().getBtnDesabilitar().setEnabled(false);
        presenter.getView().getBtnSalvar().setEnabled(true);
        presenter.getView().getBtnCancelar().setEnabled(true);
    }

    @Override
    public void salvar() {
        presenter.executarSalvamento(true);
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
    }

    @Override
    public void cancelar() {
        presenter.preencherFormularioComSelecionado();
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
    }
}