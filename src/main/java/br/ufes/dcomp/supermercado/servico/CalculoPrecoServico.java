
package br.ufes.dcomp.supermercado.servico;

import br.ufes.dcomp.supermercado.model.HistoricoPreco;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculoPrecoServico {
    private ProdutoRepository produtoRepository;
    private HistoricoPrecoRepository historicoPrecoRepository;
    private LocalDate dataUltimoCalculoGlobal;
    
    public CalculoPrecoServico(ProdutoRepository produtoRepository, HistoricoPrecoRepository historicoPrecoRepository){
        this.produtoRepository = produtoRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
    }
    
    public void executarCalculoGlobal(LocalDate dataAtual){
        validarIntervaloDe10Dias(dataAtual);
        
        List<Produto> produtos = produtoRepository.buscarTodos();
        
        for(Produto produto:produtos){
            Double percentualLucroCategoria = produto.getCategoria().getPercentualLucro();
            
            Double novoPrecoVenda = calcularPrecoVenda(produto.getPrecoCusto(), percentualLucroCategoria);
            
            produto.setMargemLucroAtual(percentualLucroCategoria);
            produto.setPrecoVendaAtual(novoPrecoVenda);
            
            HistoricoPreco historicoPreco = new  HistoricoPreco(dataAtual, percentualLucroCategoria, novoPrecoVenda, produto);
            historicoPrecoRepository.salvar(historicoPreco);
        }
        this.dataUltimoCalculoGlobal = dataAtual; 
    }    
    
    public void validarIntervaloDe10Dias(LocalDate dataAtual){
       if(this.dataUltimoCalculoGlobal != null){
           long diasPassados = ChronoUnit.DAYS.between(this.dataUltimoCalculoGlobal, dataAtual);
           if(diasPassados < 10){
               throw new RuntimeException("O cálculo só pode ser realizado novamente após 10 dias.");
           }
       } 
    }    

    private Double calcularPrecoVenda(Double precoCusto, Double percentualLucro) {
        Double precoVenda = precoCusto * (1+(percentualLucro/100));
        
       return Math.round(precoVenda * 100.0) / 100.0;
    }
    
    public void setDataUltimoCalculoGlobal(LocalDate dataUltimoCalculoGlobal){
        this.dataUltimoCalculoGlobal = dataUltimoCalculoGlobal;
    }
  
}
