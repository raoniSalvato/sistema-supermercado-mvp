
package br.ufes.dcomp.supermercado;

import br.ufes.dcomp.supermercado.presenter.AutenticacaoPresenter;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import br.ufes.dcomp.supermercado.seeder.DataBaseSeeder;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import br.ufes.dcomp.supermercado.servico.CategoriaServico;

public class SistemaSupermercadoMvp {

    public static void main(String[] args) {
        CategoriaRepository categoriaRepo = new CategoriaRepository();
        ProdutoRepository produtoRepo = new ProdutoRepository();
        HistoricoPrecoRepository historicoRepo = new HistoricoPrecoRepository();
        
        CalculoPrecoServico calculoServico = new CalculoPrecoServico(produtoRepo, historicoRepo);
        CategoriaServico categoriaServico = new CategoriaServico(categoriaRepo, produtoRepo);

        DataBaseSeeder seeder = new DataBaseSeeder(categoriaRepo, produtoRepo, calculoServico);
        seeder.semear();
        
        java.awt.EventQueue.invokeLater(() -> {
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            new AutenticacaoPresenter(usuarioRepo);
        });
    }
}
