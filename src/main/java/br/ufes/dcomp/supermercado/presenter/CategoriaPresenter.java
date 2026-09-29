
package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.servico.CategoriaServico;
import br.ufes.dcomp.supermercado.view.CategoriaView;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class CategoriaPresenter {
    private CategoriaView view;
    private CategoriaRepository repository;
    private CategoriaServico servico;
    private Categoria categoriaSelecionada;
    
    public CategoriaPresenter(CategoriaRepository repository, CategoriaServico servico){
        this.repository = repository;
        this.servico = servico;
        this.view = new CategoriaView();
        
        configurarBotoes();
        carregarTabela();
        estadoVisualizacao();
        
        this.view.setVisible(true);
    }

    private void configurarBotoes() {
        view.getBtnNovo().addActionListener(e -> estadoInclusao());
        view.getBtnCancelar().addActionListener(e -> estadoVisualizacao());
        view.getBtnFechar().addActionListener(e -> view.dispose());
        view.getBtnExcluir().addActionListener(e -> excluirCategoria());
        
        view.getBtnSalvar().addActionListener(e -> salvarCategoria());
        
       
        view.getBtnEditar().addActionListener(e -> {
            view.getJblModo().setText("Modo: Edição");
            view.getTextCategoria().setEnabled(true);
            view.getTextPercentualLucro().setEnabled(true);
            view.getBtnEditar().setEnabled(false);
            view.getBtnExcluir().setEnabled(false);
            view.getBtnSalvar().setEnabled(true);
            view.getBtnCancelar().setEnabled(true);
        });
        
        view.getTableCategorias().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });
    }

    private void carregarTabela() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTableCategorias().getModel();
        modelo.setNumRows(0);
        
        List<Categoria> categorias = repository.buscarTodas();
        for(Categoria categoria: categorias){
            modelo.addRow(new Object[]{
               categoria.getNome(), categoria.getPercentualLucro()
            });
        }   
    }
    
    public void preencherFormulario(){
        int linhaSelecionada = view.getTableCategorias().getSelectedRow();
        
        if(linhaSelecionada != -1){
            List<Categoria> categorias = repository.buscarTodas();
            this.categoriaSelecionada = categorias.get(linhaSelecionada);
        
            view.getTextCategoria().setText(this.categoriaSelecionada.getNome());
            view.getTextPercentualLucro().setText(String.valueOf(this.categoriaSelecionada.getPercentualLucro()));
        
            estadoVisualizacao();
        }
    }

    private void estadoVisualizacao() {
        view.getJblModo().setText("Modo: Visualização");
        view.getTextCategoria().setEnabled(false);
        view.getTextPercentualLucro().setEnabled(false);
        
        view.getBtnNovo().setEnabled(true);
        
        view.getBtnEditar().setEnabled(categoriaSelecionada != null); 
        view.getBtnExcluir().setEnabled(categoriaSelecionada != null);
        
        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
    }
    
    private void estadoInclusao() {
    this.categoriaSelecionada = null;
    view.getJblModo().setText("Modo: Inclusão");
        
    view.getTextCategoria().setEnabled(true);
    view.getTextPercentualLucro().setEnabled(true);
    view.getTextCategoria().setText("");
    view.getTextPercentualLucro().setText("");
        
    view.getBtnNovo().setEnabled(false);
    view.getBtnEditar().setEnabled(false);
    view.getBtnExcluir().setEnabled(false);
        
    view.getBtnSalvar().setEnabled(true);
    view.getBtnCancelar().setEnabled(true);
    }
    
    private void salvarCategoria(){
        try {
            String nome = view.getTextCategoria().getText();
            
            String percentualTexto = view.getTextPercentualLucro().getText().replace(",", ".");
            Double percentual = Double.parseDouble(percentualTexto);
            
            if(this.categoriaSelecionada == null){
                Categoria novaCategoria = new Categoria(null, nome, percentual);
                servico.salvar(novaCategoria);
            }else{
                this.categoriaSelecionada.setNome(nome);
                this.categoriaSelecionada.setPercentualLucro(percentual);
                servico.salvar(this.categoriaSelecionada);
            }
            javax.swing.JOptionPane.showMessageDialog(view, "Categoria salva com sucesso!");
            carregarTabela();
            estadoVisualizacao();
            
            
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(view, "O percentual deve ser um número válido.");
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }
    
    private void excluirCategoria(){
        if (this.categoriaSelecionada != null) {
           
            int confirmacao = javax.swing.JOptionPane.showConfirmDialog(view,
                    "Deseja realmente excluir a categoria '" + this.categoriaSelecionada.getNome() + "'?",
                    "Confirmação de exclusão",
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirmacao == javax.swing.JOptionPane.YES_OPTION) {
                try {
                    servico.excluir(this.categoriaSelecionada);
                    
                    javax.swing.JOptionPane.showMessageDialog(view, "Item excluído com sucesso!");
                    carregarTabela();
                    estadoVisualizacao();
                    
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
