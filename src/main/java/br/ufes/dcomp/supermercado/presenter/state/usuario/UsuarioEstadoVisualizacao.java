package br.ufes.dcomp.supermercado.presenter.state.usuario;

import br.ufes.dcomp.supermercado.model.StatusUsuario;
import br.ufes.dcomp.supermercado.presenter.UsuarioPresenter;

public class UsuarioEstadoVisualizacao extends UsuarioPresenterState {

    public UsuarioEstadoVisualizacao(UsuarioPresenter presenter) {
        super(presenter);
    }

    @Override
    protected void inicializarEstado() {
        presenter.getView().getLblModo().setText("Modo: Visualização");
        presenter.habilitarCampos(false);
        
        boolean temSelecao = presenter.getUsuarioCorrente() != null;
        
        presenter.getView().getBtnNovo().setEnabled(true);
        presenter.getView().getBtnEditar().setEnabled(temSelecao);
        presenter.getView().getBtnExcluir().setEnabled(temSelecao);
        
        if (temSelecao) {
            presenter.getView().getBtnHabilitar().setEnabled(presenter.getUsuarioCorrente().getStatus() == StatusUsuario.DESABILITADO);
            presenter.getView().getBtnDesabilitar().setEnabled(presenter.getUsuarioCorrente().getStatus() == StatusUsuario.HABILITADO);
        } else {
            presenter.getView().getBtnHabilitar().setEnabled(false);
            presenter.getView().getBtnDesabilitar().setEnabled(false);
        }
        
        presenter.getView().getBtnSalvar().setEnabled(false);
        presenter.getView().getBtnCancelar().setEnabled(false);
    }

    @Override
    public void novo() {
        presenter.setEstado(new UsuarioEstadoInclusao(presenter));
    }

    @Override
    public void editar() {
        presenter.setEstado(new UsuarioEstadoEdicao(presenter));
    }

    @Override
    public void excluir() {
        presenter.executarExclusao();
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
    }

    @Override
    public void habilitar() {
        presenter.definirStatus(StatusUsuario.HABILITADO);
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
    }

    @Override
    public void desabilitar() {
        presenter.definirStatus(StatusUsuario.DESABILITADO);
        presenter.setEstado(new UsuarioEstadoVisualizacao(presenter));
    }
}