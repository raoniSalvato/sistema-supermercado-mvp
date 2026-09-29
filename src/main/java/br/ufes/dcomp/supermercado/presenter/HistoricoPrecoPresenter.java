
package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.HistoricoPreco;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.view.HistoricoPrecoView;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class HistoricoPrecoPresenter {
    private HistoricoPrecoView view;
    private HistoricoPrecoRepository historicoRepository;
    private Produto produto;

    public HistoricoPrecoPresenter(HistoricoPrecoRepository historicoRepository, Produto produto) {
        this.historicoRepository = historicoRepository;
        this.produto = produto;
        this.view = new HistoricoPrecoView();
        
        this.view.setLocationRelativeTo(null); // Centraliza a tela
        
        configurarEstadoInicial();
        carregarTabela();
        configurarListeners();
        
        this.view.setVisible(true);
    }

    private void configurarEstadoInicial() {
        view.getTextProduto().setText(produto.getNome());
        view.getTextProduto().setEnabled(false);
        
        view.getTextCategoria().setText(produto.getCategoria().getNome());
        view.getTextCategoria().setEnabled(false);
    }

    private void carregarTabela() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaDadosProduto().getModel();
        modelo.setNumRows(0); 
        
        List<HistoricoPreco> historicos = historicoRepository.buscarPorProduto(produto);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (HistoricoPreco historicoPreco : historicos) {
            modelo.addRow(new Object[]{
                historicoPreco.getDataCalculo().format(formatador),
                String.format("%.2f", historicoPreco.getPercentualLucro()),
                String.format("%.2f", historicoPreco.getPrecoVendaResultante())
            });
        }
    }

    private void configurarListeners() {
        
        view.getBtnFechar().addActionListener(e -> view.dispose());
    }
}
