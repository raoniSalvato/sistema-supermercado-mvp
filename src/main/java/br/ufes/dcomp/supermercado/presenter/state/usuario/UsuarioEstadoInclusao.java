package br.ufes.dcomp.supermercado.presenter.state.usuario;

import br.ufes.dcomp.supermercado.model.StatusUsuario;
import br.ufes.dcomp.supermercado.presenter.UsuarioPresenter;

public class UsuarioEstadoInclusao extends UsuarioPresenterState {

    public UsuarioEstadoInclusao(UsuarioPresenter presenter) {
        super(presenter);
    }

    @Override
    protected void inicializarEstado() {
        presenter.getView().getLblModo().setText("Modo: Inclusão");
        presenter.setUsuarioCorrente(null);
        
        presenter.limparCampos();
        presenter.getView().getTextStatus().setText(StatusUsuario.HABILITADO.name());
        presenter.getView().getTblUsuarios().clearSelection();
        
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
        presenter.executarSalvamento(false);
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
        presenter.limparCampos();
    }
}