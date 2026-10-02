package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.presenter.state.EdicaoState;
import br.ufes.dcomp.supermercado.presenter.state.InclusaoState;
import br.ufes.dcomp.supermercado.presenter.state.ProdutoPresenterState;
import br.ufes.dcomp.supermercado.presenter.state.VisualizacaoState;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.validacao.produto.ValidadorCategoria;
import br.ufes.dcomp.supermercado.validacao.produto.ValidadorNome;
import br.ufes.dcomp.supermercado.validacao.produto.ValidadorPreco;
import br.ufes.dcomp.supermercado.validacao.produto.ValidadorProdutoHandler;
import br.ufes.dcomp.supermercado.view.ProdutoView;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutoPresenter {

    private ProdutoView view;
    private ProdutoRepository produtoRepo;
    private CategoriaRepository categoriaRepo;
    private HistoricoPrecoRepository historicoPrecoRepository;
    
    private Produto produtoAtual;
    private List<Categoria> listaCategorias; 
    
    private ProdutoPresenterState estado;
    
    public ProdutoPresenter(ProdutoRepository produtoRepo, CategoriaRepository categoriaRepo, HistoricoPrecoRepository historicoRepo, Produto produto, boolean modoVisualizacao) {
        this.produtoRepo = produtoRepo;
        this.categoriaRepo = categoriaRepo;
        this.historicoPrecoRepository = historicoRepo;
        this.produtoAtual = produto;
        this.view = new ProdutoView();
        
        this.view.setLocationRelativeTo(null);
        
        carregarCategorias();
        configurarListeners();
        
        // Define o estado inicial da interface
        if (produto == null) {
            setEstado(new InclusaoState(this));
        } else if (modoVisualizacao) {
            preencherFormulario();
            setEstado(new VisualizacaoState(this));
        } else {
            preencherFormulario();
            setEstado(new EdicaoState(this));
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

    public void preencherFormulario() {
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

    private void configurarListeners() {
        view.getBtnFechar().addActionListener(e -> estado.fechar());
        view.getBtnCancelar().addActionListener(e -> estado.cancelar());
        view.getBtnEditar().addActionListener(e -> estado.editar());
        view.getBtnSalvar().addActionListener(e -> estado.salvar());
        
        view.getBtnVisualizarHistoricoPrecos().addActionListener(e -> {
             new HistoricoPrecoPresenter(historicoPrecoRepository, produtoAtual); 
        });
    }

   public void executarSalvar() {
        try {
            String nome = view.getTextNomeProduto().getText();
            
            String precoTexto = view.getTextPrecoCusto().getText(); 
            int indexSelecionado = view.getCbTipoCategoriaProduto().getSelectedIndex();
            
            ValidadorProdutoHandler validadorNome = new ValidadorNome();
            ValidadorProdutoHandler validadorPreco = new ValidadorPreco();
            ValidadorProdutoHandler validadorCategoria = new ValidadorCategoria();
            
            validadorNome.setProximo(validadorPreco);
            validadorPreco.setProximo(validadorCategoria);
            
            validadorNome.validar(nome, precoTexto, indexSelecionado);
            
            Double precoCusto = Double.parseDouble(precoTexto.replace(",", "."));
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
            setEstado(new VisualizacaoState(this));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        }
    }

    public ProdutoView getView() {
        return view;
    }

    public void setEstado(ProdutoPresenterState estado) {
        this.estado = estado;
    }
}