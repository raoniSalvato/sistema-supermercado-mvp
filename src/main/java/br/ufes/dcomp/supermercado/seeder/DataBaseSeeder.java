
package br.ufes.dcomp.supermercado.seeder;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import java.time.LocalDate;

public class DataBaseSeeder {
    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;
    private CalculoPrecoServico calculoPrecoServico;
    
    public DataBaseSeeder(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository, CalculoPrecoServico calculoPrecoServico){
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.calculoPrecoServico=calculoPrecoServico;    
    }
            
    public void semear(){
        Categoria educacao = new Categoria(null, "Educação", 25.0);
        Categoria papelaria = new Categoria(null, "Papelaria", 30.0);
        Categoria alimentacao = new Categoria(null, "Alimentação", 22.0);
        Categoria lazer = new Categoria(null, "Lazer", 35.0);
        Categoria entretenimento = new Categoria(null, "Entretenimento", 40.0);
        Categoria higiene = new Categoria(null, "Higiene", 28.0);
        Categoria limpeza = new Categoria(null, "Limpeza", 25.0);

        categoriaRepository.salvar(educacao);
        categoriaRepository.salvar(papelaria);
        categoriaRepository.salvar(alimentacao);
        categoriaRepository.salvar(lazer);
        categoriaRepository.salvar(entretenimento);
        categoriaRepository.salvar(higiene);
        categoriaRepository.salvar(limpeza);
        
        produtoRepository.salvar(new Produto(null, "Livro didático", 45.0, educacao));
        produtoRepository.salvar(new Produto(null, "Livro paradidático", 30.0, educacao));
        produtoRepository.salvar(new Produto(null, "Mochila escolar", 70.0, educacao));
        
        produtoRepository.salvar(new Produto(null, "Caderno universitário", 16.0, papelaria));
        produtoRepository.salvar(new Produto(null, "Lápis grafite HB", 1.20, papelaria));
        produtoRepository.salvar(new Produto(null, "Caneta esferográfica azul", 2.20, papelaria));
        produtoRepository.salvar(new Produto(null, "Borracha branca", 1.00, papelaria));
        produtoRepository.salvar(new Produto(null, "Apontador com depósito", 3.50, papelaria));
        
        produtoRepository.salvar(new Produto(null, "Jogo de tabuleiro", 55.0, lazer));
        produtoRepository.salvar(new Produto(null, "Bola recreativa", 40.0, lazer));
        produtoRepository.salvar(new Produto(null, "Quebra-cabeça 500 peças", 35.0, lazer));
        
        produtoRepository.salvar(new Produto(null, "Fone de ouvido", 48.0, entretenimento));
        produtoRepository.salvar(new Produto(null, "Caixa de som portátil", 80.0, entretenimento));
        produtoRepository.salvar(new Produto(null, "Revista de passatempos", 12.0, entretenimento));
        
        produtoRepository.salvar(new Produto(null, "Suco de uva integral", 5.50, alimentacao));
        produtoRepository.salvar(new Produto(null, "Barra de cereal", 9.00, alimentacao));
        produtoRepository.salvar(new Produto(null, "Biscoito", 2.60, alimentacao));
        
        produtoRepository.salvar(new Produto(null, "Sabonete", 3.20, higiene));
        produtoRepository.salvar(new Produto(null, "Creme dental", 2.80, higiene));
        
        produtoRepository.salvar(new Produto(null, "Detergente líquido", 5.50, limpeza));
        produtoRepository.salvar(new Produto(null, "Esponja multiuso", 1.70, limpeza));
        
        LocalDate dataRetroativa = LocalDate.now().minusDays(10);
        
        calculoPrecoServico.setDataUltimoCalculoGlobal(null); 
        calculoPrecoServico.executarCalculoGlobal(dataRetroativa);
    }
}
