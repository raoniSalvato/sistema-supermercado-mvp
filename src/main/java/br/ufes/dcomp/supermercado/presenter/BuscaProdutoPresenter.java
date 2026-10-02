
package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.view.BuscaProdutoView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class BuscaProdutoPresenter {
    private BuscaProdutoView view;
    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;
    private HistoricoPrecoRepository historicoPrecoRepository;
    
    public BuscaProdutoPresenter(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, HistoricoPrecoRepository historicoPrecoRepository){
        this.produtoRepository = produtoRepository;
        this.view = new BuscaProdutoView();
        this.categoriaRepository = categoriaRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        
        this.view.setLocationRelativeTo(null);
        
        estadoInicial();
        configurarListeners();
        buscarProdutos();
        
        this.view.setVisible(true);
    }

    private void estadoInicial() {
        view.getBtnVisualizar().setEnabled(false);
    }

    private void configurarListeners() {
        view.getBtnFechar().addActionListener(e -> view.dispose());
        
        view.getBntBuscar().addActionListener(e -> buscarProdutos());
        
        view.getTabelaProdutos().getSelectionModel().addListSelectionListener(e-> {
            if(!e.getValueIsAdjusting()){
                boolean linhaSelecionada = view.getTabelaProdutos().getSelectedRow() != -1;
                view.getBtnVisualizar().setEnabled(linhaSelecionada);
            }
        });
        
        view.getBtnNovo().addActionListener(e -> {
            new ProdutoPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository, null, false);
        });
        
        view.getBtnVisualizar().addActionListener(e -> {
            int linhaSelecionada = view.getTabelaProdutos().getSelectedRow();
            if (linhaSelecionada != -1) {
                String nomeProdutoSelecionado = (String) view.getTabelaProdutos().getValueAt(linhaSelecionada, 0);
                
                Produto produtoParaAbrir = null;
                for (Produto p : produtoRepository.buscarTodos()) {
                    if (p.getNome().equals(nomeProdutoSelecionado)) {
                        produtoParaAbrir = p;
                        break;
                    }
                }
                
                if (produtoParaAbrir != null) {
                    new ProdutoPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository, produtoParaAbrir, true);
                }
            }
        });
    }
    
    private void buscarProdutos() {
        String termo = view.getTextCbTipoBusca().getText().trim().toLowerCase();
        String filtro = view.getCbTipoBusca().getSelectedItem().toString();
        
        List<Produto> todosProdutos = produtoRepository.buscarTodos();
        List<Produto> produtosFiltrados = new ArrayList<>();
        
        if(termo.isEmpty()){
            produtosFiltrados.addAll(todosProdutos);
        }else{
            for(Produto produto: todosProdutos){
                if(filtro.equals("Nome do produto") && produto.getNome().toLowerCase().contains(termo)){
                    produtosFiltrados.add(produto);
                }else if(filtro.equals("Categoria") && produto.getCategoria().getNome().toLowerCase().contains(termo)){
                    produtosFiltrados.add(produto);
                }
            }
        }
        preencherTabela(produtosFiltrados); 
    }

    private void preencherTabela(List<Produto> produtos) {
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaProdutos().getModel();
        modelo.setNumRows(0); 
        
        for (Produto p : produtos) {
            String margem = (p.getMargemLucroAtual() != null && p.getMargemLucroAtual() > 0) ? String.format("%.2f", p.getMargemLucroAtual()) : "";
            String precoVenda = (p.getPrecoVendaAtual() != null && p.getPrecoVendaAtual() > 0) ? String.format("%.2f", p.getPrecoVendaAtual()) : "";
            
            modelo.addRow(new Object[]{
                p.getNome(),
                String.format("%.2f", p.getPrecoCusto()),
                p.getCategoria().getNome(),
                margem,
                precoVenda
            });
        }
    }
            
}
