
package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import br.ufes.dcomp.supermercado.view.CalculoPrecoView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CalculoPrecoPresenter {
    private CalculoPrecoView view;
    private CalculoPrecoServico servico;
    private ProdutoRepository produtoRepository;

    public CalculoPrecoPresenter(CalculoPrecoServico servico, ProdutoRepository produtoRepository) {
        this.servico = servico;
        this.produtoRepository = produtoRepository;
        this.view = new CalculoPrecoView();
        
        this.view.setLocationRelativeTo(null);
        
        preencherComboDatas();
        configurarListeners();
        
        this.view.setVisible(true);
    }

    private void preencherComboDatas() {
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        view.getBcDataCalculo().addItem(hoje.format(formatador));
        
    }

    private void configurarListeners() {
        view.getBtnFechar().addActionListener(e -> view.dispose());
        
        view.getBtnCalcular().addActionListener(e -> executarCalculo());
    }

    private void executarCalculo() {
        try {
             LocalDate dataCalculo = LocalDate.now();
            
            servico.executarCalculoGlobal(dataCalculo);
            
            JOptionPane.showMessageDialog(view, "Cálculo realizado com sucesso para todos os produtos!");
            
            preencherTabela();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherTabela() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTabaelaProdutos().getModel();
        modelo.setNumRows(0);
        
        List<Produto> produtos = produtoRepository.buscarTodos();
        
        for (Produto p : produtos) {
            String margem = (p.getMargemLucroAtual() != null) ? String.format("%.2f", p.getMargemLucroAtual()) : "0,00";
            String precoVenda = (p.getPrecoVendaAtual() != null) ? String.format("%.2f", p.getPrecoVendaAtual()) : "0,00";
            
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
