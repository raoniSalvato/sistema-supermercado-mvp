
package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import br.ufes.dcomp.supermercado.servico.CategoriaServico;
import br.ufes.dcomp.supermercado.view.TelaPrincipalView;

public class TelaPrincipalPresenter {
    private TelaPrincipalView view;
    private CategoriaRepository categoriaRepository;
    private CategoriaServico categoriaServico;
    private ProdutoRepository produtoRepository;
    private HistoricoPrecoRepository historicoPrecoRepository;
    private CalculoPrecoServico calculoPrecoServico;
    
    public TelaPrincipalPresenter(CategoriaRepository categoriaRepository, CategoriaServico categoriaServico, ProdutoRepository produtoRepository, HistoricoPrecoRepository historicoPrecoRepository, CalculoPrecoServico calculoPrecoServico){
        this.categoriaRepository = categoriaRepository;
        this.categoriaServico = categoriaServico;
        this.view = new TelaPrincipalView();
        this.produtoRepository = produtoRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.calculoPrecoServico = calculoPrecoServico;
        
        this.view.setLocationRelativeTo(null);
        configurarListeners();
        this.view.setVisible(true);
    }

    private void configurarListeners() {
    
        this.view.getMenuCategorias().addActionListener(e -> {
            new CategoriaPresenter(categoriaRepository, categoriaServico);
        });
        
        this.view.getMenuBuscarProdutos().addActionListener(e -> {
            new BuscaProdutoPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository);
        });
        
        this.view.getMenuCalcularMargem().addActionListener(e -> {
            new CalculoPrecoPresenter(calculoPrecoServico, produtoRepository);
        });
        
        this.view.getMenuIncluirProdutos().addActionListener(e -> {
            // Passa null (porque é um produto novo) e false (porque NÃO é modo de visualização)
            new ProdutoPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository, null, false);
        });
        
    }
    
    
}
