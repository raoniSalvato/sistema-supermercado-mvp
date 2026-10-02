package br.ufes.dcomp.supermercado.presenter.state;

import br.ufes.dcomp.supermercado.presenter.ProdutoPresenter;

public class VisualizacaoState extends ProdutoPresenterState{
    
    public VisualizacaoState(ProdutoPresenter presenter) {
        super(presenter);
        
        this.presenter.getView().getTextNomeProduto().setEnabled(false);
        this.presenter.getView().getTextPrecoCusto().setEnabled(false);
        this.presenter.getView().getCbTipoCategoriaProduto().setEnabled(false);
        this.presenter.getView().getTextMargemLucro().setEnabled(false);
        this.presenter.getView().getTextPrecoVenda().setEnabled(false);
        
        this.presenter.getView().getBtnSalvar().setVisible(false);
        this.presenter.getView().getBtnCancelar().setVisible(false);
        
        this.presenter.getView().getBtnEditar().setVisible(true);
        this.presenter.getView().getBtnVisualizarHistoricoPrecos().setVisible(true);
        this.presenter.getView().getBtnFechar().setVisible(true);
    }

    @Override
    public void editar() {
        presenter.setEstado(new EdicaoState(presenter));
    }

    @Override
    public void fechar() {
        presenter.getView().dispose();
    }
}
