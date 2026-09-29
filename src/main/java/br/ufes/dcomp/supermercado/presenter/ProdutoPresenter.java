
package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.view.ProdutoView;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutoPresenter {

    private ProdutoView view;
    private ProdutoRepository produtoRepo;
    private CategoriaRepository categoriaRepo;
    private Produto produtoAtual;
    private List<Categoria> listaCategorias; 
    private HistoricoPrecoPresenter historicoPrecoPresenter;
    private HistoricoPrecoRepository historicoPrecoRepository;
    
    public ProdutoPresenter(ProdutoRepository produtoRepo, CategoriaRepository categoriaRepo, HistoricoPrecoRepository historicoRepo, Produto produto, boolean modoVisualizacao) {
        this.produtoRepo = produtoRepo;
        this.categoriaRepo = categoriaRepo;
        this.produtoAtual = produto;
        this.view = new ProdutoView();
        this.historicoPrecoRepository = historicoRepo;
        
        this.view.setLocationRelativeTo(null);
        
        carregarCategorias();
        configurarListeners();
        
        if (modoVisualizacao && produto != null) {
            preencherFormulario();
            estadoVisualizacao();
        } else {
            estadoInclusaoEdicao();
            if (produto != null) {
                preencherFormulario(); 
            }
        }       
        this.view.setVisible(true);
    }

    private void carregarCategorias() {
        view.getCbTipoCategoriaProduto().removeAllItems();
        listaCategorias = categoriaRepo.buscarTodas();
        
        for (Categoria c : listaCategorias) {
            view.getCbTipoCategoriaProduto().addItem(c.getNome());
        }
    }

    private void preencherFormulario() {
        view.getTextNomeProduto().setText(produtoAtual.getNome());
        view.getTextPrecoCusto().setText(String.valueOf(produtoAtual.getPrecoCusto()));
        
        view.getCbTipoCategoriaProduto().setSelectedItem(produtoAtual.getCategoria().getNome());
        
        if (produtoAtual.getMargemLucroAtual() != null && produtoAtual.getMargemLucroAtual() > 0) {
            view.getTextMargemLucro().setText(String.format("%.2f", produtoAtual.getMargemLucroAtual()));
            view.getTextPrecoVenda().setText(String.format("%.2f", produtoAtual.getPrecoVendaAtual()));
        } else {
            view.getTextMargemLucro().setText("");
            view.getTextPrecoVenda().setText("");
        }
    }
    
    private void estadoVisualizacao() {
        view.getTextNomeProduto().setEnabled(false);
        view.getTextPrecoCusto().setEnabled(false);
        view.getCbTipoCategoriaProduto().setEnabled(false);
        
        view.getTextMargemLucro().setEnabled(false);
        view.getTextPrecoVenda().setEnabled(false);
        
        view.getBtnSalvar().setVisible(false);
        view.getBtnCancelar().setVisible(false);
        
        view.getBtnEditar().setVisible(true);
        view.getBtnVisualizarHistoricoPrecos().setVisible(true);
        view.getBtnFechar().setVisible(true);
    }

    private void estadoInclusaoEdicao() {
        view.getTextNomeProduto().setEnabled(true);
        view.getTextPrecoCusto().setEnabled(true);
        view.getCbTipoCategoriaProduto().setEnabled(true);

        view.getTextMargemLucro().setEnabled(false);
        view.getTextPrecoVenda().setEnabled(false);
        
        view.getBtnSalvar().setVisible(true);
        view.getBtnCancelar().setVisible(true);
        
        view.getBtnEditar().setVisible(false);
        view.getBtnVisualizarHistoricoPrecos().setVisible(false);
        view.getBtnFechar().setVisible(false); 
    }

    private void configurarListeners() {
        view.getBtnFechar().addActionListener(e -> view.dispose());
        
        view.getBtnCancelar().addActionListener(e -> {
            if (produtoAtual == null) {
                view.dispose(); 
            } else {
                preencherFormulario();
                estadoVisualizacao(); 
            }
        });
        
        view.getBtnEditar().addActionListener(e -> estadoInclusaoEdicao());
        
        view.getBtnSalvar().addActionListener(e -> salvarProduto());
   
        view.getBtnVisualizarHistoricoPrecos().addActionListener(e -> {
             new HistoricoPrecoPresenter(historicoPrecoRepository, produtoAtual); 
        });
    }

    private void salvarProduto() {
        try {
            String nome = view.getTextNomeProduto().getText();
            Double precoCusto = Double.parseDouble(view.getTextPrecoCusto().getText().replace(",", "."));
            
            int indexSelecionado = view.getCbTipoCategoriaProduto().getSelectedIndex();
            Categoria categoriaSelecionada = listaCategorias.get(indexSelecionado);
            
            if (produtoAtual == null) {
                Produto novo = new Produto(null, nome, precoCusto, categoriaSelecionada);
                produtoRepo.salvar(novo);
                this.produtoAtual = novo;
            } else {
                produtoAtual.setNome(nome);
                produtoAtual.setPrecoCusto(precoCusto);
                produtoAtual.setCategoria(categoriaSelecionada);
            }
            
            JOptionPane.showMessageDialog(view, "Produto salvo com sucesso!");
            estadoVisualizacao();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Verifique os dados informados. Preço deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
