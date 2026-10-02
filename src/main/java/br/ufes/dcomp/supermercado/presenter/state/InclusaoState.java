package br.ufes.dcomp.supermercado.presenter.state;

import br.ufes.dcomp.supermercado.presenter.ProdutoPresenter;

public class InclusaoState extends ProdutoPresenterState {

    public InclusaoState(ProdutoPresenter presenter) {
        super(presenter);

        this.presenter.getView().getTextNomeProduto().setEnabled(true);
        this.presenter.getView().getTextPrecoCusto().setEnabled(true);
        this.presenter.getView().getCbTipoCategoriaProduto().setEnabled(true);

        this.presenter.getView().getTextMargemLucro().setEnabled(false);
        this.presenter.getView().getTextPrecoVenda().setEnabled(false);
        
        this.presenter.getView().getBtnSalvar().setVisible(true);
        this.presenter.getView().getBtnCancelar().setVisible(true);
        
        this.presenter.getView().getBtnEditar().setVisible(false);
        this.presenter.getView().getBtnVisualizarHistoricoPrecos().setVisible(false);
        this.presenter.getView().getBtnFechar().setVisible(false);
    }

    @Override
    public void salvar() {
        presenter.executarSalvar();
    }

    @Override
    public void cancelar() {
        presenter.getView().dispose();
    }
}
